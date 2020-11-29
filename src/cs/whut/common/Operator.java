package cs.whut.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Operator extends User {

    static final String infos = "****Welcome to menu of Operator****\n\t" +
            "1.Upload document\n\t" +
            "2.Download document\n\t" + "3.Files list\n\t" + "4.Change password\n\t" + "5.Exit\n" +
            "**********************************";
    static final String tip_menu = "Please select menu:";

    private Scanner scanner = new Scanner(System.in);

    public Operator(String name, String password, String role) {
        super(name, password, role);
    }

    @Override
    public void showMenu() throws SQLException, IOException {
        System.out.println(infos);
        System.out.print(tip_menu);
        this.run();
    }

    private void uploadFile() {
        System.out.println("Upload document.");
        System.out.print("Input the path of the document:");
        String filePath = scanner.nextLine();
        System.out.print("Input the number of the document:");
        String fileNumber = scanner.nextLine();
        System.out.print("Input the description of the document:");
        String fileDescription = scanner.nextLine();
        if (super.uploadFile(filePath, fileNumber, fileDescription)) {
            System.out.println("Upload succeed!");
        } else {
            System.out.println("Something wrong happened when uploading document.");
        }

    }

    public void run() throws SQLException, IOException {
        String order;
        while ((order = scanner.nextLine()) != null) {
            switch (order) {
                case "1":                                                                                           //operator option 1
                    uploadFile();
                    break;
                case "2":                                                                                           //operator option 2
                    downloadFile();
                    break;
                case "3":                                                                                           //operator option 3
                    super.showFileList();
                    break;
                case "4":                                                                                           //operator option 4
                    this.changePassword();
                    break;
                case "5":                                                                                           //operator option 5
                    super.exitSystem();
                    break;
                default:
                    System.out.println("Role Operator does NOT have such option!");
                    break;
            }
            this.showMenu();
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
}
