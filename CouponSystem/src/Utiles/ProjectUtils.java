package Utiles;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataTypes.Coupon;
import DataTypes.CouponType;

public class ProjectUtils {
	/**
	 * extract all the coupon information- id,title,satrt date,end
	 * date,amount,type,maessage,price and image drom the result set
	 */
	public static Coupon extractCouponFromResultSet(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("ID");
		String title = resultSet.getString("TITLE");
		Date startDate = resultSet.getDate("START_DATE");
		Date endDate = resultSet.getDate("END_DATE");
		int amount = resultSet.getInt("AMOUNT");
		CouponType couponType = CouponType.valueOf(resultSet.getString("TYPE"));
		String message = resultSet.getString("MESSAGE");
		double price = resultSet.getDouble("PRICE");
		String image = resultSet.getString("IMAGE");

		Coupon coupon = new Coupon(id, title, startDate, endDate, amount, couponType, message, price, image);

		return coupon;
	}
}
