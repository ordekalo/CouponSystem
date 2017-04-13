package Test;

import DataTypes.Coupon;
import DataTypes.CouponType;
import Exceptions.CouponSystemException;
import Facade.CompanyFacade;
import Facade.CustomerFacade;

public class CustomerTest {

	public static void main(String[] args) {
		CustomerFacade custFacade = new CustomerFacade();

		Coupon coup = new Coupon();
		coup.setId(111);
		coup.setPrice(150);
		coup.setTitle("TV");
		coup.setType(CouponType.ELECRICITY);
		coup.setAmount(10);
		System.out.println(coup);
		Coupon coup1 = new Coupon();
		coup1.setId(222);
		coup1.setPrice(122);
		coup1.setTitle("SPORT");
		coup1.setType(CouponType.FOOD);
		coup1.setAmount(20);
		System.out.println(coup1);
		CompanyFacade cFacade = new CompanyFacade();
		try {
			cFacade.createCoupon(coup1);
		} catch (CouponSystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			custFacade.purchaseCoupon(coup);
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
