package HW2;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        // выводятся все методы, объявленные в самом String и интерфейсах, которые он реализует, без методов родительского класса Object
        for (Method method : String.class.getDeclaredMethods()) {
            System.out.println(method);
        }
    }
}
