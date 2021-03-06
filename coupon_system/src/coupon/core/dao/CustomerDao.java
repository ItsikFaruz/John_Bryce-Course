package coupon.core.dao;

import java.util.List;

import coupon.core.beans.Coupon;
import coupon.core.beans.Customer;
import coupon.core.beans.Coupon.Category;
import coupon.core.exception.CouponSystemException;

public interface CustomerDao {

	boolean isCustomerExists(String email, String password) throws CouponSystemException;

	int addCustomer(Customer customer) throws CouponSystemException;

	void updateCustomer(Customer customer) throws CouponSystemException;

	void deleteCustomer(int customerId) throws CouponSystemException;

	Customer getOneCustomer(int customerId) throws CouponSystemException;

	List<Customer> getAllCustomer() throws CouponSystemException;

	boolean isEmailCustomerExists(String email) throws CouponSystemException;
	
	boolean isCustomerIdChanged(int customerId) throws CouponSystemException;
	
	List<Coupon> getAllCouponOfCustomer(int customerId) throws CouponSystemException;
	
	boolean checksCoupnPurchased(int customerId, int couponId) throws CouponSystemException;
	
	boolean checkCouponPurchased(int customerId ,Coupon coupon) throws CouponSystemException;

    int getCustomerId(String email, String password) throws CouponSystemException;
    
    List<Coupon> getAllCouponOfCustomer(int customerId,  Category category ) throws CouponSystemException;
    
    List<Coupon> getAllCouponOfCustomerBelwoMaxPrice(int customerId,  Double maxPrice ) throws CouponSystemException;
    
    
}
