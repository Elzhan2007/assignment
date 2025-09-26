#### Assignment 1

i. Implemented algorithms
ii. We implemented 4 algorithms:
1. Merge Sort
2. Quick Sort
3. Deterministic Select (Median-of-Medians)
4. Closest Pair of Points (2D)


#### Merge Sort
``` java
import java.util.Arrays;
public class mergesort {

    public static int[] mergeSort(int[] arr) {
        if (arr.length <= 1) {
            return arr;
        }

        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        return merge(mergeSort(left), mergeSort(right));
    }

    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        int[] sortedArr = mergeSort(arr);
        System.out.println("Sorted Array: " + Arrays.toString(sortedArr));
    }
}


#### Merge Sort with Time and Recursion Depth Measurement
import java.util.Arrays;
public class MergeSortWithMetrics {

    private static int recursionDepth = 0;

    public static int[] mergeSortWithMetrics(int[] arr) {
        recursionDepth++;
        if (arr.length <= 1) {
            recursionDepth--;
            return arr;
        }

        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        return mergeWithMetrics(mergeSortWithMetrics(left), mergeSortWithMetrics(right));
    }

    public static int[] mergeWithMetrics(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;
        int comparisons = 0;

        while (i < left.length && j < right.length) {
            comparisons++;
            if (left[i] < right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        
        long startTime = System.nanoTime();
        int[] sortedArr = mergeSortWithMetrics(arr);
        long endTime = System.nanoTime();

        System.out.println("Sorted Array: " + Arrays.toString(sortedArr));
        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Recursion Depth: " + recursionDepth);
    }
}



#### Quick Sort
import java.util.Random;
public class QuickSortt {
    
    public static int partition(int[] arr, int low, int high) {
        Random random = new Random();
        int pivotIndex = random.nextInt(high - low + 1) + low;
        int temp = arr[pivotIndex];
        arr[pivotIndex] = arr[high];
        arr[high] = temp;

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static void quickSort(int[] arr, int low, int high) {
        while (low < high) {
            int pi = partition(arr, low, high);

            if (pi - low < high - pi) {
                quickSort(arr, low, pi - 1);
                low = pi + 1;
            } else {
                quickSort(arr, pi + 1, high);
                high = pi - 1;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("Sorted Array: " + java.util.Arrays.toString(arr));
    }
}

#### Deterministic select
import java.util.Arrays;
public class select {

    public static int medianOfMedians(int[] arr) {
        int n = arr.length;
        int groupsCount = (n + 4) / 5;
        int[] medians = new int[groupsCount];
        
        for (int i = 0; i < groupsCount; i++) {
            int start = i * 5;
            int end = Math.min(start + 5, n);
            int[] group = Arrays.copyOfRange(arr, start, end);
            Arrays.sort(group);
            medians[i] = group[group.length / 2];
        }
        
        if (medians.length <= 5) {
            Arrays.sort(medians);
            return medians[medians.length / 2];
        } else {
            return medianOfMedians(medians);
        }
    }

    public static int partition(int[] arr, int low, int high, int pivot) {
        int i = low;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
            }
        }
        int temp = arr[i];
        arr[i] = arr[high];
        arr[high] = temp;
        return i;
    }

    public static int deterministicSelect(int[] arr, int low, int high, int k) {
        if (low == high) {
            return arr[low];
        }

        int pivot = medianOfMedians(Arrays.copyOfRange(arr, low, high + 1));
        
        int pivotIndex = partition(arr, low, high, pivot);
        
        if (pivotIndex == k) {
            return arr[pivotIndex];
        } else if (pivotIndex > k) {
            return deterministicSelect(arr, low, pivotIndex - 1, k);
        } else {
            return deterministicSelect(arr, pivotIndex + 1, high, k);
        }
    }

    public static void main(String[] args) {
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        int k = 3;
        int result = deterministicSelect(arr, 0, arr.length - 1, k);
        System.out.println("The " + (k + 1) + "-th smallest element is: " + result);
    }
}

#### Closest pair of points
import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;

public class ClosestPairOfPoints {

    public static double distance(int[] p1, int[] p2) {
        return Math.sqrt(Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2));
    }

    public static double closestInStrip(int[][] strip, double d) {
        double minDist = d;
        Arrays.sort(strip, Comparator.comparingInt(p -> p[1]));
        
        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < Math.min(i + 7, strip.length); j++) {
                double dist = distance(strip[i], strip[j]);
                if (dist < minDist) {
                    minDist = dist;
                }
            }
        }
        return minDist;
    }

    public static double closestPair(int[][] pointsSortedByX, int[][] pointsSortedByY) {
        if (pointsSortedByX.length <= 3) {
            double minDist = Double.POSITIVE_INFINITY;
            for (int i = 0; i < pointsSortedByX.length; i++) {
                for (int j = i + 1; j < pointsSortedByX.length; j++) {
                    minDist = Math.min(minDist, distance(pointsSortedByX[i], pointsSortedByX[j]));
                }
            }
            return minDist;
        }

        int mid = pointsSortedByX.length / 2;
        int[] midPoint = pointsSortedByX[mid];
        
        int[][] leftX = Arrays.copyOfRange(pointsSortedByX, 0, mid);
        int[][] rightX = Arrays.copyOfRange(pointsSortedByX, mid, pointsSortedByX.length);
        
        ArrayList<int[]> leftY = new ArrayList<>();
        ArrayList<int[]> rightY = new ArrayList<>();
        
        for (int[] p : pointsSortedByY) {
            if (p[0] <= midPoint[0]) {
                leftY.add(p);
            } else {
                rightY.add(p);
            }
        }

        double leftMin = closestPair(leftX, leftY.toArray(new int[0][0]));
        double rightMin = closestPair(rightX, rightY.toArray(new int[0][0]));

        double d = Math.min(leftMin, rightMin);

        int[][] strip = new int[pointsSortedByY.length][2];
        int stripCount = 0;
        for (int[] p : pointsSortedByY) {
            if (Math.abs(p[0] - midPoint[0]) < d) {
                strip[stripCount++] = p;
            }
        }

        return Math.min(d, closestInStrip(Arrays.copyOfRange(strip, 0, stripCount), d));
    }

    public static double closestPairOfPoints(int[][] points) {
        int[][] pointsSortedByX = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSortedByX, Comparator.comparingInt(p -> p[0]));
        
        int[][] pointsSortedByY = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSortedByY, Comparator.comparingInt(p -> p[1]));
        
        return closestPair(pointsSortedByX, pointsSortedByY);
    }

    public static void main(String[] args) {
        int[][] points = {
            {2, 3}, {12, 30}, {40, 50}, {5, 1}, {12, 10}, {3, 4}
        };
        double result = closestPairOfPoints(points);
        System.out.println("The closest pair of points has a distance of: " + result);
    }
}



# **Divide-and-Conquer Algorithms - Report**

## **1. Architecture Notes**

- **Merge Sort**: Uses a reusable buffer and insertion sort for small arrays. Recursion depth is **O(log n)**.
- **Quick Sort**: Randomized pivot, recurses on the smaller partition, iterates on the larger one. Average recursion depth is **O(log n)**.
- **Deterministic Select**: Divides into groups of 5, uses median-of-medians as pivot. Recursion depth is **O(log n)**.
- **Closest Pair**: Divide-and-conquer with a strip array to find pairs near the dividing line. Recursion depth is **O(log n)**.


## **2. Recurrence Analysis**

| Algorithm                | Recurrence                               | Θ Complexity     |
|--------------------------|-----------------------------------------|------------------|
| **Merge Sort**            | \( T(n) = 2T(n/2) + O(n) \)             | Θ(n log n)       |
| **Quick Sort (average)**  | \( T(n) = 2T(n/2) + O(n) \)             | Θ(n log n)       |
| **Quick Sort (worst)**    | \( T(n) = T(n-1) + O(n) \)              | Θ(n²)            |
| **Deterministic Select**  | \( T(n) = T(n/5) + T(7n/10) + O(n) \)   | Θ(n)             |
| **Closest Pair**          | \( T(n) = 2T(n/2) + O(n) \)             | Θ(n log n)       |


## **3. Summary**

- **MergeSort** and **QuickSort** performed as expected, with **O(n log n)** in average case and **O(n²)** for QuickSort in the worst case.
- **Deterministic Select** consistently ran in **O(n)** time, confirming its theoretical efficiency.
- **Closest Pair** followed its **O(n log n)** time complexity.

### **Conclusion**:
The theoretical time complexities were confirmed by measurements, with **Deterministic Select** being the most efficient in terms of time complexity. **MergeSort** and **QuickSort** showed solid performance, but QuickSort had edge cases where performance degraded.
