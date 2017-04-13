package DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import CouponSystem.ConnectionPool;
import DAO.interfaces.CustomerDAO;
import DataTypes.Coupon;
import DataTypes.Customer;
import Exceptions.DAOException;
import Utiles.ProjectUtils;

public class CustomerDBDAO implements CustomerDAO {

	private static CustomerDAO inatence = new CustomerDBDAO();

	public static CustomerDAO getInstence() {
		return inatence;
	}

	/**
	 * get the connection instance
	 */

	private ConnectionPool pool = ConnectionPool.getInstance();

	/****************************************************************************
	 * this createCustomer methods are responsible to create a customer in the
	 * database.
	 ****************************************************************************/
	@Override
	public void createCustomer(Customer customer) throws DAOException {
		String sql = "INSERT INTO Customer VALUES(?,?,?)";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			statement.setString(2, customer.getCustName());
			statement.setString(3, customer.getPassword());
			statement.execute();
			System.out.println("Customer was Created!");
		} catch (Exception e) {
			throw new DAOException("Creating Customer Failed", e);
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
	}

	/********************************************
	 * this deleteCustomer methods are responsible to delete a customer from the
	 * database.
	 ********************************************/
	@Override
	public void deleteCustomer(Customer customer) throws DAOException {
		String sql = "DELETE FROM Customer WHERE ID=?";
		/**
		 * Get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			statement.execute();
			System.out.println("Customer Deleted!");
		} catch (Exception e) {
			throw new DAOException("Delete Customer Failed", e);
		} finally {
			/**
			 * Return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
	}

	/********************************************
	 * this updateCustomer methods are responsible to update customer in the
	 * database (only password).
	 ********************************************/
	@Override
	public void updateCustomer(Customer customer) throws DAOException {
		String sql = "UPDATE Customer SET PASSWORD=? WHERE ID=?";
		/**
		 * get a connection from the pool
		 */
		CustomerDBDAO custDBDAO = new CustomerDBDAO();
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			if (custDBDAO.isCustomerExistById(customer.getId())) {
				statement.setString(1, customer.getPassword());
				statement.setLong(2, customer.getId());
				statement.executeUpdate();
				System.out.println("Customer " + customer.getId() + " was updated");				
			}else {
				System.out.println("This Customer id ("+customer.getId()+") Isn't Exist");
			}
		} catch (Exception e) {
			throw new DAOException("Update Customer " + customer.getId() + " Failed", e);
		} finally {
			// return connection to connection pool
			pool.returnConnection(connection);
		}
	}

	/*************************************************************************
	 * this getCustomer methods get an id and return a customer details from the
	 * database.
	 ************************************************************************/
	@Override
	public Customer getCustomer(long id) throws DAOException {

		String sql = "SELECT * FROM Customer WHERE ID = ?";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		Customer customer = new Customer();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet Rs = statement.executeQuery();
			if (Rs.next()) {
				customer.setId(id);
				customer.setCustName(Rs.getString(2));
				customer.setPassword(Rs.getString(3));
				Rs.close();
				return customer;
			} else {
				Rs.close();
				throw new DAOException("Customer: " + id + " - Is Not Exist");
			}
		} catch (SQLException e) {
			throw new DAOException("Get Customer Failed", e);
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);

		}
	}

	/*************************************************************************
	 * this getallCustomer methods return a AllCustomer from the database.
	 ************************************************************************/
	@Override
	public Collection<Customer> getAllCustomers() throws DAOException {

		String sql = "SELECT * FROM Customer";
		// get a connection from the pool
		Connection connection = pool.getConnection();
		// create a collection of customer
		Collection<Customer> customers = new ArrayList<>();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				// create a new customer for the collection
				Customer customer = new Customer();
				customer.setId(Rs.getLong(1));
				customer.setCustName(Rs.getString(2));
				customer.setPassword(Rs.getString(3));
				// add the new customer to the collection
				customers.add(customer);
			}
			Rs.close();
			return customers;
		} catch (SQLException e) {
			throw new DAOException("Get All Customers Failed!", e);
		} finally {
			// return the connection to the pool
			pool.returnConnection(connection);
		}
	}

	/*************************************************************************
	 * this getCoupons methods get a customer and return a Coupons from the
	 * database.
	 ************************************************************************/
	@Override
	public Collection<Coupon> getCouponsByCustomer(Customer customer) throws DAOException {
		String sql = "SELECT Coupon_ID FROM Customer_Coupon WHERE Customer_ID=?";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		/**
		 * create a collection of customer
		 */

		Collection<Coupon> coupons = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			ResultSet couponIDset = statement.executeQuery();
			while (couponIDset.next()) {

				String Tempsql = "SELECT * FROM Coupon WHERE ID=?";
				PreparedStatement Tempstatement = connection.prepareStatement(Tempsql);
				Tempstatement.setLong(1, couponIDset.getLong(1));
				ResultSet sqlCoupon = Tempstatement.executeQuery();
				while (sqlCoupon.next()) {
					Coupon newCoupum = ProjectUtils.extractCouponFromResultSet(sqlCoupon);
					coupons.add(newCoupum);
				}
				sqlCoupon.close();
			}
			couponIDset.close();
			return coupons;

		} catch (SQLException e) {
			throw new DAOException("Coupon was not found ", e);
		} finally {
			/**
			 * return the connection to the Connection Pool
			 */
			pool.returnConnection(connection);
		}
	}

	/*************************************************************************
	 * this boolean login methods return true or false if the customer password
	 * us correct
	 ************************************************************************/
	@Override
	public Boolean login(String custName, String password) throws DAOException {

		String sql = "SELECT PASSWORD FROM Customer WHERE cust_Name=?";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, custName);
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				if (Rs.getString(1).equals(password)) {
					System.out.println("The Password Is Correct");
					return true;
				} else {
					System.out.println("Sorry! Password is Incorrect!");
					return false;
				}
			}
			System.out.println(" There Is No Such Customer Name as " + custName + "In The Database.");
			return false;
		} catch (SQLException e) {
			throw new DAOException("Login Error. Please Try Again.");
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
	}

	/*************************************************************
	 * this boolean isCustomerExsistByName methods return true or false if the
	 * customer is already exists or not in the database
	 *************************************************************/
	@Override
	public boolean isCustomerExistByName(String CUST_NAME) throws DAOException {

		String sql = "SELECT * FROM Customer WHERE CUST_NAME=?";

		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, CUST_NAME);
			ResultSet Rs = statement.executeQuery();
			if (Rs.next()) {
				System.out.println("Customer Is Found!");
				return true;
			} else {
				System.out.println("Customer Wasn't Found!");
				return false;
			}
		} catch (SQLException e) {
			throw new DAOException("Connection Error");
		} finally {
			/**
			 * RETURN THE CONNECTION TO THE CONNECTIONPOOL
			 */
			pool.returnConnection(connection);
		}
	}

	/*************************************************************
	 * this boolean isCustomerExsistById methods return true or false if the
	 * customer is already exists or not in the database
	 *************************************************************/
	@Override
	public boolean isCustomerExistById(long id) throws DAOException {

		String sql = "SELECT * FROM Customer WHERE ID=?";

		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet Rs = statement.executeQuery();
			if (!(Rs == null)) {
				if (Rs.next()) {
					System.out.println("Customer Found");
					return true;
				} else {
					System.out.println("Customer Wasn't Found");
					return false;
				}
			}
			return false;
		} catch (SQLException e) {
			throw new DAOException("Connection Error");
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
	}

}
