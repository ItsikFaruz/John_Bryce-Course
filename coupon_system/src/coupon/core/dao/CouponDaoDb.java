package coupon.core.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import coupon.core.beans.Coupon;
import coupon.core.beans.Coupon.Category;
import coupon.core.exception.CouponSystemException;

public class CouponDaoDb implements CouponDao {

	@Override
	public int addCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "insert into coupon values (0 ,?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			pstmt.setInt(1, coupon.getCompanyId());
			pstmt.setString(2, coupon.getCategory().toString());
			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, Date.valueOf(coupon.getStartDate()));
			pstmt.setDate(6, Date.valueOf(coupon.getEndDate()));
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.executeUpdate();
			ResultSet rsId = pstmt.getGeneratedKeys();
			rsId.next();
			int id = rsId.getInt(1);
			return id;

		} catch (SQLException e) {
			throw new CouponSystemException("addCouponFaild", e);

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "update coupon set company_id = ? , category =? , title=?, description = ?, start_date=? , end_date=? , amount=?, price=?, image=? where id = ? ";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {

			pstmt.setInt(1, coupon.getCompanyId());
			pstmt.setString(2, coupon.getCategory().toString());
			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, Date.valueOf(coupon.getStartDate()));
			pstmt.setDate(6, Date.valueOf(coupon.getEndDate()));
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.setInt(10, coupon.getId());

			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("updateCoupon failed - coupon" + coupon.getId() + " not found:");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("updateCoupon faild", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	@Override
	public void deleteCoupon(int couponId) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();

		String sql = "delete from coupon where id = ? ";
		try (PreparedStatement pstmt2 = con.prepareStatement(sql);) {
			pstmt2.setInt(1, couponId);
			int rowCount = pstmt2.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("ERROR: the coupon:" + couponId + " Id not exist");

			}
		} catch (SQLException e) {
			throw new CouponSystemException("delete coupon faild", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * the method returns all coupons from database
	 */
	@Override
	public List<Coupon> getAllCoupon() throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select *from coupon";

		List<Coupon> coupons = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(rs.getDate("start_date").toLocalDate());
				coupon.setEndDate(rs.getDate("end_date").toLocalDate());
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);

			}

			return coupons;

		} catch (SQLException e) {
			throw new CouponSystemException("getAllCoupon faild", e);

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * the method pulls out all the details on a coupon from database
	 */
	@Override
	public Coupon getOneCoupon(int couponId) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select *from coupon where id = ?";

		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, couponId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(rs.getDate("start_date").toLocalDate());
				coupon.setEndDate(rs.getDate("end_date").toLocalDate());
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				return coupon;

			} else {
				throw new CouponSystemException("the coupon:" + couponId + " is not exist");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("getAllCoupon faild", e);

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * adding coupon purchase to database
	 */
	@Override
	public void addCouponPurchase(int customerId, int couponId) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "insert into customer_coupon values (?,?)";

		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("addCouponPurchase", e);

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * deleting coupon purchase from database
	 */
	@Override
	public void deleteCouponPurchase(int customerId, int couponId) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "delete from customer_coupon where customer_id = ? and coupon_id = ?";

		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("deleteCouponPurchase", e);

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	public void deleteAllCompanyCoupons(int companyId) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();

		String sql = "delete from coupon where company_id = ? ";
		try (PreparedStatement pstmt2 = con.prepareStatement(sql);) {
			pstmt2.setInt(1, companyId);
			int rowCount = pstmt2.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("ERROR: the coupons of company: " + companyId + " Id not exist");

			}
		} catch (SQLException e) {
			throw new CouponSystemException("deleteAllCompanyCoupons faild", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}
	
	public void deleteAllCouponPurchaseOfCustomer(int customerId ) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "delete from customer_coupon where customer_id = ?";

		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("deleteCouponPurchase", e);

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	
	
	
	
}