package cs.whut.common;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;


public abstract class User {
    private String name;
    private String password;
    private String role;

    String uploadpath = ".\\uploadfile\\";
    String downloadpath = ".\\downloadfile\\";

    User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public boolean changeSelfInfo(String password) throws SQLException {
        if (DataProcessing.update(name, password, role)) {
            this.password = password;
            System.out.println("Password has been changed successfully!");
            return true;
        } else
            return false;
    }

    public boolean downloadFile(String ID) throws SQLException {
        try {
            byte[] buffer = new byte[1024];
            Doc doc = DataProcessing.searchDoc(ID);

            if (doc == null) {
                return false;
            }
            File inFile = new File(uploadpath + doc.getFilename());
            File outFile = new File(downloadpath + doc.getFilename());
            if (!outFile.createNewFile()) {
                System.out.println("File has already existed.");
                return false;
            }

            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(inFile));
            BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(outFile));


            int byteRead = 0;
            while ((byteRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, byteRead);
            }

            inStream.close();
            outStream.close();

            return true;
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

        return false;
    }


    public boolean uploadFile(String filePath, String fileNumber, String fileDescription) {
        try {
            if (DataProcessing.searchDoc(fileNumber) != null) {
                System.out.println("Document ID has existed.");
                return false;
            }

            File inFile = new File(filePath);
            File outFile = new File(uploadpath + inFile.getName());

            if (!outFile.createNewFile()) {
                System.out.println("Create file failed.");
                return false;
            }

            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(inFile));
            BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(outFile));

            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, byteRead);
            }

            inStream.close();
            outStream.close();

            if (DataProcessing.insertDoc(fileNumber, filePath, new Timestamp(System.currentTimeMillis()),
                    fileDescription, inFile.getName())) {
                System.out.println("Upload file succeed!");
            } else {
                System.out.println("There is something wrong when uploading file.");
            }

        } catch (SQLException | IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return true;
    }

    public void showFileList() throws SQLException {
        Enumeration<Doc> e = DataProcessing.getAllDocs();
        Doc tempDoc;
        while (e.hasMoreElements()) {
            tempDoc = e.nextElement();
            System.out.println("ID: " + tempDoc.getID() + "\tCreator: " + tempDoc.getCreator() + "\tTime: " +
                    tempDoc.getTimestamp() + "\tFilename: " + tempDoc.getFilename());
            System.out.println("Description: " + tempDoc.getDescription());
        }
    }

    public abstract void showMenu() throws SQLException, IOException;

    public void exitSystem() {
        System.out.println("System exits, thanks for utilizing!");
        System.exit(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
