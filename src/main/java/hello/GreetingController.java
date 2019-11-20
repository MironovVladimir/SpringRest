package hello;

import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s";
    private static final AtomicLong counter = new AtomicLong();
    //private static final EmployeeList listEmpl = new EmployeeList();
    private static EmployeeContainer listEmpl = null;

    static {
        try {
            listEmpl = new XMLContainer("test.xml");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/greetings")
    public Map<Long, Employee> greeting(@RequestParam(value = "name"
            , defaultValue = "World") String name){

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
