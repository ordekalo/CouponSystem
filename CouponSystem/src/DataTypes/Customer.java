package DataTypes;

import java.io.Serializable;
import java.util.Collection;

public class Customer implements Serializable {

	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	public Customer() {
		super();
	}

	public Customer(long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
		this.coupons = coupons;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Override
	public String toString() {
		return "Customer [id= " + id + ", custName= " + custName + ", password= " + password + "]";
		// , coupons= " + coupons
		// + coupons.toString() + "]";
	}

}
