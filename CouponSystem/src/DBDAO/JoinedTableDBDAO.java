package DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import CouponSystem.ConnectionPool;
import DAO.interfaces.JoinedTableDAO;
import DataTypes.Company;
import DataTypes.Coupon;
import DataTypes.Customer;
import Exceptions.DAOException;
import Utiles.ProjectUtils;

public class JoinedTableDBDAO implements JoinedTableDAO {

	private static JoinedTableDBDAO instance = new JoinedTableDBDAO();

	public JoinedTableDBDAO() {
	}

	public static JoinedTableDBDAO getInstance() {
		return instance;

	}

	private ConnectionPool pool = ConnectionPool.getInstance();

	/**
	 * delete coupon from company_coupon and customer_coupon tables.
	 */
	@Override
	public void deleteCoupon(Coupon coupon) throws DAOException {

		String sql = "DELETE FROM Company_Coupon WHERE Coupon_ID=?";
		String sql2 = "DELETE FROM Customer_Coupon WHERE Coupon_ID=?";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, coupon.getId());
			statement.execute();

			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.setLong(1, coupon.getId());
			statement2.execute();
		} catch (SQLException e) {
			throw new DAOException("delete Coupon Failed", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	/**
	 * delete specific customer and coupon in customer_coupon table from
	 * database
	 */
	@Override
	public void deleteCustomer_Coupon(Customer customer, Coupon coupon) throws DAOException {
		String sql = "DELETE FROM Customer_coupon WHERE Customer_ID=? and Coupon_ID=?";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			statement.setLong(2, coupon.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("delete failed!");
		} finally {
			pool.returnConnection(connection);
		}
	}

	/**
	 * delete customer and all of its coupon purchase history from
	 * Customer_Coupon table
	 */
	@Override
	public void deleteCustomerPurchaseHistory(Customer customer) throws DAOException {

		String sql = "DELETE FROM Customer_Coupon WHERE Customer_ID=?";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("Delete Customer Purchase History from Customer_Coupon table failed!");
		} finally {
			pool.returnConnection(connection);
		}
	}

	/**
	 * add new customer and coupon for customer_coupon table.
	 */
	@Override
	public void customerPurchaseCoupon(Customer customer, Coupon coupon) throws DAOException {

		String sql = "INSERT INTO Customer_Coupon VALUE (?,?)";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customer.getId());
			statement.setLong(2, coupon.getId());
			statement.execute();

		} catch (SQLException e) {
			throw new DAOException("creating a new customer_coupon row failed!");
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * add new Company and coupon for Company_Coupon table.
	 */
	@Override
	public void companyCreateCoupon(Company company, Coupon coupon) throws DAOException {

		String sql = "INSERT INTO Company_Coupon VALUE (?,?)";
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, company.getId());
			statement.setLong(2, coupon.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DAOException("creating a new Company_coupon row failed!");
		} finally {
			pool.returnConnection(connection);
		}

	}

	/*************************************************************************
	 * this method is getting a collections of all the purchased coupon of a
	 * Specific customer by the customer ID and coupon ID
	 * 
	 * @param Custid
	 *            the Specific customer ID
	 * @param Coupid
	 *            the Coupon ID
	 * @throw DAEException if connection failed
	 */
	@Override
	public Collection<Coupon> getAllPurchasedCouponByCustomer(long Custid) throws DAOException {
		// create a collections of Purchased Coupon
		Collection<Coupon> CustomerPurchasedCoupon = new ArrayList<>();

		String sql = "SELECT * FROM Coupon JOIN Customer_Coupon ON Coupon_ID=Customer_Coupon.Coupon_ID WHERE Customer_ID=?";

		Connection connection = pool.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, Custid);
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				Coupon coupon = ProjectUtils.extractCouponFromResultSet(Rs);
				CustomerPurchasedCoupon.add(coupon);
			}
			Rs.close();
		} catch (SQLException e) {
			throw new DAOException("Connection Failed!", e);
			
		} finally {
			pool.returnConnection(connection);
		}
		return CustomerPurchasedCoupon;
	}
	
	/********************************************
	 * this deleteCustomerCoupons methods are responsible to delete specific costumer by his id from
	 * customer_coupons table at the database.
	 ********************************************/
	@Override
	public void deleteCustomerFromCustomerCoupons(long id) throws DAOException {

		String sql = "DELETE FROM Customer_Coupon WHERE Customer_ID=?";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			statement.execute();
			System.out.println("This Customer "+id+" was Deleted!");
		} catch (Exception e) {
			throw new DAOException("Delete Customer Failed", e);
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
	}

}
