package hello;

import org.springframework.data.repository.CrudRepository;

public interface Repository extends CrudRepository<Employee, Long> {

}
