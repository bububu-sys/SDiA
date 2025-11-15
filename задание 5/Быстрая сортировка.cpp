// Быстрая сортировка

#include <iostream>
#include <vector>

// Быстрая сортировка
void quickSort(std::vector<int>& arr, int left, int right) {
    if (left >= right) {
        return; // базовый случай: массив из одного элемента или пустой
    }

    int pivot = arr[right]; // выбираем опорный элемент (можно выбрать по-разному)
    int i = left;
    int j = right - 1;

    while (i <= j) {
        // ищем элемент слева, который больше или равен опорному
        while (i <= j && arr[i] < pivot) {
            i++;
        }
        // ищем элемент справа, который меньше или равен опорному
        while (i <= j && arr[j] > pivot) {
            j--;
        }
        if (i < j) {
            std::swap(arr[i], arr[j]);
            i++;
            j--;
        }
    }
    // ставим опорный элемент на правильную позицию
    std::swap(arr[i], arr[right]);
    // рекурсивно сортируем левую и правую части
    quickSort(arr, left, i - 1);
    quickSort(arr, i + 1, right);
}

int main() {
    std::vector<int> array = {64, 34, 25, 12, 22, 11, 90};
    quickSort(array, 0, array.size() - 1);

    std::cout << "Отсортированный массив: ";
    for (int num : array) {
        std::cout << num << " ";
    }
    std::cout << std::endl;

    return 0;
}