package hello;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class XMLContainer implements EmployeeContainer {
    private String filename;
    private Document document;
    private Element root;
    private AtomicLong currentId = new AtomicLong(0);

    XMLContainer(String filename) throws IOException, SAXException, ParserConfigurationException {
        this.filename = filename;
        logger.info("opening source xml "+ filename);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        this.document = documentBuilder.parse(filename);
        root = document.getDocumentElement();
        logger.info(filename+" is opened");
    }

    @Override
    public void addEmployee(Employee _employee){

        Element employee = objectToElement(currentId.incrementAndGet(), _employee);

        root.appendChild(employee);

        saveChanges();

        logger.info("added employee with id: "+currentId );
    }

    @Override
    public void putEmployee(long _id, Employee _employee) {
        NodeList employees = document.getElementsByTagName("employee");
        int i;
        if(employees.getLength() == 0) return;
        for(i=0; i<employees.getLength();i++){
            Element employee = (Element)employees.item(i);
            if(employee.getElementsByTagName("id").item(0).getTextContent().equals(String.valueOf(_id))) break;
            if(i==employees.getLength()-1) {
                logger.info("tried to update not existing id: "+_id );
                return;
            }
        }
        root.removeChild(employees.item(i));
        Element employee = objectToElement(_id, _employee);

        root.appendChild(employee);

        saveChanges();
        logger.info("updated element with "+_id+" id");
    }

    @Override
    public void deleteEmployee(long id) {
        NodeList employees = document.getElementsByTagName("employee");
        int i;
        if(employees.getLength()==0) return;
        for(i=0; i<employees.getLength();i++){
            Element employee = (Element)employees.item(i);
            if(employee.getElementsByTagName("id").item(0).getTextContent().equals(String.valueOf(id))) break;
            if(i==employees.getLength()-1) {
                logger.info("tried to delete not existing id" + id);
                return;
            }
        }
        root.removeChild(employees.item(i));
        saveChanges();
        logger.info("element with "+id+" id was removed");
    }

    @Override
    public Employee getEmployee(long id) {
        NodeList employees = document.getElementsByTagName("employee");
        int i;
        if(employees.getLength()==0) return null;
        for(i=0; i<employees.getLength();i++){
            Element employee = (Element)employees.item(i);
            if(employee.getElementsByTagName("id").item(0).getTextContent().equals(String.valueOf(id))) break;
            if(i==employees.getLength()-1) {
                logger.info("tried to get element with not existing id: "+id);
                return null;
            }
        }
        logger.info("sending element with id="+id+" to user");
        return elementToObject((Element) employees.item(i));
    }

    @Override
    public Map<Long, Employee> getBase() {
        Map<Long ,Employee> ret = new HashMap<>();
        NodeList employees = document.getElementsByTagName("employee");
        int i;
        for(i=0; i<employees.getLength();i++){
           Employee employee = elementToObject((Element) employees.item(i));
            ret.put(employee.getId(), employee);
        }
        return ret;
    }

    private void saveChanges(){
        try {
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(filename);
            transformer.transform(source, result);
        } catch(Exception e) {
            e.printStackTrace();
            logger.warning("error in saving changes");
        }
    }

    private Element objectToElement(long _id, Employee _employee){
        Element employee = document.createElement("employee");//objectToElement(_employee, id)

        Element name = document.createElement("name");
        name.appendChild(document.createTextNode(_employee.getName()));
        employee.appendChild(name);

        Element surname = document.createElement("surname");
        surname.appendChild(document.createTextNode(_employee.getSurname()));
        employee.appendChild(surname);

        Element date = document.createElement("date");
        date.appendChild(document.createTextNode(_employee.getDate()));
        employee.appendChild(date);

        Element id = document.createElement("id");
        id.appendChild(document.createTextNode( String.valueOf(_id)));
        employee.appendChild(id);
        return employee;
    }

    private Employee elementToObject(Element employee){
        String retName = employee
                .getElementsByTagName("name").item(0).getTextContent();
        String retSurname = employee
                .getElementsByTagName("surname").item(0).getTextContent();
        String retDate = employee
                .getElementsByTagName("date").item(0).getTextContent();
        long retId = Long.parseLong(employee
                .getElementsByTagName("id").item(0).getTextContent());
        return new Employee(retName, retSurname, retDate, retId);
    }
}
