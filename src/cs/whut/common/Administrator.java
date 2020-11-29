package cs.whut.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

public class Administrator extends User {

    static final String infos = "****Welcome to menu of Administrator****\n\t" +
            "1.Edit user\n\t" + "2.Delete user\n\t" + "3.Create user\n\t" +
            "4.List all users\n\t" +
            "5.Download document\n\t" + "6.Files list\n\t" + "7.Change password\n\t" + "8.Exit\n" +
            "**********************************";
    static final String tip_menu = "Please select menu:";
    private Scanner scanner = new Scanner(System.in);

    public Administrator(String name, String password, String role) {
        super(name, password, role);
    }

    @Override
    public void showMenu() throws IOException, SQLException {
        System.out.println(infos);
        System.out.print(tip_menu);
        this.run();
    }

    public void run() throws SQLException, IOException {
        String order;
        while ((order = scanner.nextLine()) != null) {
            switch (order) {
                case "1":                                                                                           //administrator option 1
                    changeUserInfo();
                    break;
                case "2":                                                                                           //administrator option 2
                    delUser();
                    break;
                case "3":                                                                                           //administrator option 3
                    addUser();
                    break;
                case "4":                                                                                           //administrator option 4
                    listUser();
                    break;
                case "5":                                                                                           //administrator option 5
                    downloadFile();
                    break;
                case "6":                                                                                           //administrator option 6
                    showFileList();
                    break;
                case "7":                                                                                           //administrator option 7
                    changeSelfInfo();
                    break;
                case "8":                                                                                           //administrator option 8
                    exitSystem();
                    break;
                default:
                    System.out.println("No such option!");
                    break;
            }
            showMenu();
        }
    }

    private void downloadFile() throws SQLException {
        System.out.println("Download file.");
        System.out.print("Please input the number of document:");
        String fileID = scanner.nextLine();
        if (super.downloadFile(fileID)) {
            System.out.println("Download succeed!");
        } else {
            System.out.println("Something wrong happened when downloading document." +
                    fileID + ".");
        }

    }

    private void changeUserInfo() throws SQLException, IOException {
        System.out.println("Edit user.");
        System.out.print("Input username:");
        String username = scanner.nextLine();
        System.out.print("Input password:");
        String password = scanner.nextLine();
        System.out.print("Input role:");
        String role = scanner.nextLine();
        try {
            if (DataProcessing.update(username, password, role)) {
                System.out.println("Succeed!");
            } else {
                System.out.println("Something wrong happened when changing " + username + ".");
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            this.showMenu();
        }

    }

    public void delUser() throws SQLException, IOException {
        System.out.println("Delete user.");
        System.out.print("Input username:");
        String username = scanner.nextLine();
        try {
            if (DataProcessing.delete(username)) {
                System.out.println("Succeed!");
            } else {
                System.out.println("Something wrong happened when deleting " + username + ".");
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            this.showMenu();
        }

    }

    private void changeSelfInfo() throws SQLException, IOException {
        System.out.println("Change your own password.");
        System.out.print("Please input new password:");
        String newPassword = scanner.nextLine();
        try {
            if (super.changeSelfInfo(newPassword)) {
                System.out.println("Password change succeed!");
            } else {
                System.out.println("Something wrong happened when changing password.");
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            this.showMenu();
        }

    }

    private void addUser() throws SQLException, IOException {
        System.out.println("Create user.");
        System.out.print("Input username:");
        String username = scanner.nextLine();
        System.out.print("Input password:");
        String password = scanner.nextLine();
        System.out.print("Input role:");
        String role = scanner.nextLine();
        try {
            if (DataProcessing.insert(username, password, role)) {
                System.out.println("Succeed!");
            } else {
                System.out.println("Something wrong happened when creating " + username + ".");
            }
        } catch (SQLException e) {
            e.getLocalizedMessage();
            this.showMenu();
        }
    }

    private void listUser() throws SQLException, IOException {
        System.out.println("List all users.");
        try {
            Enumeration<User> users = DataProcessing.getAllUser();
            while (users.hasMoreElements()) {
                User temp = users.nextElement();
                System.out.println("Name: " + temp.getName() + "\t\tPassword: " + temp.getPassword() +
                        "\t\tRole: " + temp.getRole());
            }
        } catch (SQLException e) {
            e.getLocalizedMessage();
            this.showMenu();
        }
    }
}