package Facade;

import java.util.ArrayList;
import java.util.Collection;

import DBDAO.CompanyDBDAO;
import DBDAO.CustomerDBDAO;
import DBDAO.JoinedTableDBDAO;
import DataTypes.Company;
import DataTypes.Customer;
import Exceptions.DAOException;
import Exceptions.FacadeException;

public class AdminFacade implements CouponClientFacade {

	// private long id;

	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	private CustomerDBDAO CustomerDBDAO = new CustomerDBDAO();
	private JoinedTableDBDAO joinedTableDBDAO = new JoinedTableDBDAO();

	// Ctor's
	public AdminFacade(long id) {
	}

	public AdminFacade() {
	}

	// /***************************************
	/**
	 * LOGIN METHOD FOR ADMIN.
	 *
	 * @param userName
	 *            the Admin userName
	 * @param password
	 *            the Admin password
	 */
	// public AdminFacade login(String password, String userName, ClientType
	// type) throws DAOException {
	// if (password.equals("1234") && userName.equals("admin") &&
	// type.equals("ADMIN")) {
	// return new AdminFacade();
	// } else {
	// throw new DAOException("Login Unsuccessfull! Incorrect USERNAME or
	// PASSWORD.");
	// }
	// }

	/**********************************************************************
	 * creaCompany method is checking if the company name is already exist, if
	 * true is't create a new company. if false, is't throws an exception.
	 */
	public void createCompany(Company company) throws FacadeException {
		try {
			if (companyDBDAO.isCompanyExsistByCompanyName(company.getCompName()) != true) {
				throw new FacadeException("Company Name Already Exsist");
			}
			companyDBDAO.createCompany(company);

		} catch (DAOException e) {
			throw new FacadeException("Create Company Failed", e);
		}
	}

	/**********************************************************************
	 * deleteCompany method is checking if the company id is exsist, if true
	 * is't delete the company. if false, is't throws an exception.
	 */
	public void deleteCompany(Company company) throws FacadeException {
		try {
			if (companyDBDAO.getCompany(company.getId()) == null) {
				throw new FacadeException("Company Is Not Exsist!");
			}
			// delete all customer coupons from the join table Customer_coupon
			joinedTableDBDAO.deleteCustomerFromCustomerCoupons(company.getId());
			// delete all company coupons from the join table Company_coupon
			companyDBDAO.deleteAllCompanyCoupons(company);
			// delete the company from the DB
			companyDBDAO.deleteCompany(company);
		} catch (DAOException e) {
			throw new FacadeException("ddelete Company Failed", e);
		}
	}

	/**********************************************************************
	 * updateCompany method is checking if the company exist by id, if true is't
	 * update the company. if false, is't throws an exception.
	 */
	public void updateCompany(Company company) throws FacadeException {
		try {
			companyDBDAO.updateCompany(company);
		} catch (DAOException e) {
			throw new FacadeException("Update Company Failed", e);

		}
	}

	/**********************************************************************
	 * getAllCompany method is getting a collections of companies from the
	 * Database.if true,that's means that the collection is empey, we get a an
	 * empty collection. if false, we get the collection of companies from the
	 * DB.
	 */
	public Collection<Company> getAllCompanies() throws FacadeException {
		Collection<Company> getAllCompanies = new ArrayList<>();
		try {
			getAllCompanies = companyDBDAO.getAllCompanies();
			if (getAllCompanies.isEmpty()) {
				// return an empty collection
				return getAllCompanies;
			}
			// return the collections of all companies
			return getAllCompanies;
		} catch (DAOException e) {
			throw new FacadeException("Getting Companies Failed", e);
		}
	}

	/**********************************************************************
	 * getCompany method get all company detail from the DB.
	 */
	public Company getCompany(long id) throws FacadeException {
		try {
			Company company = companyDBDAO.getCompany(id);
			if (company == null) {
				return null;
			}
			return company;
		} catch (DAOException e) {
			throw new FacadeException("Get Company Is Failed!", e);
		}
	}

	/**********************************************************************
	 * createCustomer method is creating a new customer in the database. we
	 * check if the customer is already exist by his name
	 */
	public void createCustomer(Customer customer) throws FacadeException {
		try {
			if (CustomerDBDAO.isCustomerExistByName(customer.getCustName()) == true) {
				throw new FacadeException("Customer Is Allredy Exsist");
			}
			// Creating the Customer
			CustomerDBDAO.createCustomer(customer);
		} catch (DAOException e) {
			throw new FacadeException("Creating Customer Failed!", e);
		}
	}

	/**********************************************************************
	 * deleteCustomer method is delete a customer by id from the DB.
	 */
	public void deleteCustomer(Customer customer) throws FacadeException {
		try {
			if (CustomerDBDAO.isCustomerExistById(customer.getId()) == true) {
				// delete the customer from the DB
				CustomerDBDAO.deleteCustomer(customer);
				// delete the customer and all of his purchase coupons from the
				// Custoner_coupon table
				joinedTableDBDAO.deleteCustomerPurchaseHistory(customer);
			}
		} catch (DAOException e) {
			throw new FacadeException("Delete Customer Failed!", e);
		}
	}

	/**********************************************************************
	 * updetCustomer method is updating customer by name
	 */
	public void updetCustomer(Customer customer) throws FacadeException {
		try {
			if (CustomerDBDAO.isCustomerExistByName(customer.getCustName()) == false) {
				throw new FacadeException("Customer Is Not Exsist");
			}
			CustomerDBDAO.updateCustomer(customer);
		} catch (DAOException e) {
			throw new FacadeException("Update Customer Failed!", e);
		}
	}

	/**********************************************************************
	 * getCustomer method is getting all the customer detail in the DB.
	 */
	public Customer getCustomer(Long id) throws FacadeException {

		try {
			Customer customer = CustomerDBDAO.getCustomer(id);
			return customer;
		} catch (DAOException e) {
			throw new FacadeException("Get Customer Failed", e);
		}
	}

	/**********************************************************************
	 * getAllCustomer method is getting a collections of customer from the db.if
	 * true, that's means that the collection is empty,we get a an empty
	 * collection. if false, we get the collection of customer
	 */
	public Collection<Customer> getAllCustomer() throws FacadeException {
		try {
			Collection<Customer> AllCustomersCollection = CustomerDBDAO.getAllCustomers();
			// if the collection is empty we return an empty collection
			return AllCustomersCollection;
		} catch (DAOException e) {
			throw new FacadeException("Get all Customers Failed", e);
		}
	}

}
