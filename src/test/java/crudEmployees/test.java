package crudEmployees;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.hamcrest.Matchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class test {
    @Autowired
    private MockMvc mockMvc;
    private long currentId;

    @Autowired
    private Repository rep;

    @Before
    public void getCurrentId(){
        rep.deleteAll();
        rep.save(new Employee("I", "Don't", "Know, how to do it better", 100 ));
        for(Employee i : rep.findAll()){
            currentId = i.id;
        }
        currentId++;
        rep.deleteAll();
    }

    @Test
    public void getTest() throws Exception {
        rep.save(new Employee("John", "Doe", "10.01.1000", 100));

        mockMvc.perform(MockMvcRequestBuilders.get("/EmployeeCrud")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\""
                        +currentId+"\":{\"name\":\"John\",\"surname\":\"Doe\",\"date\":\"10.01.1000\",\"id\":"
                        +currentId+"}}")));
    }

    @Test
    public void getByIdTest() throws Exception {
        rep.save(new Employee("Jane", "Doe", "10.01.1000", 100));

        mockMvc.perform(MockMvcRequestBuilders.get("/EmployeeCrud/"+currentId)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jane")).
                andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Doe")).
                andExpect(MockMvcResultMatchers.jsonPath("$.date").value("10.01.1000"));
    }

    @Test
    public void putTest() throws Exception {

        rep.save(new Employee("OldName", "OldSurname", "OldDate", 0));

        mockMvc.perform(MockMvcRequestBuilders.
                put("/EmployeeCrud/"+currentId).
                contentType(MediaType.APPLICATION_JSON).
                content("{\"name\": \"NewName\",\"surname\": \"NewSurname\",\"date\": \"10.01.1000\",\"id\": 100}"));

        mockMvc.perform(MockMvcRequestBuilders.get("/EmployeeCrud/"+currentId)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.name").value("NewName")).
                andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("NewSurname")).
                andExpect(MockMvcResultMatchers.jsonPath("$.date").value("10.01.1000"));
    }

    @Test
    public void postTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/EmployeeCrud").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"name\": \"NewName\",\"surname\": \"NewSurname\",\"date\": \"10.01.1000\",\"id\": 100}")).
                andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/EmployeeCrud/"+currentId)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.name").value("NewName")).
                andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("NewSurname")).
                andExpect(MockMvcResultMatchers.jsonPath("$.date").value("10.01.1000"));
    }

    @Test
    public void deleteTest() throws Exception {
        rep.save(new Employee("Delete","Me","20.04.1889", 100));

        mockMvc.perform(MockMvcRequestBuilders.delete("/EmployeeCrud/"+currentId)).
                andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/EmployeeCrud")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(Matchers.equalToIgnoringCase("{}")));
    }
}
