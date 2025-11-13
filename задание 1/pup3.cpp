// № 1
#include <iostream>
#include <list>

int main() {
    std::list<int> List = {1, 2, 3}; // инициализация списка

    for (int item : List) {
        std::cout << item << std::endl;
    }

    return 0;
}

// № 2
#include <iostream>
#include <stack>

int main() {
    std::stack<int> s; // создаём стек

    s.push(1); // добавляем элементы
    s.push(2);
    s.push(3);

    std::cout << s.top() << std::endl; // выводит 3
    s.pop();

    std::cout << s.top() << std::endl; // выводит 2
    s.pop();

    std::cout << s.top() << std::endl; // выводит 1
    s.pop();

    return 0;
}