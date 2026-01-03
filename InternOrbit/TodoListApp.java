import java.io.*;
import java.util.*;
class Task {
    String title;
    String category;
    String dueDate;
    boolean isCompleted;

    Task(String title, String category, String dueDate, boolean isCompleted) {
        this.title = title;
        this.category = category;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        return title + "," + category + "," + dueDate + "," + isCompleted;
    }

    static Task fromString(String line) {
        String[] parts = line.split(",");
        return new Task(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3]));
    }
}

public class TodoListApp {

    static final String FILE_NAME = "tasks.txt";
    static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasks();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- TO-DO LIST MENU ---");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Remove Task");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addTask(sc);
                case 2 -> viewTasks();
                case 3 -> markTaskCompleted(sc);
                case 4 -> removeTask(sc);
                case 5 -> {
                    saveTasks();
                    System.out.println("Tasks saved. Exiting application.");
                }
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 5);

        sc.close();
    }

    static void addTask(Scanner sc) {
        System.out.print("Enter task title: ");
        String title = sc.nextLine();

        System.out.print("Enter category: ");
        String category = sc.nextLine();

        System.out.print("Enter due date (dd-mm-yyyy): ");
        String dueDate = sc.nextLine();

        tasks.add(new Task(title, category, dueDate, false));
        System.out.println("Task added successfully!");
    }

    static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        System.out.println("\n--- TASK LIST ---");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            System.out.println((i + 1) + ". " + t.title +
                    " | Category: " + t.category +
                    " | Due: " + t.dueDate +
                    " | Status: " + (t.isCompleted ? "Completed" : "Pending"));
        }
    }

    static void markTaskCompleted(Scanner sc) {
        viewTasks();
        if (tasks.isEmpty()) return;

        System.out.print("Enter task number to mark completed: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).isCompleted = true;
            System.out.println("Task marked as completed!");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    static void removeTask(Scanner sc) {
        viewTasks();
        if (tasks.isEmpty()) return;

        System.out.print("Enter task number to remove: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Task removed successfully!");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    static void loadTasks() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(Task.fromString(line));
            }
        } catch (IOException ignored) {
        }
    }

    static void saveTasks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task t : tasks) {
                bw.write(t.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }
}
