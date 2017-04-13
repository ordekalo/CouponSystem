package Facade;

import java.sql.Date;
import java.util.Collection;

import DAO.interfaces.CompanyDAO;
import DAO.interfaces.CouponDAO;
import DAO.interfaces.JoinedTableDAO;
import DBDAO.CompanyDBDAO;
import DBDAO.CouponDBDAO;
import DBDAO.JoinedTableDBDAO;
import DataTypes.Company;
import DataTypes.Coupon;
import DataTypes.CouponType;
import Exceptions.CouponSystemException;
import Exceptions.DAOException;
import Exceptions.FacadeException;

/**
 * A Facade which contains all methods that can be executed by the company user.
 * Any instance represents a single company, that can execute the methods by
 * this object, whose given by the login static method This class implements
 * {@link CouponClientFacade}, together with {@link AdminFacade} and
 * {@link CustomerFacade}. The Company Facade is part of the {@link Facades}
 * layer which enables the users to handle their information.
 */
public class CompanyFacade implements CouponClientFacade {
	/**
	 * Company object containing the attributes of the company
	 */
	private Company company;
	/**
	 * A reference to the CompanyDBDAO instance.
	 */
	private CompanyDAO companyDAO = CompanyDBDAO.getInstence();
	/**
	 * A reference to the CouponDBDAO instance.
	 */
	private CouponDAO couponDAO = CouponDBDAO.getInstence();
	/**
	 * A reference to the JoinedTablesDBDAO instance.
	 */
	private JoinedTableDAO joinedTableDAO = JoinedTableDBDAO.getInstence();

	/**
	 * Private CTOR
	 */
	public CompanyFacade() {
		super();
	}

	/**
	 * Get the company details of the company
	 * 
	 * @return The current company.
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * createCoupon : checking if the coupon title is already exist (coupon
	 * title and id must be unique) if true, we get an exception. if false, is't
	 * create a new coupon.
	 * 
	 * @param Coupon
	 *            The Coupon object
	 * @throws FacadeException
	 */
	public void createCoupon(Coupon coupon) throws CouponSystemException {

		try {
			// if (companyDAO.isCouponExsistByTitle(coupon) == true) {
			// throw new CouponSystemException("Coupon Title Is Already
			// Exist!");
			// }
			// create coupon
			couponDAO.createCoupon(coupon);
			// create coupon in Company_Coupon table
			joinedTableDAO.companyCreateCoupon(this.company, coupon);
		} catch (DAOException e) {
			throw new FacadeException("Create Coupon Failed", e);
		}
	}

	/**
	 * deleteCoupon : delete the coupon by id, and delete all of coupons from
	 * the join table.
	 * 
	 * @param coupon
	 *            the specific coupon to delete by id
	 * @throws FacadeException
	 */
	public void deleteCoupon(Coupon coupon) throws FacadeException {
		try {
			// delete coupon
			couponDAO.deleteCoupon(coupon);
			// delete coupon from the join table Company_coupon and
			// Customer_coupon
			joinedTableDAO.deleteCoupon(coupon);
		} catch (DAOException e) {
			throw new FacadeException("Delete Coupon Failed", e);
		}
	}

	/**
	 * updateCoupon : update the coupon attribute by id.
	 * 
	 * @throws FacadeException
	 *             when Update Coupon Failed
	 */
	public void updateCoupon(Coupon coupon) throws FacadeException {
		try {
			Coupon coup = couponDAO.getCoupon(coupon.getId());
			if (coup != null) {
				coup.setPrice(coupon.getPrice());
				coup.setEndDate(coupon.getEndDate());
				couponDAO.updateCoupon(coup);
			}
			throw new FacadeException("Coupon Is Not Exsist");
		} catch (DAOException e) {
			throw new FacadeException("Update Coupon Failed", e);
		}
	}

	/**
	 * getCoupon : checking if the coupon id exist in the DB and return the
	 * specific coupon
	 * 
	 * @throws FacadeException
	 *             when Get Coupon Was Failed
	 */
	public Coupon getCoupon(long id) throws FacadeException {
		try {
			if (couponDAO.getCoupon(id) == null) {
				throw new FacadeException("No Coupon Was Found");
			}
			return couponDAO.getCoupon(id);
		} catch (DAOException e) {
			throw new FacadeException("Get Coupon Was Failed", e);
		}
	}

	/**
	 * getAllCoupons : returns all the coupons of the specific company
	 * 
	 * @throws FacadeException
	 *             when Get All Coupon Is Failed
	 */
	public Collection<Coupon> getAllCoupon() throws FacadeException {

		try {
			Collection<Coupon> getAllCoupon = companyDAO.getCouponsOfCompany(this.company);
			if (getAllCoupon.isEmpty()) {
				// return an empty collection
				return getAllCoupon;
			}
			// return a collections of the company coupon
			return getAllCoupon;
		} catch (DAOException e) {
			throw new FacadeException("Get All Coupon Is Failed", e);
		}

	}

	/**
	 * getCouponsByType : returns all the coupons of the specific type of coupon
	 * 
	 * @throws FacadeException
	 *             Get Coupon Failed
	 */
	public Collection<Coupon> getCouponsByType(CouponType type) throws FacadeException {
		try {
			Collection<Coupon> getCouponsByType = couponDAO.getCouponByType(type);
			// check if the collection is empty
			if (getCouponsByType.isEmpty()) {
				// check the empty collection
				return getCouponsByType;
			}
			// Return the collection of coupon be the type
			return getCouponsByType;
		} catch (DAOException e) {
			throw new FacadeException("Get Coupon Failed", e);
		}
	}

	/**
	 * getCouponsyMaxPrice : Get all coupons of the current company, which their
	 * price is not more than a maxPrice
	 * 
	 * @param maxPrice
	 *            The maximum price of all the coupons.
	 * @return A collection of coupons owned by current company by the company
	 *         id, with price which doesn't exceeds the given maximum price
	 *         (maxPrice).
	 * @throws FacadeException
	 *             When connection failed.
	 */
	public Collection<Coupon> getCouponsByMaxPrice(Company company, double maxPrice) throws FacadeException {
		try {
			Collection<Coupon> coup = couponDAO.getCouponsByMaxPrice(this.company, maxPrice);
			return coup;
		} catch (DAOException e) {
			throw new FacadeException(" Get Coupons Failed", e);
		}
	}

	/**
	 * getCouponsByEndDate : Get All coupons of the current company, which their
	 * expiration date is before a specific date.
	 * 
	 * @param maxEndDate
	 *            The latest date of the coupons.
	 * @return A collection of coupons owned by current company, with expiration
	 *         date which is before or exactly the given date.
	 * @throws FacadeException
	 *             When connection failed.
	 */
	public Collection<Coupon> getCouponsByEndDate(Company company, Date maxEndDate) throws FacadeException {
		try {
			Collection<Coupon> coupons = couponDAO.getCouponsByMaxEndDate(this.company, maxEndDate);
			return coupons;
		} catch (DAOException e) {
			throw new FacadeException(" Get Coupons Faeiled", e);
		}
	}

}
