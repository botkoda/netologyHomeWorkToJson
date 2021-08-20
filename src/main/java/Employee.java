import java.util.Objects;

public class Employee implements Comparable<Employee> {
    int id;
    String firstName;
    String lastName;
    String country;
    int age;


    public Employee() {
        // Пустой конструктор
    }

    public Employee(int id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return id == employee.id && age == employee.age && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(country, employee.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, country, age);
    }

    @Override
    public int compareTo(Employee e) {
        String s1 = "" + id + firstName + lastName + age + country;
        String s2 = "" + e.getId() + e.getFirstName() + e.getLastName() + e.getAge() + e.getCountry();
        return s1.compareTo(s2);
    }

    @Override
    public String toString() {
        return id + "," + firstName + "," + lastName + "," + country + "," + age;

    }
}