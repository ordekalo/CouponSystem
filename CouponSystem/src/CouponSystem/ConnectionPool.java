package CouponSystem;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

import CouponDataBase.C_Statements;

public class ConnectionPool {
	/**
	 * SINGLETON
	 */
	// set the maximum connection up to 10
	public static final int MAX_CONNECTIONS = 10;

	// create two Hashset for the used and unused connection
	private HashSet<Connection> connections = new HashSet<>();

	private static ConnectionPool instance = new ConnectionPool();

	public static ConnectionPool getInstance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	/*******************************
	 * PRIVATE CONCTRACTOR TO SET THE CONNECTION POOL CLASS TO A SINGLETON. THE
	 * CONCTRACTOR FOR LOOP ADDING A CONNECTION TO THE CONNECTIONPOLL SET.
	 ******************************/
	private ConnectionPool() {
		try {
			for (int i = 0; i < MAX_CONNECTIONS; i++) {
				connections.add(createConnection());
			}
			System.out.println("****[ All Connection are added to the Connection Pool ]******");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/*******************************
	 * createConnection MATHOD - READ THE URL FROM THE FILE
	 * NAMED:DataBaseURL.txt ! WE LOADING THE DRIVER AND GET CONNECTION TO THE
	 * DATABASE. THE CONNECTION RETURNS TO THE CONSTRACTOR AND REFEAL THE
	 * UNUSEDCONNECTIONS
	 ******************************/
	private Connection createConnection() { //
		try (BufferedReader reader = new BufferedReader(new FileReader(C_Statements.directory_address));) {
			String url = reader.readLine();//The Server Connection Url From The File.
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			return (DriverManager.getConnection(url));
		} catch (SQLException |IOException| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Connection Url Error occured. Please Try Again.");
			return null;
		}
	}

	/*******************************
	 * getConnection METHOD IS A SYNCHRONIZED METHOD/ WHILE THERE IS NO
	 * UNUSEDCONNECTION IN THE POOL, WE NEED TO WAIT FOR ONE. THE MOMENT THAT WE
	 * GET A CONNECTION, WE ADD IT TO THE USEDCONNETION SET. AND NOTIFYALL().
	 ******************************/
	public synchronized Connection getConnection() {
		Iterator<Connection> it = connections.iterator();
		while (!it.hasNext()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Connection con = it.next();
		it.remove();
		notifyAll();
		return con;
	}

	/*******************************
	 * returnConnection METHOD IS A SYNCHRONIZED METHOD/ WHILE THERE IS NO
	 * USEDCONNECTION IN THE POOL, WE NEED TO WAIT FOR ONE. THE MOMENT THAT WE
	 * GET A CONNECTION, WE ADD IT TO THE UNUSEDCONNETION SET. AND NOTIFYALL().
	 ******************************/
	public synchronized void returnConnection(Connection con) {
		Iterator<Connection> it = connections.iterator();
		while (!it.hasNext()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		connections.add(it.next());
		notifyAll();
	}

	/**
	 * closeAllConnections : Closing all the connection in connection pool
	 */
	// Closing all the connection in both arrays with for each loop
	public void closeAllConnections() {
		try {
			for (Connection connection : connections) {
				connection.close();
			}
			System.out.println("All Connections Are Closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
