# Сортировка Шелла

def shell_sort(arr):
    n = len(arr)
    gap = n // 2  # начальный интервал

    while gap > 0:
        for i in range(gap, n):
            temp = arr[i]
            j = i

            # вставка элемента arr[i] в отсортированный подмассив
            while j >= gap and arr[j - gap] > temp:
                arr[j] = arr[j - gap]
                j -= gap

            arr[j] = temp
        gap //= 2  # уменьшение интервала

    return arr

# пример использования
arr = [64, 34, 25, 12, 22, 11, 90]
print("Отсортированный массив:", shell_sort(arr))