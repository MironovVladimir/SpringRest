package crudEmployees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class WebController {

    @Autowired
    private SQLContainer listEmpl;

    static {
       /* try {
            listEmpl = new XMLContainer("test.xml");
        } catch (IOException e) {
            e.printStackTrace();
            EmployeeContainer.logger.warning("IO exception "+e.getMessage());
        } catch (SAXException e) {
            e.printStackTrace();
            EmployeeContainer.logger.warning("SAX exception "+e.getMessage());
        } catch (ParserConfigurationException e) {
            EmployeeContainer.logger.warning("Parser configuration exception "+e.getMessage());
            e.printStackTrace();
        }\
        */
    }

    @GetMapping("/EmployeeCrud")
    public Map<Long, Employee> getMethod(){

        return listEmpl.getBase();
    }

    @RequestMapping("/EmployeeCrud/{id}")
    public Employee getByIdMethod(@PathVariable String id){
        return listEmpl.getEmployee(Long.parseLong(id));
    }

    @PostMapping("/EmployeeCrud")
    public Employee postMethod(@RequestBody Employee input) throws Exception {
        listEmpl.addEmployee(input);
        return input;
    }

    @PutMapping("EmployeeCrud/{id}")
    public Map<Long ,Employee> putMethod (@PathVariable String id, @RequestBody Employee input){
        listEmpl.putEmployee(Long.parseLong(id), input);
        return listEmpl.getBase();
    }

    @DeleteMapping("EmployeeCrud/{id}")
    public Map<Long, Employee> deleteMethod(@PathVariable String id){
        listEmpl.deleteEmployee(Long.parseLong(id));
        return listEmpl.getBase();
    }
}