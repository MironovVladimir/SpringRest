package crudEmployees;

import org.junit.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XMLContainerTest {

    private EmployeeContainer XMLTest;

    @Before
    public void Init() throws ParserConfigurationException, SAXException, IOException {
        XMLTest = new XMLContainer("test.xml");
    }

    @Test
    public void addEmployee() {
        Employee John = new Employee("John", "Doe", "13.01.1800", 1);
        XMLTest.addEmployee(John);
        Assert.assertEquals(John.getName(), XMLTest.getEmployee(1).getName());
        Assert.assertEquals(John.getSurname(), XMLTest.getEmployee(1).getSurname());
        Assert.assertEquals(John.getDate(), XMLTest.getEmployee(1).getDate());
        Employee Jane = new Employee("Jane", "Doe", "13.01.1801", 1);
        XMLTest.addEmployee(Jane);
        Assert.assertEquals(Jane.getName(), XMLTest.getEmployee(2).getName());
        Assert.assertEquals(Jane.getSurname(), XMLTest.getEmployee(2).getSurname());
        Assert.assertEquals(Jane.getDate(), XMLTest.getEmployee(2).getDate());
        XMLTest.deleteEmployee(1);
        XMLTest.deleteEmployee(2);
    }

    @Test
    public void putEmployee() {
        Employee Jack = new Employee("Jack", "Doe", "13.01.1801", 1);
        XMLTest.putEmployee(1,Jack);
        Assert.assertNull(XMLTest.getEmployee(1));

        XMLTest.addEmployee(new Employee("John","Doe","111",1));
        XMLTest.addEmployee(new Employee("John","Doe","111",1));
        System.out.println(XMLTest.getEmployee(1).getName());

        XMLTest.putEmployee(1,Jack);
        Assert.assertEquals(Jack.getName(), XMLTest.getEmployee(1).getName());
        Assert.assertEquals(Jack.getSurname(), XMLTest.getEmployee(1).getSurname());
        Assert.assertEquals(Jack.getDate(), XMLTest.getEmployee(1).getDate());

        XMLTest.deleteEmployee(1);
        XMLTest.deleteEmployee(2);
    }

    @Test
    public void deleteEmployee() {
        XMLTest.addEmployee(new Employee("John","Doe","111",1));
        XMLTest.addEmployee(new Employee("John","Doe","111",1));
        XMLTest.addEmployee(new Employee("John","Doe","111",1));
        XMLTest.addEmployee(new Employee("John","Doe","111",1));
        XMLTest.deleteEmployee(4);
        Assert.assertNull(XMLTest.getEmployee(4));
        Assert.assertNotNull(XMLTest.getEmployee(3));
        Assert.assertNotNull(XMLTest.getEmployee(2));
        XMLTest.deleteEmployee(1);
        Assert.assertNull(XMLTest.getEmployee(1));
        Assert.assertNotNull(XMLTest.getEmployee(3));
        XMLTest.deleteEmployee(4);
        XMLTest.deleteEmployee(3);
        XMLTest.deleteEmployee(2);
        XMLTest.deleteEmployee(100);


    }
}