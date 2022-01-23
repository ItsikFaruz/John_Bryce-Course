package companyFacadeTest;

import coupon.core.dao.ConnectionPool;
import coupon.core.exception.CouponSystemException;
import coupon.core.facades.CompanyFacade;

public class TestdeleteCoupon {

	public static void main(String[] args) {
		
		try {

			CompanyFacade companyfacade = new CompanyFacade("@@","1234");
			
			companyfacade.deleteCoupon(15);
			
			System.out.println("coupon deleted");
		} catch (CouponSystemException e) {

			e.printStackTrace();
		} finally {
			try {
				ConnectionPool.getInstance().closeAllConnections();
				System.out.println("all connections closed");
			} catch (CouponSystemException e) {
				e.printStackTrace();
			}
		}

	}

}