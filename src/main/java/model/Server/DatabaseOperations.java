package model.Server;

import java.sql.*;
import java.util.HashMap;

public class DatabaseOperations {

    private String url;

    public DatabaseOperations() {
        try {
            Class.forName("org.sqlite.JDBC");
            // db parameters
            this.url = "jdbc:sqlite:Database/dictionary.sqlite";
        } catch (ClassNotFoundException e) {
            System.out.println("Database connect fail");
        }
    }

    public boolean insert(String word, String meanings){
        String sql = "INSERT INTO Dictionary(word, meanings) VALUES(?,?)";
        try {
            // create a connection to the database
            Connection conn = DriverManager.getConnection(this.url);
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, word);
            pstmt.setString(2, meanings);
            pstmt.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(String word) {
        String sql = "DELETE FROM Dictionary WHERE word=?";

        try {
            // create a connection to the database
            Connection conn = DriverManager.getConnection(this.url);
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, word);
            pstmt.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean searchIsWordExist(String word) {
        String sql = "SELECT word FROM Dictionary WHERE word=?";

        try {
            // create a connection to the database
            Connection conn = DriverManager.getConnection(this.url);
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();

            conn.close();
            if(rs.next()) {return true;}
            else {return false;}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public HashMap<String, String> searchWordMeanings(String word) {
        String sql = "SELECT * FROM Dictionary WHERE word=?";
        HashMap<String, String> result = new HashMap<String, String>();

        try {
            // create a connection to the database
            Connection conn = DriverManager.getConnection(this.url);
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                String rWord = rs.getString("word");
                String rMeanings = rs.getString("meanings");
                result.put(rWord, rMeanings);
            }
            conn.close();
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
