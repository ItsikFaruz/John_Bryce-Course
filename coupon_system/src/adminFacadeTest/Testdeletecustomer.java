package adminFacadeTest;

import coupon.core.dao.ConnectionPool;
import coupon.core.exception.CouponSystemException;
import coupon.core.facades.AdminFacade;

public class Testdeletecustomer {

	public static void main(String[] args) {
		try {
			

			AdminFacade admin = new AdminFacade();

			admin.deleteCustomer(13);;

			System.out.println("customer deleted ");

			
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