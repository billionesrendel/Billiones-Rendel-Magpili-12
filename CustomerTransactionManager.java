import java.util.Scanner;

public class CustomerTransactionManager {

    public static void main(String[] args) {
 Scanner scanner = new Scanner(System.in);

        String[] categories = {
            "Food", "Clothing", "Electronics"
        };

        System.out.print("Enter number of customers: ");
        int count = scanner.nextInt();
        scanner.nextLine();

        if (count <= 0) {
            System.out.println("Invalid number of customers!");
            scanner.close();
            return;
        }

        // 1D Arrays
        String[] transactionIDs = new String[count];
        double[] transactionAmounts = new double[count];

        // 2D Array for item category details
        double[][] itemDetails = new double[count][3];

        // Input customer transactions
        for (int i = 0; i < count; i++) {

            System.out.println("\n--- Customer " + (i + 1) + " ---");

            System.out.print("Enter Transaction ID: ");
            transactionIDs[i] = scanner.nextLine();

            System.out.print("Enter Total Purchase Amount: ");
            transactionAmounts[i] = scanner.nextDouble();

            for (int j = 0; j < 3; j++) {
                System.out.print("Enter amount for "
                        + categories[j] + ": ");
                itemDetails[i][j] = scanner.nextDouble();
            }

            scanner.nextLine();
        }

        // Display original transaction records
        System.out.println("\n===== CUSTOMER TRANSACTIONS =====");

        displayData(transactionIDs, transactionAmounts,
                    itemDetails, categories);

        // Sorting algorithm selection
        System.out.println("\n===== SORTING MENU =====");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Insertion Sort");
        System.out.println("3. Selection Sort");
        System.out.println("4. Merge Sort");

        System.out.print("Choose sorting algorithm: ");
        int choice = scanner.nextInt();

        System.out.println("\nSorting Order:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");

        System.out.print("Choose order: ");
        int order = scanner.nextInt();

        if (order != 1 && order != 2) {
            System.out.println("Invalid sorting order!");
            scanner.close();
            return;
        }

        boolean ascending = (order == 1);

        switch (choice) {

            case 1:
                bubbleSort(transactionIDs, transactionAmounts,
                           itemDetails, ascending);
                break;

            case 2:
                insertionSort(transactionIDs, transactionAmounts,
                              itemDetails, ascending);
                break;

            case 3:
                selectionSort(transactionIDs, transactionAmounts,
                              itemDetails, ascending);
                break;

            case 4:
                mergeSort(transactionIDs, transactionAmounts,
                          itemDetails, 0, count - 1, ascending);
                break;

            default:
                System.out.println("Invalid choice!");
                scanner.close();
                return;
        }

        // Display sorted transaction records
        System.out.println("\n===== SORTED TRANSACTIONS =====");

        displayData(transactionIDs, transactionAmounts,
                    itemDetails, categories);

        scanner.close();
    }

    // Display customer transaction summary
    public static void displayData(
            String[] IDs,
            double[] amounts,
            double[][] details,
            String[] categories) {

        for (int i = 0; i < IDs.length; i++) {

            System.out.println("\nCustomer " + (i + 1));
            System.out.println("Transaction ID: " + IDs[i]);

            System.out.printf("Total Purchase: %.2f%n",
                              amounts[i]);

            for (int j = 0; j < details[i].length; j++) {

                System.out.printf("%s: %.2f%n",
                        categories[j], details[i][j]);
            }
        }
    }

    // Swap customer records together
    public static void swap(
            String[] IDs,
            double[] amounts,
            double[][] details,
            int i, int j) {

        String tempID = IDs[i];
        IDs[i] = IDs[j];
        IDs[j] = tempID;

        double tempAmount = amounts[i];
        amounts[i] = amounts[j];
        amounts[j] = tempAmount;

        double[] tempDetails = details[i];
        details[i] = details[j];
        details[j] = tempDetails;
    }

