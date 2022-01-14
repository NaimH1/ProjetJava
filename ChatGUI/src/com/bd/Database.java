package com.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

	public static void main(String[] args) {

		String BDD = "iut";
		String url = "jdbc:oracle:thin:@iutdoua-oracle.univ-lyon1.fr:1521:cdb1/" + BDD;
		String user = "p2111646";
		String passwd = "624695";

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

}
