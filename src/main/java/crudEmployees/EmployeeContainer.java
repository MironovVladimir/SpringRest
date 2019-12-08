package crudEmployees;

import java.util.Map;
import java.util.logging.Logger;

public interface EmployeeContainer {
    Logger logger = Logger.getLogger(EmployeeContainer.class.getName());
    void addEmployee(Employee employee);
    void putEmployee(long id, Employee employee);
    void deleteEmployee(long id);
    Employee getEmployee(long id);
    Map<Long, Employee> getBase();
}
