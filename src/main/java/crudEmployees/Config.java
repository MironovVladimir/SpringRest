package crudEmployees;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Configuration
public class Config {
    @Bean
    public XMLContainer xmlContainer(){
        try {
            return new XMLContainer("test.xml");
        } catch (IOException e) {
            e.printStackTrace();
            EmployeeContainer.logger.warning("IO exception "+e.getMessage());
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
            EmployeeContainer.logger.warning("SAX exception "+e.getMessage());
        } catch (ParserConfigurationException e) {
            EmployeeContainer.logger.warning("Parser configuration exception "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
