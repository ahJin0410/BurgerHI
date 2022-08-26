package first.team.burgerHi.adminKiosk.controller;

import java.util.List;
import java.util.Map;

import first.team.burgerHi.adminKiosk.model.dto.CategoryDTO;
import first.team.burgerHi.adminKiosk.model.dto.MenuDTO;
import first.team.burgerHi.adminKiosk.model.service.AdminService;

public class AdminController {
	private AdminService adminService = new AdminService();

	/* 카테고리별 판매 랭킹 */
	public Map<Integer, String> selectRanking(int category) {
		Map<Integer, String> menu = adminService.selectRanking(category);
		return menu;
	}

	/* 전체 카테고리 목록 조회 */
	public List<CategoryDTO> selectAllCategory() {
		List<CategoryDTO> categoryList = adminService.selectAllCategory();
		return categoryList;
	}

	/* 카테고리 추가 */
	public int registCategory(String categoryName, int refCategory) {
		int result = adminService.registCategory(categoryName, refCategory);
		return result;
	}

	/* 카테고리 수정 */
	public int modifyCategory(CategoryDTO category) {
		int result = adminService.modifyCategory(category);
		return result;
	}

	/* 카테고리 삭제 */
	public int deleteCategory(int categoryNo) {
		int result = adminService.deleteCategory(categoryNo);
		return result;
	}

	/* 전체 메뉴 목록 조회 */
	public List<MenuDTO> selectAllMenu() {
		List<MenuDTO> menuList = adminService.selectAllMenu();
		return menuList;
	}

	/* 메뉴 추가 */
	public int registtMenu(MenuDTO menu) {
		int result = adminService.registtMenu(menu);
		return result;
	}

	/* 메뉴 수정 */
	public int modifyMenu(MenuDTO menu) {
		int result = adminService.modifyMenu(menu);
		return result;
	}

	/* 메뉴 삭제 */
	public int deleteMenu(String menuName) {
		int menuCode = 0;
		List<MenuDTO> menuList = adminService.selectAllMenu();
		for(MenuDTO menu : menuList) {
			if(menu.getName().equals(menuName)) {
				menuCode = menu.getMenuCode();
				break;
			}
		}
		int result = adminService.deleteMenu(menuCode);
		return result;
	}

	/* 날짜별 매출 조회 */
	public int selectDateSales(int month, int date) {
		int monthSales = adminService.selectDateSales(month, date);
		return monthSales;
	}

	/* 총 매출 */
	public int selectAllSales() {
		int price = adminService.selectAllSales();
		return price;
	}

	/* 등급별 매출 */
	public Map<Integer, Integer> selectGradeSales() {
		Map<Integer, Integer> price = adminService.selectGradeSales();
		return price;
	}

	/* 결제 종류별 매출 */
	public Map<String, Integer> selectMethodSales() {
		Map<String, Integer> price = adminService.selectMethodSales();
		return price;
	}

	/* 전체 회원 목록 조회 */
	public List<Object> selectAllUser() {
		List<Object> user = adminService.selectAllUser();
		return user;
	}

	/* 회원 등급 수정 */
	public int updateUserGrade(int userNo, int gradeNo) {
		int result = adminService.updateUserGrade(userNo, gradeNo);
		return result;
	}

}
