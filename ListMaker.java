import java.util.ArrayList;
import java.util.Scanner;

public class ListMaker {
    private static final Scanner console = new Scanner(System.in);
    private static final ArrayList<String> items = new ArrayList<>();

    public static void main(String[] args) {
        boolean quit = false;
        while (!quit) {
            displayMenuAndList();
            String choice = SafeInput.getRegExString(console, "Choose A, D, I, P, or Q: ", "[AaDdIiPpQq]");
            switch (choice.toUpperCase()) {
                case "A": addItem(); break;
                case "D": deleteItem(); break;
                case "I": insertItem(); break;
                case "P": printList(); break;
                case "Q":
                    if (SafeInput.getYNConfirm(console, "Are you sure you want to quit?")) {
                        quit = true;
                    }
                    break;
            }
        }
        System.out.println("Goodbye!");
        console.close();
    }

    private static void displayMenuAndList() {
        System.out.println("\n===== LIST MAKER =====");
        displayList();
        System.out.println("\nA - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("I - Insert an item");
        System.out.println("P - Print the list");
        System.out.println("Q - Quit");
    }

    private static void displayList() {
        if (items.isEmpty()) {
            System.out.println("(The list is empty)");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
        }
    }

    private static void displayNumberedList() {
        System.out.println("\nCurrent list:");
        displayList();
    }

    private static void addItem() {
        String item = SafeInput.getNonZeroLenString(console, "Type the item to add: ");
        items.add(item);
        System.out.println("Added: " + item);
    }

    private static void deleteItem() {
        if (items.isEmpty()) {
            System.out.println("The list is empty; there is nothing to delete.");
            return;
        }
        displayNumberedList();
        int number = SafeInput.getRangedInt(console, "Enter the item number to delete: ", 1, items.size());
        System.out.println("Deleted: " + items.remove(number - 1));
    }

    private static void insertItem() {
        displayNumberedList();
        int location = SafeInput.getRangedInt(console, "Enter the position to insert at (1 means first): ", 1, items.size() + 1);
        String item = SafeInput.getNonZeroLenString(console, "Type the item to insert: ");
        items.add(location - 1, item);
        System.out.println("Inserted: " + item);
    }

    private static void printList() {
        System.out.println("\nYour list:");
        displayList();
    }
}

class SafeInput {
    public static String getRegExString(Scanner console, String prompt, String pattern) {
        while (true) {
            System.out.print(prompt);
            String answer = console.nextLine().trim();
            if (answer.matches(pattern)) return answer;
            System.out.println("Please enter one of the listed choices.");
        }
    }

    public static int getRangedInt(Scanner console, String prompt, int low, int high) {
        while (true) {
            System.out.print(prompt);
            String answer = console.nextLine().trim();
            try {
                int value = Integer.parseInt(answer);
                if (value >= low && value <= high) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Please enter a whole number from " + low + " to " + high + ".");
        }
    }

    public static boolean getYNConfirm(Scanner console, String prompt) {
        return getRegExString(console, prompt + " (Y/N): ", "[YyNn]").equalsIgnoreCase("Y");
    }

    public static String getNonZeroLenString(Scanner console, String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = console.nextLine().trim();
            if (!answer.isEmpty()) return answer;
            System.out.println("Please enter at least one character.");
        }
    }
}