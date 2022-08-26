package first.team.burgerHi.adminKiosk.model.dao;

import static first.team.burgerHi.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import first.team.burgerHi.adminKiosk.model.dto.CategoryDTO;
import first.team.burgerHi.adminKiosk.model.dto.MenuDTO;

public class AdminDAO {
	Properties prop = new Properties();		// xml 파일로 저장되어 있는 쿼리를 불러오기 위한 인스턴스 생성
	
	public AdminDAO() {
		try {
			prop.loadFromXML(new FileInputStream("mapper/burgerhi-query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, String> selectRanking(Connection con, int category) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Map<Integer, String> menu = new HashMap<Integer, String>();
		String query = prop.getProperty("selectMenuRankingBy");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, category);
			rset = pstmt.executeQuery();
			
			for(int i = 1; rset.next(); i++) {
				menu.put(i, rset.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return menu;
	}
	
	/* 전체 카테고리 목록 조회 */
	public List<CategoryDTO> selectAllCategory(Connection con) {
		Statement stmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectAllCategory");
		List<CategoryDTO> categoryList = new ArrayList<>();
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				CategoryDTO cate = new CategoryDTO();
				cate.setCode(rset.getInt("CATEGORY_CODE"));
				cate.setName(rset.getString("CATEGORY_NAME"));
				cate.setRefCode(rset.getInt("REF_CATEGORY_CODE"));
				cate.setRefName(rset.getString(4));
				categoryList.add(cate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		return categoryList;
	}

	/* 카테고리 추가 */
	public int registCategory(Connection con, String categoryName, int refCategory) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("registCategory");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, categoryName);
			pstmt.setInt(2, refCategory);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/* 카테고리 수정 */
	public int updateCategory(Connection con, CategoryDTO category) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("modifyCategory");
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, category.getName());
			pstmt.setInt(2, category.getRefCode());
			pstmt.setInt(3, category.getCode());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/* 카테고리 삭제 */
	public int deleteCategory(Connection con, int categoryNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteCategory");
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, categoryNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/* 전체 메뉴 목록 조회 */
	public List<MenuDTO> selectAllMenu(Connection con) {
		Statement stmt = null;
		ResultSet rset = null;
		List<MenuDTO> menuList = new ArrayList<>();
		String Query = prop.getProperty("selectAllMenu");
		
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(Query);
			
			while(rset.next()) {
				MenuDTO menu = new MenuDTO();
				menu.setMenuCode(rset.getInt("MENU_CODE"));
				menu.setName(rset.getString("MENU_NAME"));
				menu.setPrice(rset.getInt("PRICE"));
				menu.setExplain(rset.getString("MENU_EXPLAIN"));
				menu.setCategoryCode(rset.getInt("CATEGORY_CODE"));
				menu.setOrderable(rset.getString("ORDERABLE"));
				
				menuList.add(menu);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		return menuList;
	}

	/* 메뉴 추가 */
	public int registtMenu(Connection con, MenuDTO menu) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("registtMenu");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, menu.getName());
			pstmt.setInt(2, menu.getPrice());
			pstmt.setString(3, menu.getExplain());
			pstmt.setInt(4, menu.getCategoryCode());
			pstmt.setString(5, menu.getOrderable());	
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	/* 메뉴 수정 */
	public int modifyMenu(Connection con, MenuDTO menu) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("updateMenu");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, menu.getName());
			pstmt.setInt(2, menu.getPrice());
			pstmt.setString(3, menu.getExplain());
			pstmt.setInt(4, menu.getCategoryCode());
			pstmt.setString(5, menu.getOrderable());	
			pstmt.setInt(6, menu.getMenuCode());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	/* 메뉴 삭제 */
	public int deleteMenu(Connection con, int menuCode) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteMenu");
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, menuCode);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	/* 월별 매출 조회 */
	public int selectMonthSales(Connection con, int month) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int monthSales = 0;
		String query = prop.getProperty("selectMonthSales");

		try {
			pstmt = con.prepareStatement(query);
			if (month < 10) {
				pstmt.setString(1, "0" + month);
			} else {
				pstmt.setString(1, "" + month);
			}

			rset = pstmt.executeQuery();

			if (rset.next()) {
				monthSales = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return monthSales;
	}

	/* 일별 매출 조회 */
	public int selectDateSales(Connection con, int month, int date) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int dateSales = 0;
		String query = prop.getProperty("selectDateSales");

		try {
			pstmt = con.prepareStatement(query);
			if (month < 10) {
				pstmt.setString(1, "0" + month);
			} else {
				pstmt.setString(1, "" + month);
			}
			if (date < 10) {
				pstmt.setString(2, "0" + date);
			} else {
				pstmt.setString(2, "" + date);
			}

			rset = pstmt.executeQuery();

			while (rset.next()) {
				dateSales = rset.getInt(2);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return dateSales;
	}

	/* 총 매출 */
	public int selectAllSales(Connection con) {
		Statement stmt = null;
		ResultSet rset = null;
		int allSales = 0;
		String query = prop.getProperty("selectAllSales");
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				allSales = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		return allSales;
	}

	/* 등급별 매출 */
	public Map<Integer, Integer> selectGradeSales(Connection con) {
		Statement stmt = null;
		ResultSet rset = null;
		Map<Integer, Integer> gradeSales = new HashMap<Integer, Integer>();
		String query = prop.getProperty("selectGradeSales");
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			
			for(int i = 0; rset.next(); i++) {
				gradeSales.put(i, rset.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		return gradeSales;
	}

	/* 결제 종류 별 매출 */
	public Map<String, Integer> selectMethodSales(Connection con) {
		Statement stmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectMethodSales");
		Map<String, Integer> methodSales = new HashMap<String, Integer>();
		
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				methodSales.put(rset.getString(1), rset.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		return methodSales;
	}

	/* 전체 회원 목록 조회 */
	public List<Object> selectAllUser(Connection con) {
		Statement stmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectAllUser");
		List<Object> user = new ArrayList<Object>();
		
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				user.add(rset.getInt("USER_NO"));
				user.add(rset.getString("USER_NAME"));
				user.add(rset.getString("USER_ID"));
				user.add(rset.getString("GRADE_NAME"));
				user.add(rset.getInt("USER_POINT"));
				user.add(rset.getString("PHONE"));
				user.add(rset.getString("USER_YN"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		return user;
	}

	/* 회원 등급 수정 */
	public int updateUserGrade(Connection con, int userNo, int gradeNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("modifyUserGrade");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, gradeNo);
			pstmt.setInt(2, userNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

}
