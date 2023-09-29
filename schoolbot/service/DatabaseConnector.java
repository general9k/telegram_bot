package ru.schoolbot.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {

    private static Connection conn;

    public static void connect() {
        try {
            // создание соединения с базой данных
            conn = DriverManager.getConnection("jdbc:sqlite:answers.db");

            // создание таблицы answers, если ее еще нет
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS answers (user_id INTEGER, answer TEXT)");

            // создание таблицы messages, если ее еще нет
            stmt.execute("CREATE TABLE IF NOT EXISTS messages (user_id INTEGER, message TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveMessage(int userId, String message) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO messages (user_id, message) VALUES (?, ?)");
            pstmt.setInt(1, userId);
            pstmt.setString(2, message);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getMessages(int userId) {
        List<String> messages = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM messages WHERE user_id=?");
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                messages.add(rs.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

}
