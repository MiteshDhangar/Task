package com.mitesh;
import java.util.*;

public class TSPDynamicInput {
    static double tourLength(List<Integer> tour, double[][] dist) {
        double total = 0;
        int n = tour.size();
        for (int i = 0; i < n - 1; i++) {
            total += dist[tour.get(i)][tour.get(i + 1)];
        }
        total += dist[tour.get(n - 1)][tour.get(0)]; // return to start
        return total;
    }

    static List<Integer> nearestNeighbor(double[][] dist, int start) {
        int n = dist.length;
        boolean[] used = new boolean[n];
        List<Integer> tour = new ArrayList<>();
        tour.add(start); used[start] = true;

        for (int k = 1; k < n; k++) {
            int last = tour.get(tour.size() - 1);
            int bestJ = -1; double bestDist = Double.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!used[j] && dist[last][j] < bestDist) {
                    bestDist = dist[last][j]; bestJ = j;
                }
            }
            tour.add(bestJ); used[bestJ] = true;
        }
        return tour;
    }

    static List<Integer> twoOpt(List<Integer> initTour, double[][] dist) {
        List<Integer> best = new ArrayList<>(initTour);
        double bestLen = tourLength(best, dist);
        boolean improved = true;
        while (improved) {
            improved = false;
            int n = best.size();
            for (int i = 1; i < n - 1 && !improved; i++) {
                for (int j = i + 1; j < n; j++) {
                    List<Integer> candidate = new ArrayList<>(best);
                    Collections.reverse(candidate.subList(i, j));
                    double candidateLen = tourLength(candidate, dist);
                    if (candidateLen + 1e-9 < bestLen) {
                        best = candidate;
                        bestLen = candidateLen;
                        improved = true;
                        break;
                    }
                }
            }
        }
        return best;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Hardcoded default data (28 capitals + matrix)
        String[] defaultCities = {
                "Agartala", "Aizawl", "Amaravati", "Bengaluru", "Bhopal", "Bhubaneswar", "Chandigarh",
                "Chennai", "Dehradun", "Dispur", "Gangtok", "Gandhinagar", "Hyderabad", "Imphal",
                "Itanagar", "Jaipur", "Kohima", "Kolkata", "Lucknow", "Mumbai", "Panaji", "Patna",
                "Raipur", "Ranchi", "Shillong", "Shimla", "Srinagar", "Thiruvananthapuram"
        };

        double[][] defaultDistances = {
                {0, 2259, 1660, 2166, 1669, 2057, 2030, 2209, 2168, 1591, 1163, 1088, 1307, 1433, 1464, 1136, 2147, 1532, 1859, 1882, 2233, 1442, 1748, 1024, 1606, 1512, 1791, 1573},
                {2259, 0, 1984, 1302, 1476, 2241, 1757, 1972, 1879, 2193, 1213, 2375, 1515, 1530, 2341, 1830, 1390, 1395, 1555, 2044, 2013, 2155, 2253, 2114, 1481, 1753, 1727, 1397},
                {1660, 1984, 0, 2321, 2247, 2114, 1440, 1212, 1545, 1067, 2077, 1642, 2066, 1225, 2125, 1424, 1606, 1277, 1763, 1672, 2051, 1985, 1241, 1789, 1881, 1630, 1553, 1442},
                {2166, 1302, 2321, 0, 1641, 1960, 2240, 336, 1237, 1470, 2425, 2457, 575, 1020, 1964, 2035, 1413, 1307, 2100, 798, 872, 2310, 1415, 1795, 1826, 1734, 1511, 1472},
                {1669, 1476, 2247, 1641, 0, 1910, 1724, 1957, 1171, 2185, 2361, 1541, 1161, 1586, 2301, 1715, 1592, 1037, 916, 1390, 2287, 1030, 2167, 1746, 1533, 1711, 2327, 1374},
                {2057, 2241, 2114, 1960, 1910, 0, 1112, 1697, 1711, 1311, 2265, 2156, 1205, 1832, 1880, 1084, 2303, 1716, 1632, 1972, 1353, 1976, 2168, 2275, 1996, 1294, 1688, 1354},
                {2030, 1757, 1440, 2240, 1724, 1112, 0, 1780, 792, 2031, 1520, 1592, 1925, 2248, 1157, 1816, 2291, 1906, 1965, 1244, 2361, 1169, 1494, 1580, 2292, 2117, 1723, 1692},
                {2209, 1972, 1212, 336, 1957, 1697, 1780, 0, 1090, 2242, 1924, 1656, 513, 1947, 1705, 2177, 1759, 1940, 2397, 1361, 1686, 2397, 2175, 2371, 1343, 1507, 2303, 1910},
                {2168, 1879, 1545, 1237, 1171, 1711, 792, 1090, 0, 1085, 1906, 1041, 1913, 1947, 1278, 1112, 1147, 2191, 1721, 1564, 1689, 2323, 1116, 1941, 1215, 1014, 1816, 1513},
                {1591, 2193, 1067, 1470, 2185, 1311, 2031, 2242, 1085, 0, 2247, 1520, 1456, 1575, 1512, 1673, 1122, 1571, 1576, 2015, 2210, 1066, 1126, 2367, 1953, 1035, 1983, 1182},
                {1163, 1213, 2077, 2425, 2361, 2265, 1520, 1924, 1906, 2247, 0, 2132, 2334, 2073, 2025, 2275, 1534, 1674, 2222, 1366, 1642, 1403, 2405, 2410, 2090, 1284, 1305, 1804},
                {1088, 2375, 1642, 2457, 1541, 2156, 1592, 1656, 1041, 1520, 2132, 0, 2463, 2113, 2123, 1400, 2382, 2248, 2282, 2032, 1553, 1780, 2031, 2010, 2254, 2232, 1487, 1774},
                {1307, 1515, 2066, 575, 1161, 1205, 1925, 513, 1913, 1456, 2334, 2463, 0, 1582, 2003, 1810, 2198, 2356, 1586, 1819, 1584, 1209, 1437, 1398, 1272, 1728, 2052, 1244},
                {1433, 1530, 1225, 1020, 1586, 1832, 2248, 1947, 1947, 1575, 2073, 2113, 1582, 0, 2298, 1503, 2214, 2180, 1487, 1636, 1777, 1995, 1161, 2271, 2191, 2278, 1947, 1441},
                {1464, 2341, 2125, 1964, 2301, 1880, 1157, 1705, 1278, 1512, 2025, 2123, 2003, 2298, 0, 1571, 1731, 2205, 1306, 2293, 2007, 1945, 2237, 1523, 1220, 2244, 1420, 1653},
                {1136, 1830, 1424, 2035, 1715, 1084, 1816, 2177, 1112, 1673, 2275, 1400, 1810, 1503, 1571, 0, 2182, 2271, 1330, 1898, 1895, 2253, 1905, 1254, 1906, 2002, 1580, 1915},
                {2147, 1390, 1606, 1413, 1592, 2303, 2291, 1759, 1147, 1122, 1534, 2382, 2198, 2214, 1731, 2182, 0, 1231, 1652, 1965, 1544, 2292, 1183, 2068, 1154, 1777, 1844, 1404},
                {1532, 1395, 1277, 1307, 1037, 1716, 1906, 1940, 2191, 1571, 1674, 2248, 2356, 2180, 2205, 2271, 1231, 0, 1730, 1467, 1212, 1470, 1595, 1980, 2191, 1955, 2176, 1785},
                {1859, 1555, 1763, 2100, 916, 1632, 1965, 2397, 1721, 1576, 2222, 2282, 1586, 1487, 1306, 1330, 1652, 1730, 0, 2123, 1397, 2088, 1086, 1877, 2147, 1756, 1925, 2080},
                {1882, 2044, 1672, 798, 1390, 1972, 1244, 1361, 1564, 2015, 1366, 2032, 1819, 1636, 2293, 1898, 1965, 1467, 2123, 0, 1066, 1803, 1512, 2366, 1315, 2275, 2217, 1973},
                {2233, 2013, 2051, 872, 2287, 1353, 2361, 1686, 1689, 2210, 1642, 1553, 1584, 1777, 2007, 1895, 1544, 1212, 1397, 1066, 0, 1587, 1633, 2171, 1144, 2367, 1984, 2239},
                {1442, 2155, 1985, 2310, 1030, 1976, 1169, 2397, 2323, 1066, 1403, 1780, 1209, 1995, 1945, 2253, 2292, 1470, 2088, 1803, 1587, 0, 2401, 1802, 1530, 2299, 1367, 1206},
                {1748, 2253, 1241, 1415, 2167, 2168, 1494, 2175, 1116, 1126, 2405, 2031, 1437, 1161, 2237, 1905, 1183, 1595, 1086, 1512, 1633, 2401, 0, 1246, 2320, 2005, 1582, 2173},
                {1024, 2114, 1789, 1795, 1746, 2275, 1580, 2371, 1941, 2367, 2410, 2010, 1398, 2271, 1523, 1254, 2068, 1980, 1877, 2366, 2171, 1802, 1246, 0, 1583, 2360, 2213, 2251},
                {1606, 1481, 1881, 1826, 1533, 1996, 2292, 1343, 1215, 1953, 2090, 2254, 1272, 2191, 1220, 1906, 1154, 2191, 2147, 1315, 1144, 1530, 2320, 1583, 0, 1127, 2047, 1542},
                {1512, 1753, 1630, 1734, 1711, 1294, 2117, 1507, 1014, 1035, 1284, 2232, 1728, 2278, 2244, 2002, 1777, 1955, 1756, 2275, 2367, 2299, 2005, 2360, 1127, 0, 1060, 1252},
                {1791, 1727, 1553, 1511, 2327, 1688, 1723, 2303, 1816, 1983, 1305, 1487, 2052, 1947, 1420, 1580, 1844, 2176, 1925, 2217, 1984, 1367, 1582, 2213, 2047, 1060, 0, 2123},
                {1573, 1397, 1442, 1472, 1374, 1354, 1692, 1910, 1513, 1182, 1804, 1774, 1244, 1441, 1653, 1915, 1404, 1785, 2080, 1973, 2239, 1206, 2173, 2251, 1542, 1252, 2123, 0}
        };

        String[] cities;
        double[][] dist;

        try {
            System.out.println("Enter number of cities (or press Enter to use default 28 cities):");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                cities = defaultCities;
                dist = defaultDistances;
            } else {
                int N = Integer.parseInt(line);
                cities = new String[N];
                System.out.println("Enter city names separated by space:");
                String[] cityNames = sc.nextLine().trim().split("\\s+");
                if (cityNames.length != N) {
                    System.out.println("Number of city names does not match. Using defaults.");
                    cities = defaultCities;
                    dist = defaultDistances;
                } else {
                    for (int i = 0; i < N; i++) cities[i] = cityNames[i];
                    dist = new double[N][N];
                    System.out.println("Enter distance matrix row by row (space separated):");
                    for (int i = 0; i < N; i++) {
                        String[] vals = sc.nextLine().trim().split("\\s+");
                        if (vals.length != N) throw new Exception();
                        for (int j = 0; j < N; j++)
                            dist[i][j] = Double.parseDouble(vals[j]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Input error. Using defaults.");
            cities = defaultCities;
            dist = defaultDistances;
        }

        int N = cities.length;
        int startCity = new Random().nextInt(N);

        long startTime = System.currentTimeMillis();

        List<Integer> tour = nearestNeighbor(dist, startCity);
        tour = twoOpt(tour, dist);

        double totalKm = tourLength(tour, dist);
        long endTime = System.currentTimeMillis();
        double durationSec = (endTime - startTime) / 1000.0;

        System.out.println("Tour order:");
        for (int i = 0; i < N; i++)
            System.out.println((i + 1) + ". " + cities[tour.get(i)]);
        System.out.println((N + 1) + ". " + cities[tour.get(0)] + " (return to start)");
        System.out.printf("%nTotal distance: %.2f km%n", totalKm);
        System.out.printf("Total cost    : ₹%.2f%n", totalKm);
        System.out.printf("%nExecution time: %.3f seconds%n", durationSec);
    }
}
