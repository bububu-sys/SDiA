// Пирамидальная сортировка

public class HeapSort {
    // Метод для "подъема" элемента и поддержания свойства кучи
    private static void heapify(int[] arr, int n, int i) {
        int largest = i; // Изначально считаем, что родитель — самый большой
        int left = 2 * i + 1; // левый потомок
        int right = 2 * i + 2; // правый потомок

        // если левый потомок больше текущего наибольшего
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // если правый потомок больше текущего наибольшего
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // если наибольший не родитель, меняем местами
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // рекурсивно heapify поддерево
            heapify(arr, n, largest);
        }
    }

    // Главный метод сортировки
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Построение max-кучи
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Извлекаем элементы по одному
        for (int i = n - 1; i >= 0; i--) {
            // Перемещаем текущий корень в конец массива
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // вызываем heapify для уменьшенной кучи
            heapify(arr, i, 0);
        }
    }

    public static void main(String[] args) {
        int[] array = {64, 34, 25, 12, 22, 11, 90};

        heapSort(array);

        System.out.print("Отсортированный массив: ");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
//вывод = Отсортированный массив: 11 12 22 25 34 64 90