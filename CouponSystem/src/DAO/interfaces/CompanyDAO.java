package DAO.interfaces;

import java.util.Collection;

import DataTypes.Company;
import DataTypes.Coupon;
import DataTypes.CouponType;
import Exceptions.DAOException;

public interface CompanyDAO {
	/**
	 * createCompany ADD COMPANY TO DATABASE.
	 * 
	 * @param company
	 *            Company object to add to database
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void createCompany(Company company) throws DAOException;

	/**
	 * deleteCompany REMOVE COMPANY FROM COMPANY TABLE OF DATABASE, IF COMPANY
	 * WITH THE GIVEN ID EXISTS.
	 * 
	 * @param company
	 *            The company to remove.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void deleteCompany(Company company) throws DAOException;

	/**
	 * deleteAllCompanyCoupons Remove all coupon of the company , if company
	 * with the given ID exists.
	 * 
	 * @param company
	 *            The company to remove.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void deleteAllCompanyCoupons(Company company) throws DAOException;

	/**
	 * updateCompany UPDATE INFORMATION OF SPECIFIC COMPANY.
	 * 
	 * @param company
	 *            The company.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void updateCompany(Company company) throws DAOException;

	/**
	 * getCompany GET THE COMPANY WITH THE GIVEN ID FROM DATABASE.
	 * 
	 * @param id
	 *            The requested company's ID.
	 * @return The company from database with the given ID.
	 * @throws DAOException
	 *             When company with the given ID haven't been found, or
	 *             connection with the database failed.
	 */
	public Company getCompany(long id) throws DAOException;

	/**
	 * getAllCompany GET ALL COMPANIES FROM DATABASE.
	 * 
	 * @return All companies from database.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Company> getAllCompanies() throws DAOException;

	/**
	 * getCoupons GET ALL COUPONS ASSOCIATED WITH THE GIVEN COMPANY.
	 * 
	 * @param company
	 *            The company for which to search coupons for.
	 * @return All coupons of the given company.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getCouponsOfCompany(Company company) throws DAOException;

	/**
	 * Verifies a company user credentials.
	 * 
	 * @param compName
	 *            The company name.
	 * @param password
	 *            The company password.
	 * @return True - if verification resulted true. <br/>
	 *         False - if verification resulted false.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Boolean login(String compName, String password) throws DAOException;

	/**
	 * isCompanyExsistByCompanyName check if the company exist by a given
	 * company name.
	 * 
	 * @param company
	 *            The company for which to search for.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public boolean isCompanyExsistByCompanyName(String company) throws DAOException;

	/**
	 * isCouponExsistByTitle check if the coupon title exist in DB company name.
	 * 
	 * @param Title
	 *            The Title for which to search for.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public boolean isCouponExsistByTitle(Coupon coupon) throws DAOException;

	/**
	 * getCouponsByType check if the coupon title exist in DB company name.
	 * 
	 * @param CouponType
	 *            The type of coupon for which to search for.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getCouponsByType(CouponType CouponType) throws DAOException;
}
