#Работа со структурами данных
#«мультисписок/очередь/дек/приоритетная очередь» на Python


# Мультисписок (вложенный список) — объединение вложенных списков в один
groups = [['Hong', 'Ryan'], ['Andry', 'Ross'], ['Mike', 'Smith']]  # исходные вложенные списки
names = []  # результирующий плоский список
for group in groups:  # перебираем каждый вложенный список
    for name in group:  # перебираем имена внутри каждого вложенного списка
        names.append(name)  # добавляем имя в плоский список names
print('Мультисписок:', names)  # выводим итоговый список имен
# Вывод:Мультисписок: ['Hong', 'Ryan', 'Andry', 'Ross', 'Mike', 'Smith']


# Очередь (используем collections.deque)
from collections import deque
tasks = deque()  # создаем пустую двунаправленную очередь
tasks.append("task1")  # добавляем элемент в конец очереди
tasks.append("task2")  # добавляем ещё один элемент
tasks.append("task3")  # добавляем третий элемент
while tasks:  # пока очередь не пуста
    print("Обработка:", tasks.popleft())  # извлекаем и выводим первый элемент (FIFO)
# Вывод:Обработка: task1
#       Обработка: task2
#       Обработка: task3

# Приоритетная очередь (используем queue.PriorityQueue)
from queue import PriorityQueue
pq = PriorityQueue()  # создаем пустую приоритетную очередь
pq.put((2, 'mid-priority item'))  # добавляем элемент с приоритетом 2
pq.put((1, 'high-priority item'))  # добавляем элемент с приоритетом 1 (ше приоритет)
pq.put((3, 'low-priority item'))  #ляем элемент с приоритетом 3
while not pq.empty(): # пока очередь не пуста
    print('Извлечение по приоритету:', pq.get())  # извлекаем и выводим с наименьшим приоритетом (1)
# Вывод:Извлечение по приоритету: (1, 'high-priority item')
#       Извлечение по приоритету: (2, 'mid-priority item')
#       Извлечение по приоритету: (3, 'low-priority item')


# Дек (используем collections.deque)
from collections import deque
deck = deque()  # создаем пустой двунаправленный список
deck.append("left")  # добавляем в конец
deck.appendleft("front")  # добавляем в начало
deck.append("back")  # добавляем в конец
print('Дек:', list(deck))  # выводимимое дек в виде списка
# Вывод:Дек: ['front', 'left', 'back']