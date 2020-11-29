package cs.whut.common;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;

public class DataProcessing {

    private static boolean connectToDB = false;
    static Hashtable<String, User> users;
    static Hashtable<String, Doc> docs;

    static {
        users = new Hashtable<>();
        users.put("jack", new Operator("jack", "123", "operator"));
        users.put("rose", new Browser("rose", "123", "browser"));
        users.put("kate", new Administrator("kate", "123", "administrator"));
        Init();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        docs = new Hashtable<>();
        docs.put("0001", new Doc("0001", "jack", timestamp, "Doc Source Java", "Doc.java"));
    }

    public static void Init() {
        // connect to database
        // update database connection status
        //connectToDB = Math.random() > 0.2;
    }

    public static Doc searchDoc(String ID) throws SQLException {
        if (docs.containsKey(ID)) {
            return docs.get(ID);
        }
        return null;
    }

    public static Enumeration<Doc> getAllDocs() throws SQLException {
        return docs.elements();
    }

    public static boolean insertDoc(String ID, String creator, Timestamp timestamp, String description, String filename) throws SQLException {
        Doc doc;

        if (docs.containsKey(ID)) {
            System.out.println("File ID cannot be same!");
            return false;
        } else {
            doc = new Doc(ID, creator, timestamp, description, filename);
            docs.put(ID, doc);
            return true;
        }
    }

    public static User searchUser(String name) throws SQLException {
        //if (!connectToDB)
        //    throw new SQLException("Not Connected to Database");

        //double ranValue = Math.random();
        //if (ranValue > 0.7)
        //    throw new SQLException("Error in executing Query");

        if (users.containsKey(name)) {
            return users.get(name);
        }
        return null;
    }

    public static User searchUser(String name, String password) throws SQLException {
        //if (!connectToDB) {
        //    throw new SQLException("Not Connected to Database");
        //}
        //double ranValue = Math.random();
        //if (ranValue > 0.7) {
        //    throw new SQLException("Error in executing Query");
        //}

        if (users.containsKey(name)) {
            User temp = users.get(name);
            if ((temp.getPassword()).equals(password))
                return temp;
        }
        return null;
    }

    public static Enumeration<User> getAllUser() throws SQLException {
        //if (!connectToDB)
        //    throw new SQLException("Not Connected to Database");

        //double ranValue = Math.random();
        //if (ranValue > 0.7)
        //    throw new SQLException("Error in executing Query");

        return users.elements();
    }

    public static boolean update(String name, String password, String role) throws SQLException {
        User user;
        //if (!connectToDB)
        //    throw new SQLException("Not Connected to Database");

        //double ranValue = Math.random();
        //if (ranValue > 0.7)
        //    throw new SQLException("Error in executing Update");

        if (users.containsKey(name)) {
            if (role.equalsIgnoreCase("administrator"))
                user = new Administrator(name, password, role);
            else if (role.equalsIgnoreCase("operator"))
                user = new Operator(name, password, role);
            else
                user = new Browser(name, password, role);
            users.put(name, user);
            return true;
        } else
            return false;
    }

    public static boolean insert(String name, String password, String role) throws SQLException {
        User user;

        //if (!connectToDB)
        //    throw new SQLException("Not Connected to Database");

        //double ranValue = Math.random();
        //if (ranValue > 0.7)
        //    throw new SQLException("Error in executing Insert");

        if (users.containsKey(name))
            return false;
        else {
            if (role.equalsIgnoreCase("administrator"))
                user = new Administrator(name, password, role);
            else if (role.equalsIgnoreCase("operator"))
                user = new Operator(name, password, role);
            else
                user = new Browser(name, password, role);
            users.put(name, user);
            return true;
        }
    }

    public static boolean delete(String name) throws SQLException {
        //if (!connectToDB)
        //    throw new SQLException("Not Connected to Database");

        //double ranValue = Math.random();
        //if (ranValue > 0.7)
        //    throw new SQLException("Error in executing Delete");

        if (users.containsKey(name)) {
            users.remove(name);
            return true;
        } else
            return false;

    }

    public static void disconnectFromDB() throws SQLException {
        if (connectToDB) {
            // close Statement and Connection
            try {
                //        if (Math.random() > 0.7)
                //            throw new SQLException("Error in disconnecting DB");
                //    } catch (SQLException sqlException) {
                //        sqlException.printStackTrace();
            } finally {
                connectToDB = false;
            }
        }
    }

    public static void main(String[] args) {

    }

}