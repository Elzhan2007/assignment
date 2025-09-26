// Closest pair of points
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