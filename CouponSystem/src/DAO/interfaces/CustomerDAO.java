package DAO.interfaces;

import java.util.Collection;

import DataTypes.Coupon;
import DataTypes.Customer;
import Exceptions.DAOException;

public interface CustomerDAO {

	public void createCustomer(Customer customer) throws DAOException;

	public void deleteCustomer(Customer customer) throws DAOException;

	public void updateCustomer(Customer customer) throws DAOException;

	public Customer getCustomer(long id) throws DAOException;

	public Collection<Customer> getAllCustomers() throws DAOException;

	public Collection<Coupon> getCouponsByCustomer(Customer customer) throws DAOException;

	public Boolean login(String custName, String password) throws DAOException;

	public boolean isCustomerExistByName(String customer) throws DAOException;

	public boolean isCustomerExistById(long id) throws DAOException;

}
