package crudEmployees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SQLContainer implements EmployeeContainer {

    @Autowired
    private Repository repository;

    public SQLContainer() {
    }

    @Override
    public void addEmployee(Employee employee) {

        repository.save(employee);
        logger.info("element added");
    }

    @Override
    public void putEmployee(long id, Employee employee) {
        if(!repository.existsById(id)) {
            logger.info("tried to update element with not existing id: "+id );
            return;
        }
        Employee change = repository.findById(id).get();
        change.setName(employee.getName());
        change.setSurname(employee.getSurname());
        change.setDate(employee.getDate());
        repository.save(change);
        logger.info("updated element with "+id+" id");
    }

    @Override
    public void deleteEmployee(long id) {
        if(!repository.existsById(id)) {
            logger.info("tried to delete not existing id" + id);
            return;
        }
        repository.deleteById(id);
        logger.info("element with "+id+" id was removed");
    }

    @Override
    public Employee getEmployee(long id) {
        if(!repository.existsById(id)) {
            logger.info("tried to get element with not existing id: "+id);
            return null;
        }
        Optional<Employee> ret = repository.findById(id);
        logger.info("sending element with id="+id+" to user");
        return ret.get();
    }

    @Override
    public Map<Long, Employee> getBase() {
        Iterable<Employee> ret = repository.findAll();
        Map<Long, Employee> retMap = new HashMap<>();
        for(Employee i : ret){
            retMap.put(i.getId(), i);
        }
        return retMap;
    }
}
