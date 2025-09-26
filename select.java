// Deterministic select
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
