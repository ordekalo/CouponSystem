package CouponSystem;

import DAO.interfaces.CouponDAO;
import DBDAO.CouponDBDAO;
import Exceptions.DAOException;

/**
 * A runnable class which contain the daily process of removing expired coupons
 * from the database. </br>
 */
public class DailyCouponExpirationThread extends Thread {
	/**
	 * A running method which contains the process of the runnable object. </br>
	 * This is done by waiting at first for midnight, and then executing
	 * <b>deleteAllExpiredCoupons</b> method, which removes all expired coupons
	 * from the database.</br>
	 * Then, a constant interval is set to 24 hours between every execution of
	 * deleteAllExpiredCoupons method. This method runs until the containing
	 * thread gets interrupted.
	 */
	@Override
	public void run() {
		CouponDAO couponDAO = new CouponDBDAO();
		try {
			couponDAO.deleteExpiredCoupons();
		} catch (DAOException e) {
			e.getMessage();
		}
	}

	/**
	 * Stops the thread by interrupting it.
	 */
	public void stopTask() {
		this.interrupt();
	}
}
