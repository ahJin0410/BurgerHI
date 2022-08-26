package first.team.burgerHi.adminKiosk.model.service;

import static first.team.burgerHi.common.JDBCTemplate.close;
import static first.team.burgerHi.common.JDBCTemplate.commit;
import static first.team.burgerHi.common.JDBCTemplate.getConnection;
import static first.team.burgerHi.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import first.team.burgerHi.adminKiosk.model.dao.AdminDAO;
import first.team.burgerHi.adminKiosk.model.dto.CategoryDTO;
import first.team.burgerHi.adminKiosk.model.dto.MenuDTO;

public class AdminService {
	private AdminDAO adminDAO = new AdminDAO();
	
	/* 카테고리별 판매 랭킹 */
	public Map<Integer, String> selectRanking(int category) {
		Connection con = getConnection();
		Map<Integer, String> menu = adminDAO.selectRanking(con, category);
		close(con);
		return menu;
	}
	
	/* 전체 카테고리 목록 조회 */
	public List<CategoryDTO> selectAllCategory() {
		Connection con = getConnection();
		List<CategoryDTO> categoryList = adminDAO.selectAllCategory(con);
		close(con);
		return categoryList;
	}
	
	/* 카테고리 추가 */
	public int registCategory(String categoryName, int refCategory) {
		Connection con = getConnection();
		int result = adminDAO.registCategory(con, categoryName, refCategory);
		Transaction(result, con);
		return result;
	}
	
	/* 카테고리 수정 */
	public int modifyCategory(CategoryDTO category) {
		Connection con = getConnection();
		int result = adminDAO.updateCategory(con, category);
		Transaction(result, con);
		return result;
	}
	
	/* 카테고리 삭제 */
	public int deleteCategory(int categoryNo) {
		Connection con = getConnection();
		int result = adminDAO.deleteCategory(con, categoryNo);
		Transaction(result, con);
		return result;
	}
	
	/* 전체 메뉴 목록 조회 */
	public List<MenuDTO> selectAllMenu() {
		Connection con = getConnection();
		List<MenuDTO> menuList = adminDAO.selectAllMenu(con);
		close(con);
		return menuList;
	}
	
	/* 메뉴 추가 */
	public int registtMenu(MenuDTO menu) {
		Connection con = getConnection();
		int result = adminDAO.registtMenu(con, menu);
		Transaction(result, con);
		return result;
	}
	
	/* 메뉴 수정 */
	public int modifyMenu(MenuDTO menu) {
		Connection con = getConnection();
		int result = adminDAO.modifyMenu(con, menu);
		Transaction(result, con);
		return result;
	}
	
	/* 메뉴 삭제 */
	public int deleteMenu(int menuCode) {
		Connection con = getConnection();
		int result = adminDAO.deleteMenu(con, menuCode);
		Transaction(result, con);
		return result;
	}
	
	/* 날짜별 매출 조회 */
	public int selectDateSales(int month, int date) {
		Connection con = getConnection();
		int monthSales = 0;
		if(date == 0) {
			monthSales = adminDAO.selectMonthSales(con, month);
		} else {
			monthSales = adminDAO.selectDateSales(con, month, date);
		}
		close(con);
		return monthSales;
	}
	
	/* 총 매출 */
	public int selectAllSales() {
		Connection con = getConnection();
		int price = adminDAO.selectAllSales(con);
		close(con);
		return price;
	}

	/* 등급 별 매출 */
	public Map<Integer, Integer> selectGradeSales() {
		Connection con = getConnection();
		Map<Integer, Integer> price = adminDAO.selectGradeSales(con);
		close(con);
		return price;
	}

	/* 결제 종류 별 매출 */
	public Map<String, Integer> selectMethodSales() {
		Connection con = getConnection();
		Map<String, Integer> price = adminDAO.selectMethodSales(con);
		close(con);
		return price;
	}

	/* 전체 회원 목록 조회 */
	public List<Object> selectAllUser() {
		Connection con = getConnection();
		List<Object> user = adminDAO.selectAllUser(con);
		close(con);
		return user;
	}

	/* 회원 등급 수정 */
	public int updateUserGrade(int userNo, int gradeNo) {
		Connection con = getConnection();
		int result = adminDAO.updateUserGrade(con, userNo, gradeNo);
		Transaction(result, con);
		return result;
	}


	public void Transaction(int result, Connection con) {
		if(result > 0) {
			commit(con);
		} else {
			rollback(con);
		}
		close(con);
	}

}
