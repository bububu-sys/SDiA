// Интерполирующий поиск

#include <iostream>
#include <vector>

int interpolation_search(const std::vector<int>& arr, int target) {
    int low = 0;
    int high = arr.size() - 1;

    while (low <= high && target >= arr[low] && target <= arr[high]) {
        if (arr[high] == arr[low]) {
            // Предотвращаем деление на ноль
            if (arr[low] == target)
                return low;
            else
                break;
        }

        // Расчет позиции "приближения" к target
        int mid = low + ((double)(high - low) / (arr[high] - arr[low])) * (target - arr[low]);

        if (arr[mid] == target) {
            return mid; // нашли
        } else if (arr[mid] < target) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return -1; // не нашли
}

int main() {
    std::vector<int> array = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
    int target_value = 7;

    int index = interpolation_search(array, target_value);

    if (index != -1) {
        std::cout << "Элемент " << target_value << " найден на позиции: " << index << std::endl;
    } else {
        std::cout << "Элемент " << target_value << " не найден в массиве." << std::endl;
    }

    return 0;
}