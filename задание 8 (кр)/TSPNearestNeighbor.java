//Метод ближайшего соседа (TSP)



import java.util.*;

public class TSPNearestNeighbor {

    public static List<Integer> tspNearest(int[][] dist) {
        List<Integer> path = new ArrayList<>();
        int n = dist.length;
        boolean[] visited = new boolean[n];
        int curr = 0;
        path.add(curr);
        visited[curr] = true;
        
        // Цикл выбора ближайшего непосещённого города
        for (int step = 1; step < n; step++) {
            int nextCity = -1;
            int minDist = Integer.MAX_VALUE;
            
            for (int j = 0; j < n; j++) {
                if (!visited[j] && dist[curr][j] > 0 && dist[curr][j] < minDist) {
                    minDist = dist[curr][j];
                    nextCity = j;
                }
            }
            
            if (nextCity == -1) {
                // Если нет доступных городов, прерываем выполнение
                System.out.println("Ошибка: нет доступных городов для перехода на шаге " + step);
                break;
            }
            
            path.add(nextCity);
            visited[nextCity] = true;
            curr = nextCity;
        }
        
        return path;
    }
    
    public static int calculatePathLength(List<Integer> path, int[][] dist) {
        int length = 0;
        int n = path.size();
        
        for (int i = 0; i < n - 1; i++) {
            int from = path.get(i);
            int to = path.get(i + 1);
            if (dist[from][to] <= 0) {
                System.out.println("Предупреждение: нулевое или отрицательное расстояние между " + from + " и " + to);
            }
            length += dist[from][to];
        }
        
        // Возвращаемся в начальный город
        int lastCity = path.get(n - 1);
        int firstCity = path.get(0);
        if (dist[lastCity][firstCity] <= 0) {
            System.out.println("Предупреждение: нулевое или отрицательное расстояние для возврата в начальный город");
        }
        length += dist[lastCity][firstCity];
        
        return length;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Введите количество городов: ");
            int n = scanner.nextInt();
            
            if (n <= 0) {
                System.out.println("Ошибка: количество городов должно быть положительным числом");
                return;
            }
            
            int[][] distances = new int[n][n];
            
            System.out.println("Введите матрицу расстояний " + n + "x" + n + ":");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    distances[i][j] = scanner.nextInt();
                    if (i == j && distances[i][j] != 0) {
                        System.out.println("Предупреждение: расстояние от города к самому себе должно быть 0");
                    }
                }
            }
            
            List<Integer> path = tspNearest(distances);
            int pathLength = calculatePathLength(path, distances);
            
            System.out.println("\nРезультаты:");
            System.out.println("Найденный маршрут: " + path);
            System.out.println("Длина маршрута: " + pathLength);
            
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
/*
Введите количество городов: 4
Введите матрицу расстояний 4x4:
0 10 15 20
10 0 35 25
15 35 0 30
20 25 30 0

Результаты:
Найденный маршрут: [0, 1, 3, 2]
Длина маршрута: 80
*/
