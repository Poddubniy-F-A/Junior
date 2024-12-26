package HW1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) throws Throwable {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println(getAverage(list));
    }

    private static double getAverage(ArrayList<Integer> list) throws Throwable {
        return list.stream().
                filter(val -> val % 2 == 0).mapToInt(Integer::intValue).average().
                orElseThrow((Supplier<Throwable>) () -> new Exception("Получен пустой массив"));
    }
}
