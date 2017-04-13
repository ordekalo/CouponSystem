package CouponDataBase;

// DriverLoading
public class A_LoadDataBase {

	public static void main(String[] args) {

		String driverName = "org.apache.derby.jdbc.ClientDriver";

		try {
			Class.forName(driverName);
			System.out.println("The Driver " + driverName + " is Loaded");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
