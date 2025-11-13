// (№1) Создание списка с выводом в Java

public class Main {
    public static void main(String[] args) {
        String[] list = new String [4];

        list[0] = "1";
        list[1] = "100";
        list[2] = "5";
        list[3] = "зачёт";

        System.out.println(list[0] + list[1] + list[2] + list[3]);
    }
}

// В данном примере из массива проиводится вывод по эелеменам
// Для вывода содержимого без перечисления необходимо использовать пакет java.until, его класс Arrays и метод Arrays.toString()

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String[] list = new String [4];

        list[0] = "1";
        list[1] = "100";
        list[2] = "5";
        list[3] = "зачёт";

        System.out.println(Arrays.toString(list));
    }
}

// (№2) Stack
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        // Создаём стек
        Stack<Integer> stack = new Stack<>();

        // Добавляем элементы в стек
        stack.push(1);
        stack.push(2);
        stack.push(3);

        // Выводим содержимое стека
        System.out.println("Стек: " + stack);
    }
}