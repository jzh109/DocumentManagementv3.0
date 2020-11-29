package cs.whut.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Browser extends User {

    static final String infos = "****Welcome to menu of Browser****\n\t" +
            "1.Download document\n\t" + "2.Files list\n\t" + "3.Change password\n\t" + "4.Exit\n" +
            "**********************************";
    static final String tip_menu = "Please select menu:";
    private Scanner scanner = new Scanner(System.in);

    public Browser(String name, String password, String role) {
        super(name, password, role);
    }


    @Override
    public void showMenu() throws SQLException, IOException {
        System.out.println(infos);
        System.out.print(tip_menu);
        this.run();
    }

    public void run() throws SQLException, IOException {
        String order;
        while ((order = scanner.nextLine()) != null) {
            switch (order) {
                case "1":                                                                                           //browser option 1
                    downloadFile();
                    break;
                case "2":                                                                                           //browser option 2
                    super.showFileList();
                    break;
                case "3":                                                                                           //browser option 3
                    changePassword();
                    break;
                case "4":                                                                                           //browser option 4
                    super.exitSystem();
                    break;
                default:
                    System.out.println("Role Browser does NOT have such option!");
                    break;
            }
            this.showMenu();
        }
    }

    private void downloadFile() throws IOException, SQLException {
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

    private void changePassword() throws SQLException, IOException {
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
}
