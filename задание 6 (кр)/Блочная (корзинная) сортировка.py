# Блочная (корзинная) сортировка

def bucket_sort(arr):
    if len(arr) == 0:
        return arr

    # Определяем количество корзин
    num_buckets = int(len(arr) ** 0.5)
    min_value = min(arr)
    max_value = max(arr)

    # Создаем пустые корзины
    buckets = [[] for _ in range(num_buckets)]

    # Распределяем элементы по корзинам
    for num in arr:
        index = int((num - min_value) / (max_value - min_value + 1) * num_buckets)
        buckets[index].append(num)

    # Сортируем каждую корзину и объединяем
    sorted_arr = []
    for bucket in buckets:
        sorted_arr.extend(sorted(bucket))
    return sorted_arr

# Пример использования
array = [0.42, 2.1, 1.3, 0.5, 3.8, 2.2]
print(bucket_sort(array))
#вывод = [0.42, 0.5, 1.3, 2.1, 2.2, 3.8]