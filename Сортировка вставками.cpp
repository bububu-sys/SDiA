// Сортировка вставками

#include <iostream>
#include <vector>

void insertionSort(std::vector<int>& arr) {
    int n = arr.size();
    for (int i = 1; i < n; ++i) {
        int key = arr[i]; // элемент, который вставляем в отсортированную часть
        int j = i - 1;

        // смещаем элементы, которые больше ключа, вправо
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            --j;
        }
        // вставляем ключ на правильную позицию
        arr[j + 1] = key;
    }
}

int main() {
    std::vector<int> array = {63, 58, 34, 42, 17};
    insertionSort(array);

    for (int num : array) {
        std::cout << num << " ";
    }
    // Вывод: 17 34 42 58 63
    return 0;
}