package first.team.burgerHi.userKiosk.model.service;

import static first.team.burgerHi.common.JDBCTemplate.close;
import static first.team.burgerHi.common.JDBCTemplate.commit;
import static first.team.burgerHi.common.JDBCTemplate.getConnection;
import static first.team.burgerHi.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import first.team.burgerHi.userKiosk.model.dao.ClientDAO;
import first.team.burgerHi.userKiosk.model.dto.CardDTO;
import first.team.burgerHi.userKiosk.model.dto.CategoryDTO;
import first.team.burgerHi.userKiosk.model.dto.GifticonDTO;
import first.team.burgerHi.userKiosk.model.dto.MenuDTO;
import first.team.burgerHi.userKiosk.model.dto.OrderMenuAndMenuDTO;
import first.team.burgerHi.userKiosk.model.dto.SplitPaymentDTO;
import first.team.burgerHi.userKiosk.model.dto.UserDTO;

public class ClientService {
	private ClientDAO clientDAO = new ClientDAO();

	/* 회원가입 */
	public UserDTO memberLogin(Map<String, String> loginInfo) {
		Connection con = getConnection();
		UserDTO user = clientDAO.selectByMemberInfo(con, loginInfo);
		close(con);
		return user;
	}

	/* 아이디 중복 체크 */
	public int checkMemberId(String memberId) {
		Connection con = getConnection();
		int result = clientDAO.selectByMemberId(con, memberId);
		close(con);
		return result;
	}
	
	/* 회원가입 */
	public int registMember(UserDTO user) {
		Connection con = getConnection();
		int result = clientDAO.registMember(con, user);
		Transaction(result, con);
		return result;
	}
	
	/* 회원 정보 확인 */
	public UserDTO selectMemberInfo(int userNo) {
		Connection con = getConnection();
		UserDTO user = clientDAO.selectMemberInfo(con, userNo);
		close(con);
		return user;
	}
	
	/* 회원 정보 수정 */
	public int updateUserInfo(UserDTO user) {
		Connection con = getConnection();
		int result = clientDAO.updateUserInfo(con, user);
		Transaction(result, con);
		return result;
	}
	
	/* 회원 탈퇴 */
	public int deleteMemberInfo(UserDTO user) {
		Connection con = getConnection();
		int result = clientDAO.deleteMemberInfo(con, user.getUserNo());
		Transaction(result, con);
		return result;
	}

	/* 전체 카테고리  조회 */
	public List<CategoryDTO> selectAllCategory() {
		Connection con = getConnection();
		List<CategoryDTO> categoryList = clientDAO.selectAllCategory(con);
		close(con);
		return categoryList;
	}
	
	/* 사용자가 선택한 카테고리의 전체 메뉴 조회 */
	public List<MenuDTO> memberOrderMenuBy(int categoryNo) {
		Connection con = getConnection();
		List<MenuDTO> menuList = clientDAO.memberOrderMenuBy(con, categoryNo);
		close(con);
		return menuList;
	}
	
	/* 사용자가 선택한 메뉴 장바구니에 담기 */
	public int registOrderMenu(Map<String, String> registMenu) {
		Connection con = getConnection();
		int result = clientDAO.registOrderMenu(con, registMenu);
		Transaction(result, con);
		return result;
	}
	
	/* 장바구니 확인 */
	public List<OrderMenuAndMenuDTO> selectOrderMenu(int userNo) {
		Connection con = getConnection();
		List<OrderMenuAndMenuDTO> orderMenuList = clientDAO.selectOrderMenu(con, userNo);
		close(con);
		return orderMenuList;
	}
	
	/* 장바구니 수정 */
	public int modifyOrderMenu(int[] deleteOrderMenuNo) {
		Connection con = getConnection();
		int result = clientDAO.modifyOrderMenu(con, deleteOrderMenuNo);
		if(result == deleteOrderMenuNo.length) {
			commit(con);
		} else {
			result = 0;
			rollback(con);
		}
		close(con);
		return result;
	}
	
	/* 세트메뉴 장바구니에 담기 */
	public int registOrderSetMenu(int userNo, int[] basicSet, int inputAmount, String setMenuYn) {
		Connection con = getConnection();
		int result = 0;
		
		Map<String, String> registMenu = new HashMap<>();
		registMenu.put("userNo", String.valueOf(userNo));
		registMenu.put("amount", String.valueOf(inputAmount));
		registMenu.put("setMenuYn", setMenuYn);
		
		for(int i = 0; i < basicSet.length; i++) {
			registMenu.put("menuNo", String.valueOf(basicSet[i]));
			result += clientDAO.registOrderMenu(con, registMenu);
		}
		
		if(result == basicSet.length) {
			commit(con);
		} else {
			result = 0;
			rollback(con);
		}
		close(con);
		return result;
	}
	
