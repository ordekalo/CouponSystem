package CouponDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class B_ConnectToDataBase {

	public static void main(String[] args) {

		String DB_url = "jdbc:derby://localhost:1527/CouponServer;create=true";

		try (Connection con = DriverManager.getConnection(DB_url);) {
			System.out.println("connected to " + DB_url);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("disconnected from " + DB_url);
	}

}
