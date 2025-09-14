#Создание списка с выводом в Python

#  №1
list1 = list()
list1.append('100')
list1.append('5')
list1.append('зачёт')
list1.append('4')
list1.append('3')
list1.append('IT')
print(list1)

#  №2
list2 = list()
for i in range(10):
    list2.append(i)
print(list2)
# В первой программе список состоит из чисел и строк, но так как все элементы в ' ' считываются как строки
# Во второй только из цифр, именно поэтому можно не прописывать кавычки, а изначально добавить одни числа

# № 3
stack = [] # Создаем стек с 3 элементами

stack.append(1) # Добавляем элементы в стек
stack.append(2)
stack.append(3)

print("Стек:", stack) # Выводим содержимое стека

