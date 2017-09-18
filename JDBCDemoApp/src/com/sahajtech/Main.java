package com.sahajtech;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome Java JDBC ");
        try {
            String url = "jdbc:postgresql://localhost:5432/HRAdminIT";
            String username = "postgres";
            String password = "root";
            Connection connection = getPostgresConnetion(url,username,password);
            ArrayList<String> tablesName=  getAllTablesName(connection);
            System.out.println("Printing All the Tables name which are in database");
            for (String table : tablesName){
                System.out.println("----------------------------------------------------------------------"+table+"----------------------------------------------------------------------");

            }
            System.out.println("Printing All the Tables information of columns ,size ,datatypes etc.");

            getAllTableInfo(connection);

            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("class not found exception -" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("exception -" + e.getMessage());
            e.printStackTrace();

        }
    }

    public static Connection getPostgresConnetion(String connectionString, String username ,String password)throws Exception{
        Class.forName("org.postgresql.Driver");
        Connection connection  = DriverManager.getConnection(connectionString,username,password);
        System.out.println("Connection Establish successfully");
        return connection;
    }

    public static ArrayList getAllTablesName(Connection connection){
        DatabaseMetaData databaseMetaData = null;
        ArrayList<String> tables_name = new ArrayList<>();
        try {
            databaseMetaData = connection.getMetaData();
            ResultSet tables = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tables.next()){
                tables_name.add(tables.getString("TABLE_NAME"));
            }
            return tables_name;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getAllTableInfo(Connection connection) throws SQLException {
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
    }
}
