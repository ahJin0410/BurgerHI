package first.team.burgerHi.userKiosk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import first.team.burgerHi.userKiosk.model.dto.CardDTO;
import first.team.burgerHi.userKiosk.model.dto.CategoryDTO;
import first.team.burgerHi.userKiosk.model.dto.GifticonDTO;
import first.team.burgerHi.userKiosk.model.dto.MenuDTO;
import first.team.burgerHi.userKiosk.model.dto.OrderMenuAndMenuDTO;
import first.team.burgerHi.userKiosk.model.dto.SplitPaymentDTO;
import first.team.burgerHi.userKiosk.model.dto.UserDTO;
import first.team.burgerHi.userKiosk.model.service.ClientService;

public class ClientController {
	
	private ClientService clientService = new ClientService();
	
	/* 로그인 */
	public UserDTO memberLogin(String id, String pwd) {
		Map<String, String> LoginInfo = new HashMap<String, String>();
		LoginInfo.put("id", id);
		LoginInfo.put("pwd", pwd);
		UserDTO user = clientService.memberLogin(LoginInfo);
		return user;
	}

	/* 아이디 중복 체크 */
	public int checkMemberId(String memberId) {
		int result = clientService.checkMemberId(memberId);
		return result;
	}

	/* 회원가입 */
	public int registMember(String name, String memberId, String memberPwd, String formatMemberPhone) {
		UserDTO userDTO = new UserDTO();
		userDTO.setName(name);
		userDTO.setId(memberId);
		userDTO.setPwd(memberPwd);
		userDTO.setPhone(formatMemberPhone);
		int result = clientService.registMember(userDTO);
		return result;
	}

	/* 회원 정보 확인 */
	public UserDTO selectMemberInfo(int userNo) {
		UserDTO user = clientService.selectMemberInfo(userNo);
		return user;
	}
	
	/* 회원 정보 수정 */
	public int updateUserInfo(int userNo, String pwd, String formatUserPhone) {
		UserDTO user = new UserDTO();
		user.setUserNo(userNo);
		user.setPwd(pwd);
		user.setPhone(formatUserPhone);
		int result = clientService.updateUserInfo(user);
		return result;
	}

	/* 회원 탈퇴(정보 수정) */
	public int deleteMemberInfo(UserDTO user) {
		int result = clientService.deleteMemberInfo(user);
		return result;
	}

	/* 전체 카테고리 조회 */
	public List<CategoryDTO> selectAllCategory() {
		List<CategoryDTO> categoryList = clientService.selectAllCategory();
		return categoryList;
	}

	/* 사용자가 선택한 카테고리의 전체 메뉴 조회 */
	public List<MenuDTO> memberOrderMenuBy(int categoryNo) {
		List<MenuDTO> menuList = clientService.memberOrderMenuBy(categoryNo);
		return menuList;
	}
	
	/* 사용자가 선택한 메뉴 장바구니에 담기 */
	public int registOrderMenu(int userNo, int inputSelectMenu, int amount, String setMenuYn) {
		Map<String, String> registMenu = new HashMap<>();
		registMenu.put("userNo", String.valueOf(userNo));
		registMenu.put("menuNo", String.valueOf(inputSelectMenu));
		registMenu.put("amount", String.valueOf(amount));
		registMenu.put("setMenuYn", setMenuYn);
		int result = clientService.registOrderMenu(registMenu);
		return result;
	}

	/* 장바구니 확인 */
	public List<OrderMenuAndMenuDTO> selectOrderMenu(int userNo) {
		List<OrderMenuAndMenuDTO> orderMenuList = clientService.selectOrderMenu(userNo);
		return orderMenuList;
	}
	
	/* 장바구니 수정 */
	public int modifyOrderMenu(int[] deleteOrderMenuNo) {
		int result = clientService.modifyOrderMenu(deleteOrderMenuNo);
		return result;
	}

	/* 세트메뉴 장바구니에 담기 */
	public int registOrderSetMenu(int userNo, int[] basicSet, int inputAmount, String setMenuYn) {
		int result = clientService.registOrderSetMenu(userNo, basicSet, inputAmount, setMenuYn);
		return result;
	}
	
	/* 회원 등급별 할인 조회 */
	public int selectGradeByGradeNo(int gradeNo) {
		int gradeDiscount = clientService.selectGradeByGradeNo(gradeNo);
		return gradeDiscount;
	}

	/* 카드 목록 조회 */
	public List<CardDTO> selectAllCard() {
		List<CardDTO> cardList = clientService.selectAllCard();
		return cardList;
	}

	/* 멤버십 포인트 적립 */
	public int memberGradePoint(int userNo, int newPoint, int gradeNo) {
		int totalPoint = clientService.memberGradePoint(userNo, newPoint, gradeNo);
		return totalPoint;
	}

	/* 결제 완료 후 실행되야 할 로직처리 */
	public int registPaymentTotal(SplitPaymentDTO splitpay) {
		int result = clientService.registPaymentTotal(splitpay);
		return result;
	}

	/* 등급 업데이트 */
	public void modifyUserGrade(int userNo, int membershipgrade) {
		clientService.modifyUserGrade(userNo, membershipgrade);
		
	}

	/* 이벤트 기프티콘 생성 및 조회 */
	public GifticonDTO selectlastgifticon(int eventPrice) {
		GifticonDTO gifticon = clientService.selectlastgifticon(eventPrice);
		return gifticon;
	}

	/* 사용 할 기프티콘 금액 조회 */
	public int selectGifticonBy(String gifticonNo) {
		int gifticonPrice = clientService.selectGifticonBy(gifticonNo);
		return gifticonPrice;
	}

	/* 사용한 기프티콘 금액 수정 */
	public void modifyGifticonPrice(String gifticonNo, int gifticonPrice) {
		GifticonDTO gifticon = new GifticonDTO();
		gifticon.setNo(gifticonNo);
		gifticon.setPrice(gifticonPrice);
		clientService.modifyGifticonPrice(gifticon);
	}

	/* 비회원 회원번호&등급 등록 */
	public UserDTO registNonMember() {
		UserDTO nonMember = clientService.registNonMember();
		return nonMember;
	}

	/* 비회원의 경우 주문 취소여도 장바구니 삭제되도록 */
	public void deleteOrderMenu(int userNo) {
		clientService.deleteOrderMenu(userNo);
	}




}
