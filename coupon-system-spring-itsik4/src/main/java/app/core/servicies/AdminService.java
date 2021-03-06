package app.core.servicies;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;

@Component
@Transactional
public class AdminService extends ClientService {

	@Value("${admin.email}")
	private String email;
	@Value("${admin.password}")
	private String password;
	
	
	
	/**
	 * Checks if password and email are correct
	 * 
	 * @param email
	 * @param password
	 * @return true or false
	 */
	@Override
	public boolean login(String email, String password) {
		return email.equals(this.email) && password.equals(this.password);
	}

	/**
	 * add company to database only if name or email company not exist
	 * 
	 * @param company
	 * @return the id of the company
	 * @throws CouponSystemException
	 */
	public int addCompany(Company company) throws CouponSystemException {
		if (companyRepo.existsByEmailOrName(company.getEmail(), company.getName()))
			throw new CouponSystemException("addCompany faild - email or name already exist");
		else
			companyRepo.save(company);
		return company.getId();
	}

	
	/**
	 * update company - (can`t change the id or name company).
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		Optional<Company> opt = companyRepo.findById(company.getId());
		if (opt.isPresent()) {
			Company companyFromDb = opt.get();
			companyFromDb.setEmail(company.getEmail());
			companyFromDb.setPassword(company.getPassword());
		} else
			throw new CouponSystemException("updateCompany faild - company not exist");
	}

	/**
	 * delete company and all her coupons and coupon purchased
	 * 
	 * @param company id
	 * @throws CouponSystemException
	 */
	public void deleteCompany(int companyId) throws CouponSystemException {
		Optional<Company> opt = companyRepo.findById(companyId);
		if (opt.isPresent()) {
			companyRepo.deleteById(companyId);
			System.out.println("Company " + companyId + " deleted" );
		} else
			throw new CouponSystemException("deleteCompanyr faild - customer " + companyId + " not exist");
	}

	/**
	 * get all companies from database
	 * 
	 * @return ArrayList of all companies
	 * @throws CouponSystemException
	 */
	public ArrayList<Company> getAllCompany() {
		ArrayList<Company> allCompany = (ArrayList<Company>) companyRepo.findAll();
		return allCompany;
	}

	
	/**
	 * @return Company
	 * @throws CouponSystemException
	 */
	public Company getOneCompany(int companyId) throws CouponSystemException {
		Optional<Company> opt = companyRepo.findById(companyId);
		if (opt.isPresent()) {
			return opt.get();
		} else
			throw new CouponSystemException("getOneCustomer faild - customer " + companyId + " not exist");
	}
	
	/**
	 * adding customer to database and return the ID only if email not exist
	 * 
	 * @param customer
	 * @return customer ID
	 * @throws CouponSystemException
	 */
	public int addCustomer (Customer customer) throws CouponSystemException {
	if	(!customerRepo.existsByEmail(customer.getEmail())) {	
		customerRepo.save(customer);
		return customer.getId();
	}
		else
			throw new CouponSystemException("addCustomer faild - this email already exist ");	
	}
	
	/**
	 * update customer details ( id can`t change )
	 * 
	 * @param customer
	 * @throws CouponSystemException
	 */
	public void updateCustomer(Customer customer1) throws CouponSystemException {
		Optional<Customer> opt = customerRepo.findById(customer1.getId());
		if (opt.isPresent()) {
			Customer customerFromDb = opt.get();
			customerRepo.save(customerFromDb);
		} else
			throw new CouponSystemException("updateCustomer faild - customer ID not exist");
	}
	
	/**
	 * deletes a customer and all his purchases
	 * 
	 * @param customerId
	 * @throws CouponSystemException
	 */
	public void deleteCustomer (int customerId) throws CouponSystemException {
		Optional<Customer> opt = customerRepo.findById(customerId);
		if (opt.isPresent()) {
			customerRepo.deleteById(customerId);
			System.out.println("Customer " + customerId + " deleted" );
		} else
			throw new CouponSystemException("deleteCustomer faild - customer " + customerId + " not exist");
	}
	
	/**
	 * @return array list of all customer
	 * @throws CouponSystemException
	 */
	public ArrayList<Customer> getAllCustomer() {
		ArrayList<Customer> allCustomer = (ArrayList<Customer>) customerRepo.findAll();
		return allCustomer;
	}
	
	/**
	 * @param customerId
	 * @return customer details
	 * @throws CouponSystemException
	 */
	public Customer getOneCustomer ( int customerId) throws CouponSystemException {
		Optional<Customer> opt = customerRepo.findById(customerId);
		if (opt.isPresent()) {
			return opt.get();
		} else
			throw new CouponSystemException("getOneCustomer faild - customer " + customerId + " not exist");
	}
	
	/**
	 * Deletes all expired coupons
	 */

	public void deleteExpiredCoupon () {
		couponRepo.deleteByEndDateBefore(LocalDate.now());
	}
	
}
