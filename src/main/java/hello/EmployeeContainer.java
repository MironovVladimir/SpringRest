package hello;

import java.util.Map;

public interface EmployeeContainer {
    void addEmployee(Employee employee);
    void putEmployee(long id, Employee employee);
    void deleteEmployee(long id);
    Employee getEmployee(long id);
    Map<Long, Employee> getBase();
}
