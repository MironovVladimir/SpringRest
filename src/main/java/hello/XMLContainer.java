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
    String filename;
    Document document;
    Element root;
    static AtomicLong currentId = new AtomicLong(0);

    XMLContainer(String filename) throws IOException, SAXException, ParserConfigurationException {
        this.filename = filename;

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        this.document = documentBuilder.parse(filename);
        root = document.getDocumentElement();
    }

    @Override
    public void addEmployee(Employee _employee) throws Exception{

        Element employee = document.createElement("employee");

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
        id.appendChild(document.createTextNode( String.valueOf(    currentId.incrementAndGet()/*_employee.getId()*/ )));
        employee.appendChild(id);

        root.appendChild(employee);

        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(filename);
        transformer.transform(source, result);
    }

    @Override
    public void putEmployee(long _id, Employee _employee) {
        NodeList employees = document.getElementsByTagName("employee");
        int i;
        for(i=0; i<employees.getLength();i++){
            Element employee = (Element)employees.item(i);
            if(employee.getElementsByTagName("id").item(0).getTextContent().equals(String.valueOf(_id))) break;
            if(i==employees.getLength()-1) return;
        }
        root.removeChild(employees.item(i));
        Element employee = document.createElement("employee");

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
        System.out.println(_id);
        id.appendChild(document.createTextNode( String.valueOf(    _id/*_employee.getId()*/ )));
        employee.appendChild(id);

        root.appendChild(employee);

        try {
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(filename);
            transformer.transform(source, result);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteEmployee(long id) {
        NodeList employees = document.getElementsByTagName("employee");
        int i;
        for(i=0; i<employees.getLength();i++){
            Element employee = (Element)employees.item(i);
            if(employee.getElementsByTagName("id").item(0).getTextContent().equals(String.valueOf(id))) break;
            if(i==employees.getLength()-1) return;
        }
        root.removeChild(employees.item(i));
        try {
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("test.xml");
            transformer.transform(source, result);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee getEmployee(long id) {
        NodeList employees = document.getElementsByTagName("employee");
        int i;
        for(i=0; i<employees.getLength();i++){
            Element employee = (Element)employees.item(i);
            if(employee.getElementsByTagName("id").item(0).getTextContent().equals(String.valueOf(id))) break;
            if(i==employees.getLength()-1) return null;
        }
        String retName = ((Element)employees.item(i))
                .getElementsByTagName("name").item(0).getTextContent();
        String retSurname = ((Element)employees.item(i))
                .getElementsByTagName("surname").item(0).getTextContent();
        String retDate = ((Element)employees.item(i))
                .getElementsByTagName("date").item(0).getTextContent();
        long retId = Long.parseLong(((Element)employees.item(i))
                .getElementsByTagName("id").item(0).getTextContent());
        return new Employee(retName, retSurname, retDate, retId);
    }

    @Override
    public Map<Long, Employee> getBase() {
        Map<Long ,Employee> ret = new HashMap<>();
        NodeList employees = document.getElementsByTagName("employee");
        int i;
        for(i=0; i<employees.getLength();i++){
            String retName = ((Element)employees.item(i))
                    .getElementsByTagName("name").item(0).getTextContent();
            String retSurname = ((Element)employees.item(i))
                    .getElementsByTagName("surname").item(0).getTextContent();
            String retDate = ((Element)employees.item(i))
                    .getElementsByTagName("date").item(0).getTextContent();
            long retId = Long.parseLong(((Element)employees.item(i))
                    .getElementsByTagName("id").item(0).getTextContent());
            ret.put(retId ,new Employee(retName, retSurname, retDate, retId));
        }
        return ret;
    }
}
