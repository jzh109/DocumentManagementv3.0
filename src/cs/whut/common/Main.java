package cs.whut.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, IOException {
        welcomeMenu();
    }

    public static void welcomeMenu() throws SQLException, IOException {
        String tip_system = "Document System";
        String tip_menu = "Please select menu:";
        String tip_exit = "System has exited, thanks for utilizing!";
        String infos = "****Welcome to " + tip_system + "****\n\t" +
                "1.Login\n\t2.Exit\n" +
                "**************************";
        System.out.println(infos);
        System.out.print(tip_menu);
        String order = scanner.nextLine();
        if (order.equals("2")) {
            try {
                DataProcessing.disconnectFromDB();
                System.out.println(tip_exit);
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
        } else if (order.equals("1")) {
            login();
        } else {
            System.out.println("User does NOT have such option!");
        }
    }

    public static void run(User user) throws IOException, SQLException {
        user.showMenu();
    }

    private static void login() throws IOException, SQLException {
        System.out.print("Please input username:");
        String name = scanner.nextLine().trim();
        System.out.print("Please input password:");
        String password = scanner.nextLine();
        if (!DataProcessing.users.containsKey(name)) {
            System.out.println("User doesn't exist.");
            welcomeMenu();
        } else {
            try {
                User user = DataProcessing.searchUser(name, password);
                if (user != null) {
                    run(user);
                } else {
                    System.out.println("Username or password is incorrect.");
                    welcomeMenu();
                }
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
                welcomeMenu();
            }
        }
    }
}