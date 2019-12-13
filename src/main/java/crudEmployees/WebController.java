package crudEmployees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class WebController {
    
    @Autowired
    @Qualifier("sqlContainer")
    private EmployeeContainer listEmployees;

    @GetMapping("/EmployeeCrud")
    public Map<Long, Employee> getMethod(){

        return listEmployees.getBase();
    }

    @RequestMapping("/EmployeeCrud/{id}")
    public Employee getByIdMethod(@PathVariable String id){
        return listEmployees.getEmployee(Long.parseLong(id));
    }

    @PostMapping("/EmployeeCrud")
    public Employee postMethod(@RequestBody Employee input) throws Exception {
        listEmployees.addEmployee(input);
        return input;
    }

    @PutMapping("EmployeeCrud/{id}")
    public Map<Long ,Employee> putMethod (@PathVariable String id, @RequestBody Employee input){
        listEmployees.putEmployee(Long.parseLong(id), input);
        return listEmployees.getBase();
    }

    @DeleteMapping("EmployeeCrud/{id}")
    public Map<Long, Employee> deleteMethod(@PathVariable String id){
        listEmployees.deleteEmployee(Long.parseLong(id));
        return listEmployees.getBase();
    }
}
