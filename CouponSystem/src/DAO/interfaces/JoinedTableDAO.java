package DAO.interfaces;

import java.util.Collection;

import DataTypes.Company;
import DataTypes.Coupon;
import DataTypes.Customer;
import Exceptions.DAOException;

public interface JoinedTableDAO {
	/**
	 * deleteCoupon DELETE COUPON FROM BOTH COMPANY_COUPON AND CUSTOMER_COUPON
	 * TABLES.
	 * 
	 * @param c
	 *            The coupon to remove.
	 * @throws DAOException
	 *             If coupon doesn't exist, or connection lost.
	 */
	public void deleteCoupon(Coupon coupon) throws Exceptions.DAOException;

	/**
	 * delete Customer_Coupon DELETE SPECIFIC ROW IN CUSTOMER_COUPON TABLE OF
	 * DATABASE
	 * 
	 * @param customer
	 *            The customer with the ID
	 * @param coupon
	 *            The coupon with the ID
	 * @throws DAOException
	 *             If customer or coupon doesn't exist, or connection lost.
	 */
	public void deleteCustomer_Coupon(Customer customer, Coupon coupon) throws DAOException;

	/**
	 * deleteCustomer delete customer and all of its coupon purchase history
	 * from Customer_Coupon table
	 * 
	 * @param customer
	 *            The customer to remove.
	 * @throws DAOException
	 *             If customer doesn't exist, or connection lost.
	 */
	public void deleteCustomerPurchaseHistory(Customer customer) throws DAOException;

	/**
	 * createCustomer_Coupon ADD NEW ROW FOR CUSTOMER_COUPON TABLE.
	 * 
	 * @param customer
	 *            The customer for the row.
	 * @param coupon
	 *            The coupon for the row.
	 * @throws DAOException
	 *             If customer or coupon doesn't exist, or connection lost.
	 */
	public void customerPurchaseCoupon(Customer customer, Coupon coupon) throws DAOException;

	/**
	 * createCustomer_Coupon ADD NEW ROW FOR COMPANY_COUPON TABLE.
	 * 
	 * @param company
	 *            The company for the row.
	 * @param coupon
	 *            The coupon for the row.
	 * @throws DAOException
	 *             If company or coupon doesn't exist, or connection lost.
	 */
	public void companyCreateCoupon(Company company, Coupon coupon) throws DAOException;

	public Collection<Coupon> getAllPurchasedCouponByCustomer(long custId) throws DAOException;
	
	void deleteCustomerFromCustomerCoupons(long id) throws DAOException;

}
