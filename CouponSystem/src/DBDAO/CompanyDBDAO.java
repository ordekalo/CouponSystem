package DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import CouponSystem.ConnectionPool;
import DAO.interfaces.CompanyDAO;
import DataTypes.Company;
import DataTypes.Coupon;
import DataTypes.CouponType;
import Exceptions.DAOException;
import Utiles.ProjectUtils;

public class CompanyDBDAO implements CompanyDAO {

	public static CompanyDAO getInstence() {
		return null;
	}

	private ConnectionPool pool = ConnectionPool.getInstance();

	/********************************************
	 * THIS createCompany MATHODS ARE RESPONCIBLE TO CREATE A COMPANY IN THE
	 * DATABASE. WE GET A CONNECTION FROM THE POOL AND CREATE A
	 * PREPAREDSTATEMENT .IF THERE IS A PROBLEM WITH THE SQL STATEMENT, THE
	 * FINALY BLOCK WILL RETURN THE CONNECTION TO THE POOL.
	 ********************************************/
	@Override
	public void createCompany(Company company) throws DAOException {

		String sql = "INSERT INTO Company values(?,?,?,?)";
		/**
		 * GET A CONNECTION FROM THE POOL
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, company.getId());
			statement.setString(2, company.getCompName());
			statement.setString(3, company.getPassword());
			statement.setString(4, company.getEmail());

			statement.executeUpdate();
		} catch (Exception e) {
			throw new DAOException("inster company failed", e);
		} finally {
			/**
			 * RETURN THE CONNECTION TO THE CONNECTIONPOOL
			 */
			pool.returnConnection(connection);
		}
	}

	/*************************************************************************
	 * THIS DELETECOMPANY MATHODS ARE RESPONCIBLE TO DELETE A COMPANY FROM THE
	 * DATABASE. WE GET A CONNECTION FROM THE POOL AND CREATE A
	 * PREPAREDSTATEMENT. IF THERE IS A PROBLEM WITH THE SQL STATEMENT, THE
	 * FINALY BLOCK WILL RETURN THE CONNECTION TO THE POOL.
	 ************************************************************************/
	@Override
	public void deleteCompany(Company company) throws DAOException {

		String sql = "DELETE FROM Company WHERE ID=?";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, company.getId());
			statement.executeUpdate();
			System.out.println("company deleted");

		} catch (Exception e) {
			throw new DAOException("delete company failed" + e.getMessage());
		} finally {
			/**
			 * RETURN THE CONNECTION TO THE CONNECTIONPOOL
			 */
			pool.returnConnection(connection);
		}
	}

	/*************************************************************************
	 * THIS deleteAllCompanyCoupons MATHODS ARE RESPONCIBLE TO DELETE ALL THE
	 * COUPONS OF A SPECIFIC COMPANY FROM DB
	 ************************************************************************/
	@Override
	public void deleteAllCompanyCoupons(Company company) throws DAOException {

		String sql = "DELETE FROM Company_Coupon WHERE Company_ID=?";
		/**
		 * GET A CONNECTION FROM THE POOL
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, company.getId());
			statement.executeUpdate();
			System.out.println("Company Coupon was deleted");

		} catch (Exception e) {
			throw new DAOException("delete company failed", e);
		} finally {
			/**
			 * RETURN THE CONNECTION TO THE CONNECTIONPOOL
			 */
			pool.returnConnection(connection);
		}
	}

	/*************************************************************************
	 * this updateCompany methods are responsible to update company in the
	 * database.
	 ************************************************************************/
	@Override
	public void updateCompany(Company company) throws DAOException {

		String sql = "UPDATE Company SET PASSWORD=?,EMAIL=?,WHERE ID=? ";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getPassword());
			statement.setString(2, company.getEmail());
			statement.setLong(3, company.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new DAOException("Update Company Failed", e);
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
	}

	/*************************************************************************
	 * this getCompany methods get an id and return a new company from the
	 * database.
	 ************************************************************************/
	@Override
	public Company getCompany(long id) throws DAOException {
		String sql = "SELECT * FROM Company WHERE ID = ?";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				Company company = new Company();
				company.setId(id);
				company.setCompName(Rs.getString(2));
				company.setPassword(Rs.getString(3));
				company.setEmail(Rs.getString(4));
				Rs.close();
				return company;
			}
			Rs.close();
			throw new DAOException("the company (id : " + id + " ) is not exsist");
		} catch (SQLException e) {
			throw new DAOException("get Company Failed", e);
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
	}

	/********************************************
	 * this getAllCompany methods return a collection of all company from the
	 * database.
	 ********************************************/
	@Override
	public Collection<Company> getAllCompanies() throws DAOException {

		String sql = "SELECT * FROM Company";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		/**
		 * create a collection of company
		 */
		Collection<Company> companeis = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				/**
				 * if we get here, thats means we find a company and create a
				 * company instance
				 */
				Company comp = new Company();
				// set the company state
				comp.setId(Rs.getLong(1));
				comp.setCompName(Rs.getString(2));
				comp.setPassword(Rs.getString(3));
				comp.setEmail(Rs.getString(4));
				// add the company to the collection set companies
				companeis.add(comp);
			}
			Rs.close();
			return companeis;
		} catch (SQLException e) {
			throw new DAOException("get all companies Failed!", e);
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
	}

	/********************************************
	 * this getCoupons methods return a collection of coupons from the database.
	 ********************************************/
	@Override
	public Collection<Coupon> getCouponsOfCompany(Company company) throws DAOException {

		String sql = "SELECT Coupon_ID FROM Company_Coupon WHERE Company_ID = ?";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		/**
		 * create a collection of coupon
		 */
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, company.getId());
			ResultSet CouponIDset = statement.executeQuery(sql);

			while (CouponIDset.next()) {

				String Tempsql = "SELECT * FROM Coupon WHERE ID= ?";
				PreparedStatement TempStatement = connection.prepareStatement(Tempsql);
				TempStatement.setLong(1, CouponIDset.getLong(1));
				ResultSet sqlCoupon = TempStatement.executeQuery();
				while (sqlCoupon.next()) {

					Coupon NewCoupon = new Coupon();
					sqlCoupon.getLong(1);
					sqlCoupon.getString(2);
					sqlCoupon.getDate(3);
					sqlCoupon.getDate(4);
					sqlCoupon.getInt(5);
					CouponType.valueOf(sqlCoupon.getString(6));
					sqlCoupon.getString(7);
					sqlCoupon.getDouble(8);
					sqlCoupon.getString(9);
					/**
					 * add a mew coupon to the collection
					 */
					coupons.add(NewCoupon);
					CouponIDset.close();
					sqlCoupon.close();
				}
			}
			return coupons;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
		System.out.println("No Such Coupun Is Found");
		return coupons;
	}

	/********************************************
	 * THIS BOOLEAN login MATHODS RETURN TRUE OR FALSE IF THE PASSWORD OR THE
	 * COMP_ID IS INCORRECT. FIRST, WE GET A CONNECTION FROM THE POOL AND CREATE
	 * A PREPAREDSTATEMENT. WHILE THERE IS NEXT() OF THE RESOLTE loginSet, WE
	 * ASK IF THE LOGINSET.getSTRING(1)IS EQUALSTO THE PASSWORD.IF YES' RETURN
	 * TRUE! IF NOT,THE "Password is incorrect !".IN CASE THERE IS NO
	 * loginSet.next(), SO THERE IS NO COMPANY IN THE DB. THE FINALY BLOCK WILL
	 * RETURN THE CONNECTION TO THE POOL BECAUSE WE CANT RETURN A NULL TO THE
	 * CONSTRACTOR
	 ********************************************/
	@Override
	public Boolean login(String compName, String password) throws DAOException {
		String sql = "SELECT PASSWORD FROM Company WHERE compName =?";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, compName);
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				/**
				 * check if the rs.getString(1)is equals to the correct
				 * password.
				 */
				if (Rs.getString(1).equals(password)) {
					return true;
				} else {
					System.out.println("Password Is Incorrect !");
					return false;
				}
			}
			System.out.println("there is no company" + compName + "in the dateBase!");
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
		System.out.println("There was an Error during connection");
		return false;
	}

	/********************************************
	 * this boolean isCompanyExsistByCompanyName methods return true or false if
	 * the company is already exists in the database
	 ********************************************/
	@Override
	public boolean isCompanyExsistByCompanyName(String compName) throws DAOException {

		String sql = "SELECT * FROM Company WHERE COMP_NAME=?";
		/**
		 * Get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, compName);
			ResultSet Rs = statement.executeQuery();
			if (Rs.next()) {
				System.out.println("company allredy exsit in the system!");
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
		System.out.println("Company Not Exsist in the System!");
		return false;
	}

	/********************************************
	 * this boolean isCouponExsistByTitle methods return true or false if the
	 * title is already exists in the database
	 ********************************************/
	@Override
	public boolean isCouponExsistByTitle(Coupon coupon) throws DAOException {

		String sql = "SELECT * FROM Coupon WHERE TITLE=?";
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, coupon.getTitle());
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				System.out.println("Coupon Title Allredy Exsist In DB!");
				return true;
			}
			Rs.close();
			System.out.println("Coupon Not Exsist!");
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
		System.out.println("coupon Not Exsist in the DB!");
		return false;
	}

	/**************************************************************************
	 * this getCouponsByType methods return a collection of coupon type
	 **************************************************************************/
	@Override
	public Collection<Coupon> getCouponsByType(CouponType CouponType) throws DAOException {

		String sql = "SELECT * FROM Coupon WHERE TYPE = ?";

		Collection<Coupon> coupons = new ArrayList<>();
		/**
		 * get a connection from the pool
		 */
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, CouponType.toString());
			ResultSet Rs = statement.executeQuery();
			while (Rs.next()) {
				Coupon coupon = ProjectUtils.extractCouponFromResultSet(Rs);
				// add the coupon to the collection
				coupons.add(coupon);

				Rs.close();
				return coupons;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/**
			 * return the connection to the pool
			 */
			pool.returnConnection(connection);
		}
		System.out.println("No Such Coupun is found");
		return null;
	}

}
