// Merge Sort with Time and Recursion Depth Measurement
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