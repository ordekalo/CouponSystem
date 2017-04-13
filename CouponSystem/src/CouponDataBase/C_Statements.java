package CouponDataBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/******************************************************************************
 * THIS IS A CLASS CreatingDataBase, THAT CREATE ALL THE TABLES IN THE DATABASE.
 * EVERY TIME THAT WE RUN THIS PROGRAM, ALL THE TABLE WILL BE DELETED FROM THE
 * DATABASE, AND WILL BE CREATED AGAIN.
 ******************************************************************************/

public class C_Statements {

	public static String Name_Company = "COMPANY";
	public static String Name_Customer = "CUSTOMER";
	public static String Name_Coupon = "COUPON";
	public static String Name_Customer_Coupon = "CUSTOMER_COUPON";
	public static String Name_Company_Coupon = "COMPANY_COUPON";
	
	// Setting the Sources.
	public static String url = "jdbc:derby://localhost:1527/CouponServer";
	public static String database_txt = "Database_URL.txt";
	public static String readed_database_url = "null"; // What the FileReader read from
	// the file.
	public static String folder_name = "Database_Folder";
	public static String directory_address = folder_name+"/"+database_txt;
	
	public static void main(String[] args) {
		

		// GET A CONNECTION TO DATABASE. WE READ THE DATABASE URL FROM A FILE
		// NAME: DataBaseURL.txt
		File database_folder = new File(folder_name);
		try { // Checks if the folder exists.
			database_folder.mkdir();
			if (database_folder.exists()) {
				System.out.println(database_folder + " was succesfully created.");
			} else {
				System.out.println(database_folder + " was existed.");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		// Writing the wanted url to the file
		Scanner sc = new Scanner(System.in);
		System.out.println("Please Enter The Server Url. For Default Press Enter.");
		try (FileWriter out = new FileWriter(directory_address);) {
			if (sc.nextLine().isEmpty()) {
				System.out.println("Default was selected");
				out.write(url);
				System.out.println(url + " Was Written to " + database_txt);
			} else {
				url = sc.nextLine();
				out.write(url);
				System.out.println(url + " Was Written to " + database_txt);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		sc.close();
		// Reading the url from the file
		try (BufferedReader reader = new BufferedReader(new FileReader(directory_address));) {
			readed_database_url = reader.readLine();
			System.out.println("The Source Url of The Databse is: " + readed_database_url);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// CREATE A CONNECTION TO DATABASE
		try (Connection con = DriverManager.getConnection(readed_database_url)) {

			System.out.println("CONNECTED TO: " + readed_database_url + "\n");

			// CREATE AN ARRAY LIST TO DELETE ALL TABLES IN DB, AND ANATHER ONE
			// FOR CREATE THE TABLES.
			ArrayList<String> tables = new ArrayList<>();
			ArrayList<String> name_tables = new ArrayList<>();

			// ADD THE DELETED TABLES TO THE ARRAY LIST DELETETABLES !
			name_tables.add(Name_Company);
			name_tables.add(Name_Customer);
			name_tables.add(Name_Coupon);
			name_tables.add(Name_Company_Coupon);
			name_tables.add(Name_Customer_Coupon);

			// THIS FOR LOOP LOOPS OVRE THE DELETE TABLES ARRAY, AND REMOVE ALL
			// THE TABLES FROM THE DATABASE
			System.out.println("Delete All Tables :");
			System.out.println("********************");
			Statement stmt = con.createStatement();
			for (int i = 0; i < name_tables.size(); i++) {
				try {
					stmt.executeUpdate("Drop table " + name_tables.get(i));
					System.out.println(name_tables.get(i) + " IS DELETED FROM THE DataBase !\n");
				} catch (SQLException e1) {

				}
				// CREATING THE TABLES FOR THE DATABASE
			}
			// ADD THE CREATED TABLES TO THE ARRAY LIST TABLES
			String Company = "create table " + Name_Company
					+ "(ID BIGINT PRIMARY KEY,COMP_NAME VARCHAR(30),PASSWORD VARCHAR(8),EMAIL VARCHAR(40))";
			String Customer = "create table " + Name_Customer
					+ "(ID BIGINT PRIMARY KEY,CUST_NAME VARCHAR(30),PASSWORD VARCHAR(8))";
			String Coupon = "create table " + Name_Coupon
					+ "(ID BIGINT PRIMARY KEY,TITLE VARCHAR(30),START_DATE DATE,END_DATE DATE,AMOUNT INTEGER,TYPE VARCHAR(30),MESSAGE VARCHAR (50),PRICE DOUBLE,IMAGE VARCHAR(50))";
			String Company_Coupon = "create table " + Name_Company_Coupon
					+ "(Company_ID BIGINT,Coupon_ID BIGINT, PRIMARY KEY(Company_ID,Coupon_ID))";
			String Customer_Coupon = "create table " + Name_Customer_Coupon
					+ "(Customer_ID BIGINT,Coupon_ID BIGINT, PRIMARY KEY(Customer_ID,Coupon_ID))";

			tables.add(Company);
			tables.add(Customer);
			tables.add(Coupon);
			tables.add(Company_Coupon);
			tables.add(Customer_Coupon);

			// THIS FOR LOOP LOOPS OVRE THE tables ARRAY, AND ADDING A NEW
			// TABLES TO THE DATABASE
			System.out.println("Creating New Tables :");
			System.out.println("********************");
			for (int i = 0; i < tables.size(); i++) {
				try {
					stmt.executeUpdate(tables.get(i));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println(tables.get(i) + " WAS CREATED !\n");
			}
			System.out.println("********************");

			System.out.println("Disconnected From " + readed_database_url);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}
}