    // Check sorting condition
    public static boolean shouldSwap(
            double a, double b, boolean ascending) {

        if (ascending) {
            return a > b;
        } else {
            return a < b;
        }
    }

    // 1. BUBBLE SORT
    public static void bubbleSort(
            String[] IDs,
            double[] amounts,
            double[][] details,
            boolean ascending) {

        int n = amounts.length;

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (shouldSwap(amounts[j],
                               amounts[j + 1], ascending)) {

                    swap(IDs, amounts, details, j, j + 1);
                }
            }
        }
    }

    // 2. INSERTION SORT
    public static void insertionSort(
            String[] IDs,
            double[] amounts,
            double[][] details,
            boolean ascending) {

        int n = amounts.length;

        for (int i = 1; i < n; i++) {

            int j = i;

            while (j > 0 &&
                   shouldSwap(amounts[j - 1],
                              amounts[j], !ascending)) {

                swap(IDs, amounts, details, j - 1, j);
                j--;
            }
        }
    }

    // 3. SELECTION SORT
    public static void selectionSort(
            String[] IDs,
            double[] amounts,
            double[][] details,
            boolean ascending) {

        int n = amounts.length;

        for (int i = 0; i < n - 1; i++) {

            int selected = i;

            for (int j = i + 1; j < n; j++) {

                if (ascending) {
                    if (amounts[j] < amounts[selected]) {
                        selected = j;
                    }
                } else {
                    if (amounts[j] > amounts[selected]) {
                        selected = j;
                    }
                }
            }

            if (selected != i) {
                swap(IDs, amounts, details, i, selected);
            }
        }
    }

    // 4. MERGE SORT
    public static void mergeSort(
            String[] IDs,
            double[] amounts,
            double[][] details,
            int left, int right,
            boolean ascending) {

        if (left < right) {

            int middle = left + (right - left) / 2;

            mergeSort(IDs, amounts, details,
                      left, middle, ascending);

            mergeSort(IDs, amounts, details,
                      middle + 1, right, ascending);

            merge(IDs, amounts, details,
                  left, middle, right, ascending);
        }
    }

    // Merge two sorted portions
    public static void merge(
            String[] IDs,
            double[] amounts,
            double[][] details,
            int left, int middle, int right,
            boolean ascending) {

        int n1 = middle - left + 1;
        int n2 = right - middle;

        String[] leftIDs = new String[n1];
        String[] rightIDs = new String[n2];

        double[] leftAmounts = new double[n1];
        double[] rightAmounts = new double[n2];

        double[][] leftDetails = new double[n1][];
        double[][] rightDetails = new double[n2][];

        for (int i = 0; i < n1; i++) {
            leftIDs[i] = IDs[left + i];
            leftAmounts[i] = amounts[left + i];
            leftDetails[i] = details[left + i];
        }

        for (int j = 0; j < n2; j++) {
            rightIDs[j] = IDs[middle + 1 + j];
            rightAmounts[j] = amounts[middle + 1 + j];
            rightDetails[j] = details[middle + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < n1 && j < n2) {

            boolean takeLeft;

            if (ascending) {
                takeLeft = leftAmounts[i] <= rightAmounts[j];
            } else {
                takeLeft = leftAmounts[i] >= rightAmounts[j];
            }

            if (takeLeft) {
                IDs[k] = leftIDs[i];
                amounts[k] = leftAmounts[i];
                details[k] = leftDetails[i];
                i++;
            } else {
                IDs[k] = rightIDs[j];
                amounts[k] = rightAmounts[j];
                details[k] = rightDetails[j];
                j++;
            }

            k++;
        }

        while (i < n1) {
            IDs[k] = leftIDs[i];
            amounts[k] = leftAmounts[i];
            details[k] = leftDetails[i];
            i++;
            k++;
        }

        while (j < n2) {
            IDs[k] = rightIDs[j];
            amounts[k] = rightAmounts[j];
            details[k] = rightDetails[j];
            j++;
            k++;
        }
    }
}
