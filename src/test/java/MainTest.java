import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.opencsv.CSVWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class MainTest {
    List<Employee> employeeList = Arrays.asList(
            new Employee(1, "John", "Smith", "USA", 25),
            new Employee(2, "Inav", "Petrov", "RU", 23)
    );
    ;


    @Test
    void parseXML() {
        String fileName = "testData.xml";
        List<Employee> result = Main.parseXML(createXmlFile(fileName));
        assertEquals(employeeList, result);
    }

    private String createXmlFile(String fileName) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = builder.newDocument();
        Element root = document.createElement("staff");
        document.appendChild(root);
        //первая ветка employee
        Element employee = document.createElement("employee");
        root.appendChild(employee);
        Element id = document.createElement("id");
        id.appendChild(document.createTextNode("1"));
        employee.appendChild(id);

        Element firstName = document.createElement("firstName");
        firstName.appendChild(document.createTextNode("John"));
        employee.appendChild(firstName);

        Element lastName = document.createElement("lastName");
        lastName.appendChild(document.createTextNode("Smith"));
        employee.appendChild(lastName);

        Element country = document.createElement("country");
        country.appendChild(document.createTextNode("USA"));
        employee.appendChild(country);

        Element age = document.createElement("age");
        age.appendChild(document.createTextNode("25"));
        employee.appendChild(age);


        //вторая ветка employee
        Element employee2 = document.createElement("employee");
        root.appendChild(employee2);
        Element id2 = document.createElement("id");
        id2.appendChild(document.createTextNode("2"));
        employee2.appendChild(id2);

        Element firstName2 = document.createElement("firstName");
        firstName2.appendChild(document.createTextNode("Inav"));
        employee2.appendChild(firstName2);

        Element lastName2 = document.createElement("lastName");
        lastName2.appendChild(document.createTextNode("Petrov"));
        employee2.appendChild(lastName2);

        Element country2 = document.createElement("country");
        country2.appendChild(document.createTextNode("RU"));
        employee2.appendChild(country2);

        Element age2 = document.createElement("age");
        age2.appendChild(document.createTextNode("23"));
        employee2.appendChild(age2);

        //запись в файл
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(fileName));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        try {
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return fileName;
    }


    @Test
    void listToJson() {
        String expected = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\",\"country\":\"USA\",\"age\":25},{\"id\":2,\"firstName\":\"Inav\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":23}]";
        //полученный результат
        String result = Main.listToJson(employeeList);
        assertEquals(expected, result);

    }


    @Test
    void parseCSV() {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "testData.csv";
        //полученный результат
        List<Employee> result = Main.parseCSV(columnMapping, createCSVFile(fileName));
        assertEquals(employeeList, result);
    }

    private String createCSVFile(String fileName) {

        List<String[]> emplyees = Arrays.asList(
                employeeList.get(0).toString().split(","),
                employeeList.get(1).toString().split(",")
        );

        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            for (String[] employee : emplyees) {
                writer.writeNext(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }


    @Test
    void jsonToList() {
        String json = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\",\"country\":\"USA\",\"age\":25},{\"id\":2,\"firstName\":\"Inav\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":23}]";
        List<Employee> result = Main.jsonToList(json);
        assertEquals(employeeList, result);
    }
}