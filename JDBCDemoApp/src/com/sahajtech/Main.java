package com.sahajtech;

import java.sql.*;

@SuppressWarnings("ALL")
public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome Java JDBC ");
        try {
            System.out.println("Step 1 - Loading Driver ");
            Class.forName("org.postgresql.Driver");
            System.out.println("Loading Driver successfully");
            System.out.println("Step 2 - Connecting Postgres database");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HRAdminIT", "postgres", "root");
            System.out.println("Connection successfully");
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet tables = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tables.next()) {
                String tablename = tables.getString("TABLE_NAME");
                System.out.println("----------------------------------------------------------------" + tablename + "----------------------------------------------------------------");
                ResultSet resultSet = databaseMetaData.getColumns(null, null, tablename, null);
                int i = 1;
                while (resultSet.next()) {
                    System.out.println(i +
                            " Table Schema - "+  resultSet.getString("TABLE_SCHEM")
                            + "\tTable Name - "+ resultSet.getString("TABLE_NAME")
                            + "\tcolumn Name - "+ resultSet.getString("COLUMN_NAME")
                            + "\tType Name - "+ resultSet.getString("TYPE_NAME")
                            + "\tColumn Size - "+ resultSet.getInt("COLUMN_SIZE")
                            + "\tNullable - "+ resultSet.getInt("NULLABLE"));
                    i++;
                }
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("class not found exception -" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("exception -" + e.getMessage());
            e.printStackTrace();

        }
    }

}
