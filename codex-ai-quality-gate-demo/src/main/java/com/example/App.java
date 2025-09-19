package com.example;

import com.example.dao.UserDao;
import com.example.util.CryptoUtil;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Demo app running");
        UserDao dao = new UserDao("jdbc:sqlite::memory:");
        dao.init();
        dao.createUser("alice", "password123"); // hardcoded, smell
        String secret = CryptoUtil.encrypt("top-secret"); // uses hardcoded key (security smell)
        System.out.println("Encrypted: " + secret);
    }
}
