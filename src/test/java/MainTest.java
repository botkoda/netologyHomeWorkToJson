import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;

public class MainTest extends TestCase {

    public void testParseXML() {
        Main main = new Main();
        //ожидаемый результат
        List<Employee> expected = Arrays.asList(
                new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Inav", "Petrov", "RU", 23)
        );
        //полученный результат
        List<Employee> actual = main.parseXML("data2.xml");
        Assertions.assertEquals(expected,actual);
    }


    public void testListToJson() {
        Main main = new Main();
        String expected = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\",\"country\":\"USA\",\"age\":25},{\"id\":2,\"firstName\":\"Inav\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":23}]";
        List<Employee> listEmployee = Arrays.asList(
                new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Inav", "Petrov", "RU", 23)
        );
        //полученный результат
        String actual = main.listToJson(listEmployee);

        Assertions.assertEquals(expected, actual);
    }

    public void testParseCSV() {
        Main main = new Main();
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        //ожидаемый результат
        List<Employee> expected = Arrays.asList(
                new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Inav", "Petrov", "RU", 23)
        );
        //полученный результат
        List<Employee> actual = main.parseCSV(columnMapping, fileName);
        Assertions.assertEquals(expected, actual);
    }
}