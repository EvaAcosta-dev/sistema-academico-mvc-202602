package com.uniajc;

import com.uniajc.db.DatabaseConexion;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido al Sistema Académico!");

        DatabaseConexion.getConnection();
    }
}