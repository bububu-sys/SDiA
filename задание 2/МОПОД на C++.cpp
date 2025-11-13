//Работа со структурами данных
//«мультисписок/очередь/дек/приоритетная очередь» на C++


#include <iostream>
#include <vector>
#include <queue>
#include <deque>
#include <string>

int main() {
    using namespace std;

    // Мультисписок
    vector<vector<string>> groups = {
        {"Hong", "Ryan"},
        {"Andry", "Ross"},
        {"Michael", "Smith"}
    };
    vector<string> names;  // итоговый список
    // Перебираем вложенные списки    for (const auto& group : groups) {
        for (const auto& : group) {
            names.push_back(name); // добавляем каждого в итоговый список
        }
    }
    // выводим мультисписок
    std::cout << "Мультисписок: ";
    for (const auto& n : names) std::cout << n << " ";
    std::cout << std::endl;
    //Вывод: Мультисписок: Hong Ryan Andry Ross Michael Smith


    // Очередь
    std::queue<std::string> q; // создаем очередь
    q.push("task1"); // добавляем в очередь
    q.push("task2");
    q.push("task3");
    std::cout << "Очередь: ";
    while (!q.empty()) { // пока очередь не пуста
        std::cout << q.front() << " "; // получаем первый элемент
        q.pop(); // удаляем его
    }
    std::cout << std::endl;
    //Вывод: Очередь: task1 task2 task3 


    // Дек
    std::deque<int> d = {1, 2, 3}; // создаем дек с начальными элементами
    d.push_front(0); // добавляем в начало
    d.push_back(4); // добавляем в конец
    std::cout << "Дек: ";
    for (int num : d) std::cout << num << " ";
    std::cout << std::endl;
    //Вывод: Дек: 0 1 2 4


    // Приоритетная очередь
    std::priority_queue<int> pq;
    pq.push(10); // добавляем элементы
    pq.push(20);
    pq.push(15);
    std::cout << "Приоритетная очередь (максимум): " << pq.top() << std::endl; // максимум
    pq.pop(); // удаляем максимум
    std::cout << "После pop: " << pq.top() << std::endl; // новый максимум

    return 0;
    //Вывод: Приоритетная очередь (максимум): 20
    //       После pop: 15
}


