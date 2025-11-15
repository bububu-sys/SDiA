// Сортировка слияниями

import java.util.Arrays;

public class MergeSort {
    // Метод для слияния двух отсортированных половин массива
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        // копируем данные во временные массивы
        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        // слияние
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        // копируем оставшиеся элементы из L
        while (i < n1) {
            arr[k++] = L[i++];
        }

        // копируем оставшиеся элементы из R
        while (j < n2) {
            arr[k++] = R[j++];
        }
    }

    // рекурсивная сортировка слиянием
    public static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return; // базовый случай, массив из одного элемента
        }
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid); // сортируем левую часть
        mergeSort(arr, mid + 1, right); // сортируем правую часть
        merge(arr, left, mid, right); // сливаем
    }

    public static void main(String[] args) {
        int[] array = {63, 58, 34, 42, 17};
        mergeSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
        // Ожидание: [17, 34, 42, 58, 63]
    }
}