#Сортировка бусинами (гравитационная)

def gravity_bead_sort(arr):
    max_val = max(arr)
    # Создаем "линии" бусин от 1 до max_val
    beads = [[] for _ in range(max_val)]

    # "Падаем" бусины: помещаем в соответствующий уровень
    for num in arr:
        for level in range(num):
            beads[level].append(1)  # бусина есть
        for level in range(num, max_val):
            beads[level].append(0)  # пустое место

    # Собираем отсортированные бусины, начиная с верхних уровней
    sorted_arr = []
    for level in reversed(beads):
        count = sum(level)  # количество бусин на этом уровне
        for _ in range(count):
            sorted_arr.append(1)
    # Возвращаем массив с отсортированными числами
    return sorted_arr[::-1]

# Пример использования
array = [5, 3, 1, 7, 4]
sorted_array = gravity_bead_sort(array)
print(sorted_array)
#вывод = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
