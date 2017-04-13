package Test;

import java.util.Iterator;

import DataTypes.Customer;
import Exceptions.CouponSystemException;
import Facade.AdminFacade;

public class adminTest {

	public static void main(String[] args) {

		AdminFacade aFacade = new AdminFacade();
		try {
			// *********************CREATE COMPANY*******************

			// Company comp = new Company(111, "company1", "1234", "email");
			// System.out.println(aFacade.createCompany(comp));

			// **********************UPDATE COMPANY*******************

			// Company comp = new Company(111, "company1", "1234", "email");
			// System.out.println(aFacade.updateCompany(comp));

			// ************************GET COMPANY********************

			// Company comp = new Company(111, "company1", "1234", "email");
			// System.out.println(aFacade.getCompany(111));

			// **********************DELETE COMPANY*******************

			// Company comp = new Company(111, "company1", "1234", "email");
			// System.out.println(aFacade.deleteCompany(comp));

			// *********************GET ALL COMPANIES*****************
			// System.out.println(aFacade.getAllCompanies());

			// **********************CREATE CUSTOMER******************

			// Customer cust1 = new Customer(111, "roman", "1234");
			// Customer cust2 = new Customer(222, "puma", "12345");
			// Customer cust3 = new Customer(333, "nike", "123456");
			// aFacade.createCustomer(cust1);
			// aFacade.createCustomer(cust2);
			// aFacade.createCustomer(cust3);
			// System.out.println(cust1);

			// ********************UPDATE CUSTOMER********************
			// Customer cust1 = new Customer(147, "adidas", "0122234");
			// aFacade.updetCustomer(cust1);

			// **********************GET CUSTOMER*********************

			// Customer cust1 = new Customer(147, "adidas", "0122234");
			// aFacade.getCustomer((long) 147);
			// System.out.println(cust1);

			// **********************GET ALL CUSTOMER******************

			Iterator<Customer> it = aFacade.getAllCustomer().iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
				
			}
			// System.out.println(cust1);

			// **********************DELETE CUSTOMER******************

			// Customer cust1 = new Customer(147, "adidas", "1234");
			// aFacade.deleteCustomer(cust1);

		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
			Throwable t = e.getCause();
			while (t != null) {
				if (t instanceof CouponSystemException) {
					System.out.println(t.getMessage());
				}
				t = t.getCause();
			}

		}

	}

}
