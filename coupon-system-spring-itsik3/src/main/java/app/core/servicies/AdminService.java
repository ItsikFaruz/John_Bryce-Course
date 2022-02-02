package app.core.servicies;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;

@Component
@Transactional
public class AdminService extends ClientService {

	private final String email = "admin@admin.com";
	private final String password = "admin";

	@Override
	public boolean login(String email, String password) {
		return email.equals(this.email) && password.equals(this.password);
	}

	/**
	 * add company
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	public int addCompany(Company company) throws CouponSystemException {
		if (companyRepo.existsByEmailOrName(company.getEmail(), company.getName()))
			throw new CouponSystemException("addCompany faild - email or name already exist");
		else
			companyRepo.save(company);
		return company.getId();
	}

	public void updateCompany(Company company) throws CouponSystemException {
		Optional<Company> opt = companyRepo.findById(company.getId());
		if (opt.isPresent()) {
			Company companyFromDb = opt.get();
			companyFromDb.setEmail(company.getEmail());
			companyFromDb.setPassword(company.getPassword());
		} else
			throw new CouponSystemException("updateCompany faild - company not exist");

	}

	public void deleteCompany(int companyId) {
		companyRepo.deleteById(companyId);
	}

	public ArrayList<Company> getAllCompany() {
		ArrayList<Company> allCompany = (ArrayList<Company>) companyRepo.findAll();
		return allCompany;
	}

	public Company getOneCompany(int companyId) throws CouponSystemException {
		Optional<Company> opt = companyRepo.findById(companyId);
		if (opt.isPresent()) {
			return opt.get();
		} else
			throw new CouponSystemException("getOneCompany faild - company not exist");
	}
	
	public void addCustomer (Customer customer) throws CouponSystemException {
	if	(!customerRepo.existsByEmail(customer.getEmail()))	
		customerRepo.save(customer);
		else
			throw new CouponSystemException("addCustomer faild - this email already exist ");
				
		
	}

}
