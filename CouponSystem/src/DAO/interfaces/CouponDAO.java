package DAO.interfaces;

import java.sql.Date;
import java.util.Collection;

import DataTypes.Company;
import DataTypes.Coupon;
import DataTypes.CouponType;
import Exceptions.DAOException;

public interface CouponDAO {
	/**
	 * createCoupon ADD COUPON TO DATABASE.
	 * 
	 * @param coupon
	 *            Coupon object to add to database
	 * @throws DAOException
	 *             When database contains a coupon with the same title, or
	 *             connection with the database failed.
	 */
	public void createCoupon(Coupon coupon) throws DAOException;

	/**
	 * deleteCoupon DELETE COUPON FROM COUPON TABLE OF THE DATABASE, IF COUPON
	 * WITH THE GIVEN ID EXISTS.
	 * 
	 * @param c
	 *            The coupon to remove.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void deleteCoupon(Coupon coupon) throws DAOException;

	/**
	 * updateCoupon UPDATE attribute OF SPECIFIC COUPON.
	 * 
	 * @param coupon
	 *            The coupon.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void updateCoupon(Coupon coupon) throws DAOException;

	/**
	 * getCoupon GET THE COUPON WITH THE GIVEN ID FROM DATABASE.
	 * 
	 * @param id
	 *            The requested coupon's ID.
	 * @return The coupon from database with the given ID.
	 * @throws DAOException
	 *             When coupon with the given ID haven't been found, or
	 *             connection with the database failed.
	 */
	public Coupon getCoupon(long id) throws DAOException;

	/**
	 * getAllCoupon GET ALL COUPONS FROM DATABASE.
	 * 
	 * @return All coupons from database.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getAllCoupon() throws DAOException;

	/**
	 * getCouponByType GET ALL COUPONS FROM DATABASE MATCHING A SPECIFIC COUPON
	 * TYPE.
	 * 
	 * @param couponType
	 *            The coupon type.
	 * @return All coupons from database matching a specific Coupon Type.
	 * @throws DAOException
	 *             When connection with the database failed.
	 * @see CouponType
	 */
	public Collection<Coupon> getCouponByType(CouponType type) throws DAOException;

	/**
	 * getCouponsByMaxPrice get all coupons from database matching a specific
	 * coupon price.
	 * 
	 * @param Company
	 *            The coupon type.
	 * @param maxPrice
	 *            The coupons maxPrice
	 * @return All coupons from database Below and equal to a specific price.
	 * @throws DAOException
	 *             When connection with the database failed.
	 * @see CouponType
	 */
	public Collection<Coupon> getCouponsByMaxPrice(Company company, double maxPrice) throws DAOException;

	/**
	 * getCouponsByMaxEndDate get all coupons from database matching a specific
	 * coupon End date.
	 * 
	 * @param Company
	 *            // * the specific company
	 * @param maxEndDate
	 *            the coupon endDate
	 * @return All coupons from database matching a specific EndDate.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getCouponsByMaxEndDate(Company company, Date maxEndDate) throws DAOException;

	/**
	 * deleteExpiredCoupons : delete all expired coupons from the database
	 * 
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public void deleteExpiredCoupons() throws DAOException;

	/***********************************************************************
	 * getAllPurchasedCouponByType : Get all the coupons that bought by the this
	 * customer.
	 * 
	 * @return A collection of Purchased Coupons which the this customer bought.
	 * @throws DAOException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getPurchasedCouponByTypeAndCustId(long customerId, CouponType couponType) throws DAOException;

	public Collection<Coupon> getAllPurchasedCouponBtPrice(double price) throws DAOException;

}
