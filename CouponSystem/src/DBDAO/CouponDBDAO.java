package DBDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import CouponSystem.ConnectionPool;
import DAO.interfaces.CouponDAO;
import DataTypes.Company;
import DataTypes.Coupon;
import DataTypes.CouponType;
import Exceptions.DAOException;
import Utiles.ProjectUtils;

public class CouponDBDAO implements CouponDAO {

	private static CouponDAO inatence = new CouponDBDAO();

	public CouponDBDAO() {
	}

	public static CouponDAO getInstence() {
		return inatence;

	}

	/**
	 * get the connection instance
	 */
	private ConnectionPool pool = ConnectionPool.getInstance();

	/**
	 * this createCoupon methods are creating a coupon in the database.
	 * 
	 * @param coupon
	 *            the coupon to create
	 * @throws DAOException
	 *             when connection failed
	 */
	@Override
	public void createCoupon(Coupon coupon) throws DAOException {
		String sql = "INSERT INTO Coupon VALUES(?,?,?,?,?,?,?,?,?)";
		/**
		 * get a connection from pool (getConnection is a static method)
		 */
		Connection connection = pool.getConnection();
		// Creating a statement object which holds the SQL we're about to
		// execute
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, coupon.getId());
			statement.setString(2, coupon.getTitle());
			// java.util.Date Start_Date = coupon.getStartDate();
			// java.sql.Date sDate = new java.sql.Date(Start_Date.getTime());
			// statement.setDate(3, sDate);
			statement.setDate(3, coupon.getStartDate());
			// java.util.Date End_Date = coupon.getStartDate();
			// java.sql.Date eDate = new java.sql.Date(End_Date.getTime());
			// statement.setDate(4, eDate);
			statement.setDate(4, coupon.getEndDate());
			statement.setInt(5, coupon.getAmount());
			statement.setString(6, coupon.getType().toString());
			statement.setString(7, coupon.getMessege());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.execute();

		} catch (SQLException e) {
			throw new DAOException("Create Coupon Failed! A coupon with ID '" + coupon.getId() + "' already exist!", e);
		} finally {
			// return the connection to the pool
			pool.returnConnection(connection);
		}
	}

	/**
	 * this deleteCoupon methods are deleting a coupon from the database.
	 * 
	 * @param coupon
	 *            the coupon to create
	 * @throws DAOException
	 *             when connection failed
	 */
	@Override
	public void deleteCoupon(Coupon coupon) throws DAOException {

		String sql = "DELETE FROM Coupon WHERE ID=?";
		Connection connection = pool.getConnection();
		// Creating a statement object which holds the SQL we're about to
		// execute
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, coupon.getId());
			statement.execute();
		} catch (Exception e) {
			throw new DAOException("delete coupon failed", e);
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
	}

	/**
	 * this updateCoupon methods are updating a coupon in the database.
	 * 
	 * @param coupon
	 *            the coupon to create
	 * @throws DAOException
	 *             when connection failed
	 */

	@Override
	public void updateCoupon(Coupon coupon) throws DAOException {
		String sql = "UPDATE Coupon SET TITLE =?, START_DATE=?, END_DATE=?, AMOUNT=?, TYPE=?,MESSAGE=? ,PRICE=?, IMAGE=? WHERE ID=?";
		// get a connection from the pool
		Connection connection = pool.getConnection();
		// Creating a statement object which holds the SQL we're about to
		// execute
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, coupon.getTitle());
			statement.setDate(2, coupon.getStartDate());
			statement.setDate(3, coupon.getEndDate());
			statement.setInt(4, coupon.getAmount());
			statement.setString(5, coupon.getType().toString());
			statement.setString(6, coupon.getMessege());
			statement.setDouble(7, coupon.getPrice());
			statement.setString(8, coupon.getImage());
			statement.setLong(9, coupon.getId());
			statement.execute();

		} catch (Exception e) {
			throw new DAOException("update coupon failed", e);
		} finally {
			// return the connection to the pool
			pool.returnConnection(connection);
		}
	}

	/**
	 * this updateCoupon method are updating a coupon in the database.
	 * 
	 * @param coupon
	 *            the coupon to create
	 * @throws DAOException
	 *             when connection failed
	 */
	/******************************************************************
	 * this getCoupon method get a coupon from the database, BY GIVING AN
	 * COUPON_ID. WE GET A CONNECTION FROM THE POOL AND CREATE A
	 * PREPAREDSTATEMENT. IF THERE IS A PROBLEM WITH THE SQL STATEMENT, THE
	 * FINALY BLOCK WILL RETURN THE CONNECTION TO THE POOL.
	 ****************************************************************/
	@Override
	public Coupon getCoupon(long id) throws DAOException {

		String sql = "SELECT * FROM  Coupon WHERE ID = ?";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		// Creating a statement object which holds the SQL we're about to
		// execute
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet Rs = statement.executeQuery();
			if (Rs.next()) {
				// coupon was found. creating a new coupon
				Coupon coupon = new Coupon();
				coupon.setId(id);
				coupon.setTitle(Rs.getString(2));
				coupon.setStartDate(Rs.getDate(3));
				coupon.setEndDate(Rs.getDate(4));
				coupon.setAmount(Rs.getInt(5));
				coupon.setType(CouponType.valueOf(Rs.getString(6)));
				coupon.setMessege(Rs.getString(7));
				coupon.setPrice(Rs.getDouble(8));
				coupon.setImage(Rs.getString(9));
				Rs.close();
				return coupon;
			}
			Rs.close();
			throw new DAOException("Coupon Is Not Exsist");
		} catch (SQLException e) {
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
		System.out.println("Read Coupon Failed");
		return null;
	}

	/**
	 * this getAllCoupon methods are responsible to return a collection of all
	 * coupon in the database.
	 */
	@Override
	public Collection<Coupon> getAllCoupon() throws DAOException {

		String sql = "SELECT * FROM Coupon";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		/**
		 * create a collection of coupons which returns eventually
		 */
		Collection<Coupon> coup = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				/**
				 * create a new coupon
				 */
				Coupon coupon = new Coupon();
				Rs.getLong(1);
				Rs.getString(2);
				Rs.getDate(3);
				Rs.getDate(4);
				Rs.getInt(5);
				CouponType.valueOf(Rs.getString(6));
				Rs.getString(7);
				Rs.getDouble(8);
				Rs.getString(9);
				/**
				 * add the new coupon to the collection
				 */
				coup.add(coupon);
			}
			Rs.close();
			return coup;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/**
			 * RETURN THE CONNECTION TO THE POOL
			 */
			pool.returnConnection(connection);
		}
		System.out.println("coupon was not found");
		return coup;
	}

	/**
	 * // * this getCouponByType methods are responsible to get a collection of
	 * coupons by type of coupon
	 */
	@Override
	public Collection<Coupon> getCouponByType(CouponType type) throws DAOException {

		String sql = "SELECT * FROM Coupon WHERE TYPE=?";

		Connection connection = pool.getConnection();

		Collection<Coupon> coupons = new ArrayList<>();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, type.toString());
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {

				Coupon coupon = ProjectUtils.extractCouponFromResultSet(Rs);
				// add the new coupon to the collection
				coupons.add(coupon);
				// return the collection of Coupons By Type
				return coupons;

			}
			Rs.close();
			// return an empty collection
			return coupons;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/**
			 * RETURN THE CONNECTION TO THE POOL
			 */
			pool.returnConnection(connection);
		}
		System.out.println("No Coupons were found");
		return coupons;
	}

	/**
	 * this isCouponAvailableForPurchase is a boolean methods and return true if
	 * the coupon amount is positive and false if their is not coupon left
	 * 
	 * @param coupon
	 *            the coupon attribute (only coupon id and amount mater)
	 * @throws DAOException
	 *             if connection failed
	 */
	public boolean isCouponAvailableForPurchase(Coupon coupon) throws DAOException {

		String sql = "SELECT * FROM Coupon WHERE ID=? AND AMOUNT=?" + coupon.getId();

		Connection connection = pool.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, coupon.getId());
			statement.setInt(2, coupon.getAmount());
			statement.execute();
			if (coupon.getAmount() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// return the connection to the connection pool
			pool.returnConnection(connection);
		}
		return false;
	}

	/**
	 * this getCouponsByMaxPrice method is returned a collection of coupons up
	 * to a certain price
	 * 
	 * @param company
	 *            the company that own the coupon
	 * @param maxPrice
	 *            the max price of the coupon
	 * @throws DAOException
	 *             when connection failed
	 */
	@Override
	public Collection<Coupon> getCouponsByMaxPrice(Company company, double maxPrice) throws DAOException {

		String sql = "SELECT FROM Company_Coupon WHERE ID=? AND PRICE<=?";

		Collection<Coupon> couponMaxPrice = new ArrayList<>();

		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, company.getId());
			statement.setDouble(2, maxPrice);
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {

				Coupon coupon = ProjectUtils.extractCouponFromResultSet(Rs);
				// add the new coupon to the collection
				couponMaxPrice.add(coupon);
				// return the collection of Coupons By MaxPrice
				return couponMaxPrice;
			}
			Rs.close();
			// return an empty collection
			return couponMaxPrice;
		} catch (SQLException e) {
			throw new DAOException("no coupon was found!");
		} finally {
			// return the connection to the connection pool
			pool.returnConnection(connection);
		}
	}

	/**
	 * this getCouponsWithMaxEndDate method is returned a collection of
	 * non-expired coupons
	 * 
	 * @param company
	 *            the company that owns the coupon * @param maxEndDate the max
	 *            End Date of the coupon
	 * @throws DAOException
	 *             when connection failed
	 */
	@Override
	public Collection<Coupon> getCouponsByMaxEndDate(Company company, Date maxEndDate) throws DAOException {

		String sql = "SELECT FROM Company_Coupon WHERE Company.ID=? AND END_DATE<=?";

		Collection<Coupon> couponMaxEndDate = new ArrayList<>();

		Connection connection = pool.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, company.getId());
			statement.setDate(2, maxEndDate);
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				// create a new coupon
				Coupon coupon = ProjectUtils.extractCouponFromResultSet(Rs);
				// add the new coupon to the collection
				couponMaxEndDate.add(coupon);
				// return the collection of Coupons By MaxEndDate
				return couponMaxEndDate;

			}
			Rs.close();
			// return an empty collection
			return couponMaxEndDate;
		} catch (SQLException e) {
			throw new DAOException("no coupon was found!");
		} finally {
			// return the connection to the connection pool
			pool.returnConnection(connection);
		}
	}

	/**
	 * this deleteExpiredCoupons method is deleting all coupons that that have
	 * an expired date
	 * 
	 * @throws DAOException
	 *             when connection is failed
	 */
	@Override
	public void deleteExpiredCoupons() throws DAOException {

		String sql = "DELETE Coupon, Company_Coupon FROM Coupon "
				+ "INNER JOIN Company_coupon ON Coupon.ID = Company_coupon.Coupon.ID WHERE END_DATE < now()";

		Connection connection = pool.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();

		} catch (SQLException e) {
			throw new DAOException("Failed to delete Company due to :" + e.getMessage());
		} finally {
			// return the connection to the connection pool
			pool.returnConnection(connection);
		}
	}

	/**
	 * this deleteExpiredCoupons method get all purchased coupons by type of
	 * coupon of a specific customer
	 * 
	 * @param customerId
	 *            the customer id
	 * @param type
	 *            the type of the coupon
	 * @throws DAOException
	 *             when connection is failed
	 */
	@Override
	public Collection<Coupon> getPurchasedCouponByTypeAndCustId(long customerId, CouponType type) throws DAOException {

		String sql = "SELECT * FROM Coupon JOIN Customer_Coupon on Coupon.ID = Customer_coupon.coupon.ID WHERE Customer.ID =? AND TYPE = ?";

		Collection<Coupon> PurchasedCouponByType = new ArrayList<>();

		Connection connection = pool.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, customerId);
			statement.setString(1, type.toString());
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				Coupon coupon = ProjectUtils.extractCouponFromResultSet(Rs);
				PurchasedCouponByType.add(coupon);
				// return the collection with all the purchased coupons by Type
				return PurchasedCouponByType;
			}
			// close the result set
			Rs.close();
			// return an empty collection
			return PurchasedCouponByType;
		} catch (SQLException e) {
			throw new DAOException("no coupon was found!");
		} finally {
			// return the connection to the connection pool
			pool.returnConnection(connection);
		}
	}

	/**
	 * this getAllPurchasedCouponBtPrice method get all purchased coupons by
	 * price of coupon of a specific customer
	 * 
	 * @param price
	 *            the price of the coupon
	 * @throws DAOException
	 *             when connection is failed
	 */
	@Override
	public Collection<Coupon> getAllPurchasedCouponBtPrice(double price) throws DAOException {

		Collection<Coupon> AllPurchasedCouponBtPrice = new ArrayList<>();

		String sql = "SELECT * FROM Coupon JOIN Customer_Coupon on Coupon.ID = Customer_Coupon.Coupon.ID WHERE Customer.ID =? AND PRICE = ?";

		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDouble(1, price);
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				Coupon coupon = ProjectUtils.extractCouponFromResultSet(Rs);
				AllPurchasedCouponBtPrice.add(coupon);
				// return the collection with all the purchased coupons by price
				return AllPurchasedCouponBtPrice;
			}
			Rs.close();
			// return an empty collection
			return AllPurchasedCouponBtPrice;
		} catch (SQLException e) {
			throw new DAOException("get all purchase coupon for now", e);
		} finally {
			// return the connection to the connection pool
			pool.returnConnection(connection);
		}
	}
}
