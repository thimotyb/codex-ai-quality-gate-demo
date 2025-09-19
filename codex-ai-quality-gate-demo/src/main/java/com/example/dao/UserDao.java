package com.example.dao;

import java.sql.*;

public class UserDao {
    private final String url;

    public UserDao(String url) {
        this.url = url;
    }

    public void init() throws SQLException {
        Connection c = DriverManager.getConnection(url); // missing try-with-resources
        Statement s = c.createStatement();
        s.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)");
        s.close();
        c.close();
    }

    // INTENTIONAL SECURITY BUG: SQL concatenation (SQL Injection)
    public void createUser(String username, String password) throws SQLException {
        Connection c = DriverManager.getConnection(url);
        Statement s = c.createStatement();
        String sql = "INSERT INTO users(username, password) VALUES('" + username + "', '" + password + "')";
        s.executeUpdate(sql);
        s.close();
        c.close();
    }

    // INTENTIONAL SECURITY BUG: dangerous command execution with user input
    public void runCommand(String userInput) throws Exception {
        Runtime.getRuntime().exec("sh -c " + userInput); // command injection
    }

    // INTENTIONAL CODE SMELL: returns mutable internal array (not present here, but example method)
    public String[] listToArray(java.util.List<String> list) {
        return list.toArray(new String[0]); // fine; comment kept for teaching
    }
}
