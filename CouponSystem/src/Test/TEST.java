package Test;

import DBDAO.CouponDBDAO;
import DataTypes.Company;
import DataTypes.Coupon;
import DataTypes.CouponType;
import Exceptions.DAOException;
import Facade.CustomerFacade;

public class TEST {
	Company company = new Company();

	public static void main(String[] args) throws DAOException {
		// CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		// Company company = new Company();
		// company.setId(46149076);
		// company.setCompName("yanir");
		// company.setPassword("1234");
		// company.setEmail("yanir9@gmail.com");
		// company.setCoupons(null);
		// System.out.println(company.getCompName());

		CouponDBDAO couponDBDAO = new CouponDBDAO();
		CustomerFacade = new CustomerFacade();
		Coupon coupon1 = new Coupon();
		coupon1.setId(666);
		coupon1.setPrice(1234);
		coupon1.getEndDate();
		coupon1.setType(CouponType.CAMPING);
		// System.out.println(coupon1);
		// Coupon coupon2 = new Coupon();
		// coupon2.setId(444);
		// coupon2.setPrice(1124);
		// coupon2.setType(CouponType.ELECRICITY);
		// coupon2.getEndDate();
		// System.out.println(coupon2);

		try {
			// System.out.println("company is created");
			// companyDBDAO.deleteCompany(company);
			// companyDBDAO.createCompany(company);
			// companyDBDAO.isCompanyExsistByCompanyName(company.getCompName());
			// System.out.println("company is deleted");
			//
			// CustomerDBDAO customerDBDAO = new CustomerDBDAO();
			// Customer customer = new Customer(5, "yanir iah", "1234");

			// customerDBDAO.createCustomer(customer);

			couponDBDAO.createCoupon(coupon1);
			// couponDBDAO.createCoupon(coupon2);

			// couponDBDAO.getCouponsByMaxPrice(1100);
			// System.out.println(couponDBDAO.getCouponsByMaxPrice( 1200));
		} catch (DAOException e) {
			throw new DAOException("coupon Was Not Found!");
		}

	}
}
