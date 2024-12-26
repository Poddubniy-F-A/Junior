package HW3;

import java.io.Serializable;

public class Student implements Serializable {
    public final String name;
    public final int age;
    public transient final double GPA;

    public Student(String name, int age, double GPA) {
        this.name = name;
        this.age = age;
        this.GPA = GPA;
    }
}
