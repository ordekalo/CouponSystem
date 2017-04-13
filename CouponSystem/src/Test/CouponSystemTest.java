package Test;

import CouponSystem.CouponSystem;
import Exceptions.CouponSystemException;
import Facade.ClientType;

public class CouponSystemTest {

	public static void main(String[] args) {

		CouponSystem couponSystem = CouponSystem.getInstance();

		try {
			couponSystem.login(ClientType.ADMIN, "admin", "1234");

		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