	/* 카드 목록 조회 */
	public List<CardDTO> selectAllCard() {
		Connection con = getConnection();
		List<CardDTO> cardList = clientDAO.selectAllCard(con);
		close(con);
		return cardList;
	}
	
	/* 회원 등급별 할인 조회 */
	public int selectGradeByGradeNo(int gradeNo) {
		Connection con = getConnection();
		int gradeDiscount = clientDAO.selectGradeByGradeNo(con, gradeNo);
		close(con);
		return gradeDiscount;
	}
	
	/* 결제 완료 후 실행되야 할 로직처리 */
	public int registPaymentTotal(SplitPaymentDTO splitpay) {
		int result = 0;
		int salesResult = 0;
		int totalPrice = 0;
		Connection con = getConnection();
		if(splitpay.getSplitPaymentYn().equals("Y")) {
			int paymentResult = clientDAO.registPayment(con, splitpay);
			Transaction(paymentResult, con);
			result = 1;
		} else {
			int orderResult = clientDAO.registOrder(con, splitpay.getPaymentPrice());
			int paymentResult = clientDAO.registPayment(con, splitpay);
			List<OrderMenuAndMenuDTO> orderMenuList = clientDAO.selectOrderMenu(con, splitpay.getUserNo());
			for(OrderMenuAndMenuDTO order : orderMenuList) {
				int price = order.getPrice();
				int amount = order.getOrderAmount();
				int menuCode = order.getMenuCode();
				totalPrice = price * amount;
				salesResult += clientDAO.registSalesAmount(con, menuCode, amount, totalPrice);
			}
			int deleteResult = clientDAO.deleteOrderMenu(con, splitpay.getUserNo());
			
			if(orderResult > 0 && paymentResult > 0 && salesResult == orderMenuList.size() && deleteResult > 0) {
				commit(con);
				result = 1;
			} else {
				rollback(con);
			}
		}
		close(con);
		return result;
	}

	/* 멤버십 포인트 적립 */
	public int memberGradePoint(int userNo, int newPoint, int gradeNo) {
		int totalPoint = 0;
		Connection con = getConnection();
		int updatePointResult = clientDAO.modifyUserPoint(con, userNo, newPoint);
		if(updatePointResult > 0) {
			totalPoint = clientDAO.selectUserPoint(con, userNo);
			commit(con);
		} else {
			rollback(con);
		}
		close(con);
		return totalPoint;
	}
	
	/* 등급 업그레이드 */
	public void modifyUserGrade(int userNo, int membershipgrade) {
		Connection con = getConnection();
		int result = clientDAO.modifyUserGrade(con, userNo, membershipgrade);
		Transaction(result, con);
	}
	
	/* 이벤트 기프티콘 생성 및 조회 */
	public GifticonDTO selectlastgifticon(int eventPrice) {
		GifticonDTO gifticon = new GifticonDTO();
		Connection con = getConnection();
		int result = clientDAO.registGifticonEvent(con, eventPrice);
		if(result > 0) {
			gifticon = clientDAO.selectGifticon(con);
			commit(con);
		} else {
			rollback(con);
		}
		close(con);
		return gifticon;
	}
	
	/* 사용할 기프티콘 금액 조회 */
	public int selectGifticonBy(String gifticonNo) {
		Connection con = getConnection();
		int gifticonPrice = clientDAO.selectGifticonBy(con, gifticonNo);
		close(con);
		return gifticonPrice;
	}
	
	/* 사용한 기프티콘 금액 수정 */
	public void modifyGifticonPrice(GifticonDTO gifticon) {
		Connection con = getConnection();
		int result = clientDAO.modifyGifticonPrice(con, gifticon);
		Transaction(result, con);
	}
	
	/* 비회원 회원번호&등급 등록 */
	public UserDTO registNonMember() {
		UserDTO nonMember = new UserDTO();
		Connection con = getConnection();
		int result = clientDAO.registNonMember(con);
		if(result > 0) {
			commit(con);
			nonMember = clientDAO.selectByNonMemberInfo(con);
		} else {
			rollback(con);
		}
		close(con);
		return nonMember;
	}

	/* 비회원의 경우 주문 취소여도 장바구니 삭제 되도록 */
	public void deleteOrderMenu(int userNo) {
		Connection con = getConnection();
		int deleteResult = clientDAO.deleteOrderMenu(con, userNo);
		Transaction(deleteResult, con);
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
