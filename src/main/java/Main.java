import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        String newJsonFile = "new_data_from_CSV.json";

        //ЗАДАНИЕ1 csv в json
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        List<Employee> listJson = jsonToList(json);
        writeString(json, newJsonFile);

        //ЗАДАНИЕ2 xml в json
        List<Employee> list2 = parseXML("data2.xml");
        json = listToJson(list2);
        newJsonFile = "new_data_from_XML.json";
        writeString(json, newJsonFile);


    }

    static List<Employee> jsonToList(String jsonText) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        // List<Employee> employee=gson.fromJson(jsonText,List<Employee>);
        Type listType = new TypeToken<ArrayList<Employee>>() {
        }.getType();
        List<Employee> employee = gson.fromJson(jsonText, listType);

        return employee;

    }

    static List<Employee> parseXML(String fileName) {
        List<Employee> employees = new ArrayList<Employee>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new File(fileName));
            //корневой элемент
            Node root = doc.getDocumentElement();
            //список дочерних элементов
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                //если в XML найден элемент employees
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element employee = (Element) node;
                    //  if(employee.getNodeName().equals("employee")){
                    //в список добавляем объекты класса  Employee
                    employees.add(
                            new Employee(Integer.parseInt(employee.getElementsByTagName("id").item(0).getTextContent()),
                                    employee.getElementsByTagName("firstName").item(0).getTextContent(),
                                    employee.getElementsByTagName("lastName").item(0).getTextContent(),
                                    employee.getElementsByTagName("country").item(0).getTextContent(),
                                    Integer.parseInt(employee.getElementsByTagName("age").item(0).getTextContent()))

                    );
                    //  }
                }
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    static void writeString(String json, String nameJsonFile) {
        try (FileWriter file = new FileWriter(nameJsonFile)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(list, listType);

        return json;
    }

    static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> employees = null;
        //поптыка чтения данных их data.csv
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            //класс, к которому будут привязывать данные из CSV документа,
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            //а также порядок расположения полей в этом документе
            strategy.setColumnMapping(columnMapping);
            //создает инструмент для взаимодействия CSV документа и выбранной ранее стратегии
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader).withMappingStrategy(strategy).build();
            //присваеваем полученные значения списку
            employees = csv.parse();


        } catch (IOException e) {
            e.printStackTrace();

        }
        return employees;

    }
}
