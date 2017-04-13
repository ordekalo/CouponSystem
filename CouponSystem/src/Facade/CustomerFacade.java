package Facade;

import java.util.Collection;

import DAO.interfaces.CouponDAO;
import DAO.interfaces.CustomerDAO;
import DAO.interfaces.JoinedTableDAO;
import DBDAO.CouponDBDAO;
import DBDAO.CustomerDBDAO;
import DBDAO.JoinedTableDBDAO;
import DataTypes.Coupon;
import DataTypes.CouponType;
import DataTypes.Customer;
import Exceptions.CouponSystemException;
import Exceptions.DAOException;

/**
 * A Facade which contains all methods that can be executed by the customer
 * user. Any instance represents a single customer, that can execute the methods
 * by this object. This class implements {@link CouponClientFacade}
 */
public class CustomerFacade implements CouponClientFacade {
	/**
	 * Customer object containing the attributes of the customer
	 */
	private Customer customer;
	/**
	 * A reference to the CustomerDAO instance.
	 */
	private CustomerDAO customerDAO = CustomerDBDAO.getInstence();
	/**
	 * A reference to the couponDAO instance.
	 */
	private CouponDAO couponDAO = CouponDBDAO.getInstence();
	/**
	 * A reference to the joinedTableDAO instance.
	 */
	private JoinedTableDAO joinedTableDAO = JoinedTableDBDAO.getInstence();

	/**
	 * Private CTOR
	 * 
	 */
	public CustomerFacade() {
		this.customer = customer;
	}

	/**
	 * purchaseCoupon : a customer client purchase of coupon by changing the
	 * relevant data on database.
	 * 
	 * @param coupon
	 *            The coupon to purchase. Only ID matters.
	 * @throws CouponSystemException
	 *             When this customer already purchased this coupon,or coupon
	 *             out of stock or connection failed.
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		try {
			// Check for existence of coupon.
			Coupon couponexsist = couponDAO.getCoupon(coupon.getId());
			// Check for previous purchase of this coupon by the this customer
			if (joinedTableDAO.getAllPurchasedCouponByCustomer(this.customer.getId(), coupon.getId()) != null)
				throw new CouponSystemException("this coupon Allready Purchased!");
			// Check if coupon is available
			if (couponexsist.getAmount() == 0) {
				throw new CouponSystemException("we are sory, There is no coupons left to buy");
			}

			// There is no need to check for out of date coupon because the
			// DailyCouponExpirationTask would find it before this accrues.

			// Purchase
			joinedTableDAO.customerPurchaseCoupon(this.customer, couponexsist);
			couponexsist.setAmount(couponexsist.getAmount() - 1);
			couponDAO.updateCoupon(couponexsist);
		} catch (DAOException e) {
			throw new CouponSystemException("purchase Coupon Failde", e);
		}
	}

	/**
	 * getAllPurchasedCoupon : Get a collection of all coupons Purchased Coupon
	 * by the this customer.
	 * 
	 * @return A collection of Purchased Coupons which this customer bought.
	 * @throws CouponSystemException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getAllPurchasedCoupon() throws CouponSystemException {
		try {
			Collection<Coupon> PurchasedCoupon = customerDAO.getCouponsByCustomer(this.customer);
			if (PurchasedCoupon.isEmpty()) {
				// return an empty collection
				return PurchasedCoupon;
			}
			// return the collection with all purchased coupons of this customer
			return PurchasedCoupon;
		} catch (DAOException e) {
			throw new CouponSystemException("Get All Purchased Coupon Failed", e);
		}
	}

	/**
	 * getAllPurchasedCouponByType : Get a collection of all coupons bought by
	 * this customer with a specific type of coupon.
	 * 
	 * @return A collection of Purchased Coupons which this customer bought.
	 * @throws CouponSystemException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getAllPurchasedCouponByType(CouponType type) throws CouponSystemException {

		try {
			Collection<Coupon> PurchasedCouponByType = CouponDBDAO.getInstence()
					.getPurchasedCouponByTypeAndCustId(this.customer.getId(), type);
			if (PurchasedCouponByType.isEmpty()) {
				// return an empty collection
				return PurchasedCouponByType;
			}
			// return the collection with all purchased coupons of this customer
			return PurchasedCouponByType;
		} catch (DAOException e) {
			throw new CouponSystemException("sory, we can't Get All Purchased Coupons by their type ", e);
		}
	}

	/**
	 * getAllPurchasedCouponBtPrice : Get a collection of all coupons bought by
	 * this customer with a specific price.
	 * 
	 * @return A collection of Purchased Coupons which this customer bought.
	 * @throws CouponSystemException
	 *             When connection with the database failed.
	 */
	public Collection<Coupon> getAllPurchasedCouponBtPrice(double price) throws CouponSystemException {

		try {
			Collection<Coupon> PurchasedCouponBtPrice = CouponDBDAO.getInstence().getAllPurchasedCouponBtPrice(price);
			if (PurchasedCouponBtPrice.isEmpty()) {
				// return an empty collection
				return PurchasedCouponBtPrice;
			}
			// return the collection with all purchased coupons of this customer
			return PurchasedCouponBtPrice;

		} catch (DAOException e) {
			throw new CouponSystemException("sory, we can't Get All Purchased Coupons by their type ", e);
		}
	}
}
