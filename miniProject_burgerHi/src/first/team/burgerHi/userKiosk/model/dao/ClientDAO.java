package first.team.burgerHi.userKiosk.model.dao;

import static first.team.burgerHi.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import first.team.burgerHi.userKiosk.model.dto.CardDTO;
import first.team.burgerHi.userKiosk.model.dto.CategoryDTO;
import first.team.burgerHi.userKiosk.model.dto.GifticonDTO;
import first.team.burgerHi.userKiosk.model.dto.MenuDTO;
import first.team.burgerHi.userKiosk.model.dto.OrderMenuAndMenuDTO;
import first.team.burgerHi.userKiosk.model.dto.SplitPaymentDTO;
import first.team.burgerHi.userKiosk.model.dto.UserDTO;

public class ClientDAO {
	Properties prop = new Properties();		// xml 파일로 저장되어 있는 쿼리를 불러오기 위한 인스턴스 생성
	
	public ClientDAO() {
		try {
			prop.loadFromXML(new FileInputStream("mapper/burgerhi-query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* 로그인 */
	public UserDTO selectByMemberInfo(Connection con, Map<String, String> loginInfo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectByIdAndPwd");
		UserDTO user = new UserDTO();
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, loginInfo.get("id"));
			pstmt.setString(2, loginInfo.get("pwd"));
			rset = pstmt.executeQuery();
			if(rset.next()) {
				user.setUserNo(rset.getInt("USER_NO"));
				user.setName(rset.getString("USER_NAME"));
				user.setId(rset.getString("USER_ID"));
				user.setGradeNo(rset.getInt("GRADE_NO"));
				user.setUserPoint(rset.getInt("USER_POINT"));
				user.setPhone(rset.getString("PHONE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
	      return user;
	}
	
	/* 아이디 중복 체크 */
	public int selectByMemberId(Connection con, String memberId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectByMemberId");
		int result = 0;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, memberId);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}
	
	/* 회원가입 */
	public int registMember(Connection con, UserDTO userDTO) {
		PreparedStatement pstmt = null;
		String qeury = prop.getProperty("registMember");
		int result = 0;
		try {
			pstmt = con.prepareStatement(qeury);
			pstmt.setString(1, userDTO.getName());
			pstmt.setString(2, userDTO.getId());
			pstmt.setString(3, userDTO.getPwd());
			pstmt.setInt(4, 1);
			pstmt.setString(5, userDTO.getPhone());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	/* 회원 정보 확인 */
	public UserDTO selectMemberInfo(Connection con, int userNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectMemberInfo");
		UserDTO user = new UserDTO();
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				user.setUserNo(rset.getInt("USER_NO"));
				user.setName(rset.getString("USER_NAME"));
				user.setId(rset.getString("USER_ID"));
				user.setGradeNo(rset.getInt("GRADE_NO"));
				user.setUserPoint(rset.getInt("USER_POINT"));
				user.setPhone(rset.getString("PHONE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
	      return user;
	}
	
	/* 회원 정보 수정 */
	public int updateUserInfo(Connection con, UserDTO user) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("updateUserInfo");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, user.getPwd());
			pstmt.setString(2, user.getPhone());
			pstmt.setInt(3, user.getUserNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/* 회원 탈퇴 */
	public int deleteMemberInfo(Connection con, int userNo) {
		/* USER_YN 컬럼의 데이터만 Y → N (즉, Update문 사용) */
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteMemberInfo");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/* 전체 카테고리 조회 */
	public List<CategoryDTO> selectAllCategory(Connection con) {
		Statement stmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectAllCategory");
		List<CategoryDTO> categoryList = new ArrayList<>();
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			while(rset.next()) {
				CategoryDTO category = new CategoryDTO();
				category.setCode(rset.getInt("CATEGORY_CODE"));
				category.setName(rset.getString("CATEGORY_NAME"));
				category.setRefCode(rset.getInt("REF_CATEGORY_CODE"));
				categoryList.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		return categoryList;
	}

	/* 사용자가 선택한 카테고리의 전체 메뉴 조회 */
	public List<MenuDTO> memberOrderMenuBy(Connection con, int categoryNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectMenuByCategory");
		List<MenuDTO> menuList = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, categoryNo);
			
			rset = pstmt.executeQuery();
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
		} finally {
			close(rset);
			close(pstmt);
		}
		return menuList;
	}
	
	/* 사용자가 선택한 메뉴 장바구니에 담기 */
	public int registOrderMenu(Connection con, Map<String, String> registMenu) {
		PreparedStatement pstmt = null;
		String query = prop.getProperty("registOrderMenu");
		int result = 0;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.valueOf(registMenu.get("userNo")));
			pstmt.setInt(2, Integer.valueOf(registMenu.get("menuNo")));
			pstmt.setInt(3, Integer.valueOf(registMenu.get("amount")));
			pstmt.setString(4, registMenu.get("setMenuYn"));
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/* 장바구니 확인 */
	public List<OrderMenuAndMenuDTO> selectOrderMenu(Connection con, int userNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectOrderMenu");
		List<OrderMenuAndMenuDTO> orderMenuList = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userNo);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				OrderMenuAndMenuDTO menu = new OrderMenuAndMenuDTO();
				menu.setOrderMenuNo(rset.getInt("ORDER_MENU_NO"));
				menu.setMenuCode(rset.getInt("MENU_CODE"));
				menu.setName(rset.getString("MENU_NAME"));
				menu.setOrderAmount(rset.getInt("ORDER_AMOUNT"));
				menu.setPrice(rset.getInt("PRICE"));
				menu.setOrderMenuSetYn(rset.getString("ORDER_MENU_SET_YN"));
				
				orderMenuList.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return orderMenuList;
	}

	/* 장바구니 수정 */
	public int modifyOrderMenu(Connection con, int[] deleteOrderMenuNo) {
		PreparedStatement pstmt = null;
		String query = prop.getProperty("deleteOrderMenu");
		int result = 0;
		try {
			for(int i = 0; i < deleteOrderMenuNo.length; i++) {
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, deleteOrderMenuNo[i]);
				
				result += pstmt.executeUpdate();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/* 회원 등급별 할인 조회 */
	public int selectGradeByGradeNo(Connection con, int gradeNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int gradeDiscount = 0;
		String query = prop.getProperty("selectGradeByGradeNo");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, gradeNo);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				gradeDiscount = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return gradeDiscount;
	}

	/* 카드 목록 조회 */
	public List<CardDTO> selectAllCard(Connection con) {
		Statement stmt = null;
		ResultSet rset = null;
		List<CardDTO> cardList = new ArrayList<CardDTO>();
		String query = prop.getProperty("selectAllCard");
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				CardDTO card = new CardDTO();
				card.setCode(rset.getInt("CARD_CODE"));
				card.setBank(rset.getString("CARD_BANK"));
				card.setDiscount(rset.getNString("CARD_DISCOUNT"));
				card.setCardable(rset.getString("CARDABLE"));
				
				cardList.add(card);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		
		return cardList;
	}

	/* 결제가 완료된 정보를 주문 테이블에 삽입 */
	public int registOrder(Connection con, int paymentPrice) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("registOrder");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, paymentPrice);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/* 결제가 완료된 정보를 결제 테이블에 삽입 */
	public int registPayment(Connection con, SplitPaymentDTO splitpay) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("registPayment");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, splitpay.getUserNo());
			pstmt.setInt(2, splitpay.getTotalPrice());
			pstmt.setInt(3, splitpay.getGradeNo());
			pstmt.setInt(4, splitpay.getCardCode());
			pstmt.setInt(5, splitpay.getPaymentPrice());
			pstmt.setString(6, splitpay.getPaymentBy());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/* 결제가 완료된 정보를 판매이력 테이블에 삽입 */
	public int registSalesAmount(Connection con, int menuCode, int amount, int totalPrice) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("registSalesAmount");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, menuCode);
			pstmt.setInt(2, amount);
			pstmt.setInt(3, totalPrice);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/* 결제가 완료된 장바구니 메뉴 삭제 */
	public int deleteOrderMenu(Connection con, int userNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteOrderMenu");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/* 적립된 포인트 update */
	public int modifyUserPoint(Connection con, int userNo, int newPoint) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("modifyMemberPoint");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, newPoint);
			pstmt.setInt(2, userNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	/* 적립이 완료 된 누적된 총 포인트 금액 */
	public int selectUserPoint(Connection con, int userNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totalPoint = 0;
		
		String query = prop.getProperty("selectUserPoint");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userNo);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				totalPoint = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalPoint;
	}

	/* 등급 업그레이드 */
	public int modifyUserGrade(Connection con, int userNo, int membershipgrade) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("modifyUserGrade");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, membershipgrade);
			pstmt.setInt(2, userNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/* 이벤트 기프티콘 생성 */
	public int registGifticonEvent(Connection con, int eventPrice) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("registGifticonEvent");
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, eventPrice);
				
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}
		return result;
	}

	/* 이벤트 기프티콘 조회 */
	public GifticonDTO selectGifticon(Connection con) {
		Statement stmt = null;
		ResultSet rset = null;
		GifticonDTO gifticon = new GifticonDTO();
		String query = prop.getProperty("selectlastgifticon");
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				gifticon.setNo(rset.getString(1));
				gifticon.setPrice(rset.getInt(2));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		close(stmt);
		return gifticon;
	}

	/* 사용 할 기프티콘 금액 조회 */
	public int selectGifticonBy(Connection con, String gifticonNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int gifticonPrice = 0;
		String query = prop.getProperty("selectGifticonBy");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, gifticonNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				gifticonPrice = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return gifticonPrice;
	}

	/* 사용한 기프티콘 금액 수정 */
	public int modifyGifticonPrice(Connection con, GifticonDTO gifticon) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("modifyGifticonPrice");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, gifticon.getPrice());
			pstmt.setString(2, gifticon.getNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/* 비회원 등록 */
	public int registNonMember(Connection con) {
		PreparedStatement pstmt = null;
		String qeury = prop.getProperty("registMember");
		int result = 0;
		try {
			pstmt = con.prepareStatement(qeury);
			pstmt.setString(1, null);
			pstmt.setString(2, null);
			pstmt.setString(3, null);
			pstmt.setInt(4, 5);
			pstmt.setString(5, null);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	/* 등록한 비회원 조회 */
	public UserDTO selectByNonMemberInfo(Connection con) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectNonMemberInfo");
		UserDTO user = new UserDTO();
		try {
			pstmt = con.prepareStatement(query);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				user.setUserNo(rset.getInt("USER_NO"));
				user.setGradeNo(rset.getInt("GRADE_NO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
	      return user;
	}




}
