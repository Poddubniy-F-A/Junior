package HW3;

import java.io.*;
import java.lang.reflect.Field;

public class Main {
    private final static String pathToFile = "./src/main/java/HW3/file";

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException {
        Student student = new Student("Alex", 14, 4.0);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(pathToFile));
        objectOutputStream.writeObject(student);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(pathToFile));
        Student readedStudent = (Student) objectInputStream.readObject();
        objectInputStream.close();

        Field[] fields = readedStudent.getClass().getFields();
        for (Field field : fields) {
            System.out.println(field.getName() + ": " + field.get(readedStudent));
        }
    }
}
