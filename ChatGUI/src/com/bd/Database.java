package com.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	String BDD = "iut";
	String url = "jdbc:oracle:thin:@iutdoua-oracle.univ-lyon1.fr:1521:cdb1/" + BDD;
	String user = "p2111646";
	String passwd = "624695";
	String lastusr = "select max(NUM_UTILISATEUR) FROM UTILISATEUR;" + 1;
	public void main(String[] args) {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			String query = "SELECT NUMSTAGIAIRE FROM RESULTCONT;";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				System.out.println("id " + id + " name " + name);

			}
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erreur");
			System.exit(0);
		}

	}
	
	public void insertRecord(String fullName, String password) throws SQLException {

        // Step 1: Establishing a Connection and 
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
            .getConnection(url, user, passwd);

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO UTILISATEUR (LOGIN,PASSWORD) VALUES ('"+ lastusr +"','"+ fullName +"','"+ password +"'); ")) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
    }
	
	
	 public static void printSQLException(SQLException ex) {
	        for (Throwable e: ex) {
	            if (e instanceof SQLException) {
	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
	                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
	                System.err.println("Message: " + e.getMessage());
	                Throwable t = ex.getCause();
	                while (t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }

}
