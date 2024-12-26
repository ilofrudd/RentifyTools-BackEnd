package org.rentifytools.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "qwerty";
        System.out.println(encoder.encode(password));
    }
}
