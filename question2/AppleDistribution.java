import java.util.*;

public class AppleDistribution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> apples = new ArrayList<>();

        // Input apple weights
        while (true) {
            System.out.print("Enter apple weight in grams (-1 to stop): ");
            int weight = scanner.nextInt();
            if (weight == -1) break;
            apples.add(weight);
        }

        // Sort apples in descending order
        Collections.sort(apples, Collections.reverseOrder());

        // Define shares
        int total = 100;
        int[] shares = {50, 30, 20};
        String[] names = {"Ram", "Sham", "Rahim"};

        // Distribute apples
        List<List<Integer>> distribution = distributeApples(apples, shares);

        // Print results
        System.out.println("Distribution Result:");
        for (int i = 0; i < names.length; i++) {
            System.out.print(names[i] + ": ");
            System.out.println(distribution.get(i).toString().replaceAll("[\\[\\]]", ""));
        }
    }

    public static List<List<Integer>> distributeApples(List<Integer> apples, int[] shares) {
        List<List<Integer>> distribution = new ArrayList<>();
        for (int i = 0; i < shares.length; i++) {
            distribution.add(new ArrayList<>());
        }

        int totalWeight = apples.stream().mapToInt(Integer::intValue).sum();
        int[] targetWeights = new int[shares.length];
        for (int i = 0; i < shares.length; i++) {
            targetWeights[i] = totalWeight * shares[i] / 100;
        }

        int[] currentWeights = new int[shares.length];

        for (int apple : apples) {
            int bestIndex = 0;
            double bestRatio = Double.MAX_VALUE;

            for (int i = 0; i < shares.length; i++) {
                double ratio = (double) (currentWeights[i] + apple) / targetWeights[i];
                if (ratio < bestRatio) {
                    bestRatio = ratio;
                    bestIndex = i;
                }
            }

            distribution.get(bestIndex).add(apple);
            currentWeights[bestIndex] += apple;
        }

        return distribution;
    }
}
