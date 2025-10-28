//Сортировка выбором

public class SelectionSort {
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i; // индекс минимального элемента
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j; // ищем минимальный элемент
                }
            }
            // меняем местами текущий элемент с минимальным
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    public static void main(String[] args) {
        int[] array = {63, 58, 34, 42, 17};
        selectionSort(array);
        for (int num : array) {
            System.out.print(num + " ");
        }
        // Вывод: 17, 34, 42, 58, 63
    }
}