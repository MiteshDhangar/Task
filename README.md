# 🗺️ Traveling Salesman Problem (TSP) Solver – Java

This Java program solves the **Traveling Salesman Problem (TSP)** for Indian state capitals using **Nearest Neighbor** and **2-opt heuristics**. It calculates an efficient tour to visit all cities exactly once and return to the origin, minimizing the total distance.

---

## 📌 Features

- ✅ Accepts **dynamic input** of cities and distances from the user
- 🏙️ If no input is provided, it defaults to **28 Indian capital cities** and their road distances
- ⚡ Implements:
  - **Nearest Neighbor Heuristic** (for initial solution)
  - **2-opt Optimization** (for local improvement)
- 📊 Outputs:
  - Ordered route
  - Total distance in kilometers
  - Total cost in ₹
  - Execution time in seconds

---

## 🧠 Algorithms Used

### 1. **Nearest Neighbor (Greedy Heuristic)**
- Starts from a random city and always visits the nearest unvisited one.
- Simple, fast, and gives a reasonable approximation.
- **Time Complexity:** `O(N²)`

### 2. **2-opt Optimization**
- Iteratively improves the path by reversing segments to eliminate crossovers.
- Stops when no further improvement is found.
- **Time Complexity (worst-case):** `O(N² × k)`, where `k` is the number of iterations

> ✅ These heuristics provide a practical balance between **speed** and **solution quality** for moderately sized TSP instances.

---

## 🧪 Sample Output

Enter number of cities (or press Enter to use default 28 cities):

Tour order:

Hyderabad
Chennai
Bengaluru
Mumbai
Gangtok
Aizawl
Thiruvananthapuram
Imphal
Amaravati
Dispur
Patna
Bhopal
Lucknow
Raipur
Ranchi
Agartala
Jaipur
Bhubaneswar
Chandigarh
Dehradun
Shimla
Srinagar
Itanagar
Shillong
Kohima
Kolkata
Panaji
Gandhinagar
Hyderabad (return to start)

Total distance: 32175.00 km
Total cost : ₹32175.00
Execution time: 0.007 seconds
