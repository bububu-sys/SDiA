//Работа со структурами данных
//«мультисписок/очередь/дек/приоритетная очередь» на Java


import java.util.*;

public class DataStructuresExamples {
 public static void main(String[] args) {
        // Мультисписок - объединение вложенных списков в один
        List<List<String>> groups = Arrays.asList(
            Arrays.asList("Hong", "Ryan"),
            Arrays.asList("Andry", "Ross"),
            Arrays.asList("Mike", "Smith")
        );
        List<String> names = new ArrayList<>();
        for (List<String> group : groups) {
            for (String name : group) {
                names.add(name);
            }
        }
        System.out.println("Мультисписок: " + names);
        //Вывод: Мультисписок: [Hong, Ryan, Andry, Ross, Mike, Smith]


        // Очеред (Queue)
        Queue<String> queue = new LinkedList<>();
        queue.add("ан");
        queue.add("яблоко");
        queue.add("ананас");
        System.out.println("Очередь:");
        while (queue.peek() != null) { // пока очередь не пуста
            System.out.println(queue.poll()); // извлекаем и выводим первый элемент
        }
        //Вывод: Очередь: банан
        //       Очередь: яблоко
        //       Очередь: ананас


        // Дек (Deque)
        Deque<Integer> deque = new ArrayDeque<>();
        deque.offerFirst(1); // добавляем в начало
        deque.offerLast(2);  // добавляем в конец
        deque.offerFirst(0); // добавляем ещё в начало
        System.out.println("Дек:");
        for (int num : deque) {
            System.out.println(num);
        }
        //Вывод: Дек:0
        //           1
        //           2


        // Приоритетная очередь (PriorityQueue)
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(10);
        priorityQueue.offer(20);
        priorityQueue.offer(15);
        System.out.println("Приоритетная очередь (по возрастанию): " + priorityQueue.peek());
        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll());
        }
        //Вывод: Приоритетная очередь (по возрастанию): 1
        //       После pop: 15
    }
}