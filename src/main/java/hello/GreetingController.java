package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

@RestController
public class GreetingController {

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

    @GetMapping("/greetings")
    public Map<Long, Employee> greeting(){

        return listEmpl.getBase();
    }

    @RequestMapping("/greetings/{id}")
    public Employee greetingId(@PathVariable String id){
        return listEmpl.getEmployee(Long.parseLong(id));
    }

    @PostMapping("/greetings")
    public Employee postGreeting(@RequestBody Employee input) throws Exception {
        listEmpl.addEmployee(input);
        return input;
    }

    @PutMapping("greetings/{id}")
    public Map<Long ,Employee> putMethod (@PathVariable String id, @RequestBody Employee input){
        listEmpl.putEmployee(Long.parseLong(id), input);
        return listEmpl.getBase();
    }

    @DeleteMapping("greetings/{id}")
    public Map<Long, Employee> deleteMethod(@PathVariable String id){
        listEmpl.deleteEmployee(Long.parseLong(id));
        return listEmpl.getBase();
    }
}
