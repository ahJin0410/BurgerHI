package first.team.burgerHi.userKiosk.views;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import first.team.burgerHi.userKiosk.controller.ClientController;
import first.team.burgerHi.userKiosk.model.dto.CardDTO;
import first.team.burgerHi.userKiosk.model.dto.CategoryDTO;
import first.team.burgerHi.userKiosk.model.dto.GifticonDTO;
import first.team.burgerHi.userKiosk.model.dto.MenuDTO;
import first.team.burgerHi.userKiosk.model.dto.OrderMenuAndMenuDTO;
import first.team.burgerHi.userKiosk.model.dto.SplitPaymentDTO;
import first.team.burgerHi.userKiosk.model.dto.UserDTO;

public class MembersMenu {
	private ClientController clientController = new ClientController();
	private int userNo;
	private String setMenuYn;
	private int inputSelectMenu;
	private int totalPrice;
	private int discountSetMenu;
	private int gradeNo;
	private int gradeDiscount;
	private String splitPayYn = "N";
	private String paymentBy;
	private SplitPaymentDTO splitpay = new SplitPaymentDTO();
	private String message = null;
	private int result = 0;
	private String price = null;
	
	Scanner sc = new Scanner(System.in);
	DecimalFormat format = new DecimalFormat("###,###");
	
	public void displayMemberMenu(UserDTO user) {
		while(true) {
			System.out.println(">>>>           BurgerHI 메뉴 선택           <<<<");
			System.out.println("=================================================");
			System.out.println("                       |                       ");
			System.out.println("          1            |           2           ");
			System.out.println("     메뉴 주문하기     |     회원 정보 확인    ");
			System.out.println("                       |                       ");
			System.out.println("=================================================");
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
			
			message = "\n → 번호를 선택해 주세요: ";
			inputSelectMenu = inputMismatchCheck(message);
			System.out.println("\n\n\n\n\n\n\n\n\n\n");
			
			switch(inputSelectMenu) {
				case 1: memberOrderMenu(user); break;
				case 2: checkMemberInfo(user); break;
				case 0: break;
				default: System.out.println("  → 번호를 잘못 입력하셨습니다! \n\n\n"); continue;
			}
			break;
		}
	}
	
	/* 회원 메뉴 주문 */
	public void memberOrderMenu(UserDTO user) {
		boolean flag = true;
		
		/* 카테고리&메뉴 선택 후 장바구니 INSERT */
		while(true) {
			/* 전체 Category 출력 */
			System.out.println(">>>>         BurgerHI 카테고리 선택          <<<<");
			System.out.println("=================================================");
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
			System.out.println();
			List<CategoryDTO> categoryList = clientController.selectAllCategory();
			for(CategoryDTO cate : categoryList) {
				System.out.println("▶ " + cate.getCode() + ". " + cate.getName());
			}
			System.out.println();
			
			message = "\n → 원하시는 카테고리의 번호를 입력해 주세요: ";
			int categoryNo = inputMismatchCheck(message);
			System.out.println("\n\n\n\n\n\n\n\n\n\n");
			
			if(categoryNo == 0) {
				break;
			} else if(categoryNo == 1) {
				message = "\n → 세트메뉴로 주문 하시겠습니까?(1.예 / 2. 아니오): ";
				inputSelectMenu = inputMismatchCheck(message);
				System.out.println("\n\n\n\n\n\n\n\n\n\n");
				
				switch(inputSelectMenu) {
					case 1: result = memberOrderSetMenu(user, categoryNo); break;
					case 2: result = memberOrderMenuBy(user, categoryNo); break;
					case 0: flag = false; break;
					default: System.out.println("  → 번호를 잘못 입력하셨습니다! \n\n\n"); continue;
				}
			} else {
				result = memberOrderMenuBy(user, categoryNo);
			}
			
			if(result == 1) {
				continue;
			} else if(result == 2) {
				result = checkOrderMenu(user.getUserNo());
				if(result > 1) {
					break;
				} else {
					continue;
				}
			} else if(result == 0) {
				flag = false;
				break;
			}
		}
		
		/* 최종 선택 메뉴 결제 */
		userNo = user.getUserNo();
		gradeDiscount = clientController.selectGradeByGradeNo(user.getGradeNo());
		gradeNo = user.getGradeNo();
		while(flag) {
			System.out.println(">>>>         BurgerHI 장바구니 결제          <<<<");
			System.out.println("=================================================");
			System.out.println("               |               |               ");
			System.out.println("       1 　  　|        2　　  |       3      ");
			System.out.println("     카 드     |      현 금    |    기프티콘  ");
			System.out.println("               |               |              ");
			System.out.println("=================================================");		
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
			
			inputSelectMenu = inputMismatchCheck(message);
			
			switch(inputSelectMenu) {
				case 1: paymentBy = "카드"; price = paymentCard(totalPrice, discountSetMenu); break;
				case 2: paymentBy = "현금"; price = paymentCash(totalPrice, discountSetMenu); break;
				case 3: paymentBy = "기프티콘"; price = paymentGifticon(totalPrice, discountSetMenu); break;
				case 0: flag = false; break;
				default: continue;
			}
			
			if(price.equals("0")) {
				closeDisplayMainMenu();
				break;
			} else if(price.equals("종료")) {
				break;
			} else {
				continue;
			}
		}
	}

	/* 회원 세트메뉴 주문 */
	public int memberOrderSetMenu(UserDTO user, int categoryNo) {
		setMenuYn = "Y";
		System.out.println(">>>>           BurgerHI 세트 선택            <<<<");
		System.out.println("=================================================");
		System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
		List<MenuDTO> menuList = clientController.memberOrderMenuBy(categoryNo);	// 햄버거 종류 전체 출력 메소드
		for (MenuDTO menu : menuList) {
			System.out.println("▶ " + menu.getMenuCode() + ". " + menu.getName() + "세트  "
					+ (menu.getPrice()+3000)+ "원  " + " (세트할인 1000)\n" +  " 세트구성은 " + menu.getName() + ", 코카콜라, 감자튀김입니다.");
		}
		
		message = "\n → 원하시는 세트메뉴의 번호를 입력해 주세요: ";
		int inputSetNo = inputMismatchCheck(message);
		if(inputSetNo == 0) {
			result = 0;
		}
		message = "\n → 선택한 세트메뉴의 수량을 입력해 주세요: ";
		int inputAmount = inputMismatchCheck(message);
		message = "\n → 현재 구성으로 주문하시겠습니까?(1.예 / 2.아니오): ";
		int inputSetOrder = inputMismatchCheck(message);
		
		if(inputSetOrder == 1) {
			if(inputSetNo > 0 && inputAmount > 0) {
				int[] basicSet = new int[3];
				basicSet[0] = inputSetNo;
				basicSet[1] = 4;
				basicSet[2] = 6;
				result = clientController.registOrderSetMenu(user.getUserNo(), basicSet, inputAmount, setMenuYn);
				if(result > 0) {
					System.out.println("\n\n\n\n\n\n\n\n\n\n");
					System.out.println("\n  ※ 선택하신 메뉴가 장바구니에 담겼습니다.\n\n\n ");
					result = memberAddOrderMenu(user);
				} else {
					System.out.println("\n\n\n\n\n\n\n\n\n\n");
					System.out.println("\n  ※ 죄송합니다. KIOSK 오류로 인해 장바구니에 담지 못했습니다.\n\n\n ");
				}
			}
		} else if(inputSetOrder == 2) {
			List<MenuDTO> drinkList = clientController.memberOrderMenuBy(2);
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println(">>>>           BurgerHI 음료 변경            <<<<");
			System.out.println("=================================================");
			for (MenuDTO menu : drinkList) {
				System.out.println("▶ " + menu.getMenuCode() + ". " + menu.getName() + "  "
						+ menu.getPrice() + "원\n     " + menu.getExplain());
			}
			
			message = "\n → 변경 할 음료를 골라주세요: ";
			int inputDrinkNo = inputMismatchCheck(message);
			
			List<MenuDTO> sideList = clientController.memberOrderMenuBy(3);
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println(">>>>           BurgerHI 사이드 변경            <<<<");
			System.out.println("=================================================");
			for (MenuDTO menu : sideList) {
				System.out.println("▶ " + menu.getMenuCode() + ". " + menu.getName() + "  "
						+ menu.getPrice() + "원\n     " + menu.getExplain());
			}
			
			message = "\n → 변경 할 사이드를 골라주세요: ";
			int inputSideNo = inputMismatchCheck(message);
			
			int[] basicSet = new int[3];
			basicSet[0] = inputSetNo;
			basicSet[1] = inputDrinkNo;
			basicSet[2] = inputSideNo;
			result = clientController.registOrderSetMenu(user.getUserNo(), basicSet, inputAmount, setMenuYn);
			if(result > 0) {
				System.out.println("\n\n\n\n\n\n\n\n\n\n");
				System.out.println("\n  ※ 선택하신 메뉴가 장바구니에 담겼습니다.\n\n\n ");
				result = memberAddOrderMenu(user);
			} else {
				System.out.println("\n\n\n\n\n\n\n\n\n\n");
				System.out.println("\n  ※ 죄송합니다. KIOSK 오류로 인해 장바구니에 담지 못했습니다.\n\n\n ");
			}
		} else if(inputSetOrder == 0) {
			result = 0;
		}
		return result;
	}

	/* 회원 단품메뉴 주문 */
	public int memberOrderMenuBy(UserDTO user, int categoryNo) {
		setMenuYn = "N";
		
		System.out.println(">>>>           BurgerHI 메뉴 선택            <<<<");
		System.out.println("=================================================");
		System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
		System.out.println();
		List<MenuDTO> menuList = clientController.memberOrderMenuBy(categoryNo);	// Menu 출력 메소드
		for (MenuDTO menu : menuList) {
			System.out.println("▶ " + menu.getMenuCode() + ". " + menu.getName() + "  "
					+ format.format(menu.getPrice()) + "원\n     " + menu.getExplain());
		}
		
		message = "\n → 원하시는 메뉴의 번호를 입력해 주세요: ";
		int inputMenu = inputMismatchCheck(message);
		
		
		int amount = 0;
		if(inputSelectMenu > 0) {
			message = "\n → 원하시는 메뉴의 수량을 입력해 주세요: ";
			amount = inputMismatchCheck(message);
		}
		if(inputSelectMenu > 0 && amount > 0) {
			result = clientController.registOrderMenu(user.getUserNo(), inputMenu, amount, setMenuYn);
			
			if(result > 0) {
				System.out.println("\n\n\n\n\n\n\n\n\n\n");
				System.out.println("\n  ※ 선택하신 메뉴가 장바구니에 담겼습니다.\n\n\n ");
				result = memberAddOrderMenu(user);
			} else {
				System.out.println("\n\n\n\n\n\n\n\n\n\n");
				System.out.println("\n  ※ 죄송합니다. KIOSK 오류로 인해 장바구니에 담지 못했습니다.\n\n\n ");
			}
		}
		return result;
	}
	
	/* 추가 주문 확인 */
	public int memberAddOrderMenu(UserDTO user) {
		while(true) {
			System.out.println(">>>>           BurgerHI 메뉴 선택            <<<<");
			System.out.println("=================================================");
			System.out.println("                       |                       ");
			System.out.println("           1           |           2           ");
			System.out.println("      추가 주문하기    |     장바구니 보기     ");
			System.out.println("                       |                       ");
			System.out.println("=================================================");
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");	
			
			message = "\n → 번호를 선택해 주세요: ";
			inputSelectMenu = inputMismatchCheck(message);
			System.out.println("\n\n\n\n\n\n\n\n\n\n");
			
			switch(inputSelectMenu) {
				case 1: inputSelectMenu = 1; break;
				case 2: inputSelectMenu = 2; break;
				case 0: inputSelectMenu = 0; break;
				default: System.out.println("  → 번호를 잘못 입력하셨습니다! \n\n\n"); continue;
			}
			return inputSelectMenu;
		}
	}
	
	/* 장바구니 확인 */
	public int checkOrderMenu(int userNo) {
		int setMenuAmount = 0;
		int setPrice = 0;
		while(true) {
			List<OrderMenuAndMenuDTO> orderMenuList = clientController.selectOrderMenu(userNo);				//장바구니에 Insert했던 내용 출력
			if(orderMenuList.size() > 0) {
				System.out.println(">>>>         BurgerHI 장바구니 확인          <<<<");
				System.out.println("=================================================");
				System.out.println();
				for(int i = 0; i < orderMenuList.size(); i++) {
					if(orderMenuList.get(i).getOrderMenuSetYn().equals("N")) {
						System.out.println("▶ 메뉴번호: " + orderMenuList.get(i).getOrderMenuNo());			
						System.out.println("▶ 메뉴명  : " + orderMenuList.get(i).getName());
						System.out.println("▶ 주문수량: " + orderMenuList.get(i).getOrderAmount());
						System.out.println("▶ 금액    : " + format.format(orderMenuList.get(i).getPrice()) + " * " + orderMenuList.get(i).getOrderAmount() + " = " + 
								format.format((orderMenuList.get(i).getPrice() *  orderMenuList.get(i).getOrderAmount())));
						System.out.println();
						totalPrice += (orderMenuList.get(i).getPrice() *  orderMenuList.get(i).getOrderAmount());
					} else {
						System.out.println("▶ 메뉴번호: " + orderMenuList.get(i).getOrderMenuNo());
						System.out.println("▶ 메뉴명  : " + orderMenuList.get(i).getName() + "세트");
						System.out.println("▶ 세트음료  : " + orderMenuList.get(i + 1).getName());
						System.out.println("▶ 세트사이드  : " + orderMenuList.get(i + 2).getName());
						System.out.println("▶ 주문수량: " + orderMenuList.get(i).getOrderAmount());
						setPrice = orderMenuList.get(i).getPrice() + orderMenuList.get(i+1).getPrice() + orderMenuList.get(i+2).getPrice();
						System.out.println("▶ 금액    : " + format.format(setPrice) + " * " + orderMenuList.get(i).getOrderAmount() + " = " + format.format(setPrice * orderMenuList.get(i).getOrderAmount()));
						System.out.println();
						setMenuAmount += orderMenuList.get(i).getOrderAmount();
						totalPrice += setPrice * orderMenuList.get(i).getOrderAmount();
						i+=2;
					}
				}
				
				if(setMenuAmount > 0) {
					discountSetMenu = setMenuAmount * 1000;
					int discountSetMenu = setMenuAmount * 1000;
					System.out.println("\n\n▶ 세트 할인 금액: " + format.format(discountSetMenu));
					System.out.println("▶ 총 금액: " + format.format(totalPrice) + " - " + format.format(discountSetMenu) + " = "  +format.format((totalPrice - discountSetMenu)));
					System.out.println("\n\n\n\n\n\n\n\n\n");
				}
				
				System.out.println(">>>>           BurgerHI 메뉴 선택            <<<<");
				System.out.println("=================================================");
				System.out.println("               |               |               ");
				System.out.println("      1 　   　|       2　　   |       3       ");
				System.out.println(" 추가 주문하기 | 장바구니 수정 |   결제 하기   ");
				System.out.println("               |               |               ");
				System.out.println("=================================================");
				System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
				
				message = "\n → 번호를 선택해 주세요: ";
				inputSelectMenu = inputMismatchCheck(message);
				System.out.println("\n\n\n\n\n\n\n\n\n");
				
				switch(inputSelectMenu) {
				case 0: break;
				case 1: break;
				case 2: ModifyOrderMenu(userNo); continue;
				case 3: break;
				default: System.out.println("  → 번호를 잘못 입력하셨습니다! \n\n\n"); continue;
				}
				break;
				
			} else {
				System.out.println(">>>>         BurgerHI 장바구니 확인          <<<<");
				System.out.println("=================================================");
				System.out.println("\n                    🍔 텅 🍔                   ");
				System.out.println("\n              장바구니가 텅비었어요.           \n");
				System.out.println("=================================================");
				System.out.println("\n → 주문하러 가볼까요?\n\n\n");
				inputSelectMenu = 1;
				break;
			}
		}
		return inputSelectMenu;
	}
	
	/* 장바구니 메뉴 수정 */
	public void ModifyOrderMenu(int userNo) {
		List<OrderMenuAndMenuDTO> orderMenuList = clientController.selectOrderMenu(userNo);				//장바구니에 Insert했던 내용 출력
		int setMenuAmount = 0;
		int setPrice = 0;
		int[] deleteOrderMenuNo = null;
		System.out.println(">>>>         BurgerHI 장바구니 수정          <<<<");
		System.out.println("=================================================");
		System.out.println();
		for(int i = 0; i < orderMenuList.size(); i++) {
			if(orderMenuList.get(i).getOrderMenuSetYn().equals("N")) {
				System.out.println("▶ 메뉴번호: " + orderMenuList.get(i).getOrderMenuNo());			
				System.out.println("▶ 메뉴명  : " + orderMenuList.get(i).getName());
				System.out.println("▶ 주문수량: " + orderMenuList.get(i).getOrderAmount());
				System.out.println("▶ 금액    : " + format.format(orderMenuList.get(i).getPrice()) + " * " + orderMenuList.get(i).getOrderAmount() + " = " + 
						format.format((orderMenuList.get(i).getPrice() *  orderMenuList.get(i).getOrderAmount())));
				System.out.println();
				totalPrice += (orderMenuList.get(i).getPrice() *  orderMenuList.get(i).getOrderAmount());
			} else {
				System.out.println("▶ 메뉴번호: " + orderMenuList.get(i).getOrderMenuNo());
				System.out.println("▶ 메뉴명  : " + orderMenuList.get(i).getName() + "세트");
				System.out.println("▶ 세트음료  : " + orderMenuList.get(i + 1).getName());
				System.out.println("▶ 세트사이드  : " + orderMenuList.get(i + 2).getName());
				System.out.println("▶ 주문수량: " + orderMenuList.get(i).getOrderAmount());
				setPrice = orderMenuList.get(i).getPrice() + orderMenuList.get(i+1).getPrice() + orderMenuList.get(i+2).getPrice();
				System.out.println("▶ 금액    : " + format.format(setPrice) + " * " + orderMenuList.get(i).getOrderAmount() + " = " + format.format(setPrice * orderMenuList.get(i).getOrderAmount()));
				setMenuAmount += orderMenuList.get(i).getOrderAmount();
				totalPrice += setPrice * orderMenuList.get(i).getOrderAmount();
				i+=2;
			}
		}
		
		if(setMenuAmount > 0) {
			discountSetMenu = setMenuAmount * 1000;
			System.out.println("\n\n▶ 세트 할인 금액: " + format.format(discountSetMenu));
			System.out.println("▶ 총 금액: " + format.format(totalPrice) + " - " + format.format(discountSetMenu) + " = "  +format.format((totalPrice - discountSetMenu)));
			System.out.println("\n\n\n\n\n\n\n\n\n");
		}
		
		message = "\n → 삭제하실 메뉴 번호를 입력해 주세요: ";
		inputSelectMenu = inputMismatchCheck(message);
		
		for(int i = 0; i < orderMenuList.size(); i++) {
			if(orderMenuList.get(i).getOrderMenuNo() == inputSelectMenu) {
				if(orderMenuList.get(i).getOrderMenuSetYn().equals("Y")) {
					deleteOrderMenuNo = new int[3];
					deleteOrderMenuNo[0] = orderMenuList.get(i).getOrderMenuNo();
					deleteOrderMenuNo[1] = orderMenuList.get(i + 1).getOrderMenuNo();
					deleteOrderMenuNo[2] = orderMenuList.get(i + 2).getOrderMenuNo();
					break;
				} else {
					deleteOrderMenuNo = new int[1];
					deleteOrderMenuNo[0] = orderMenuList.get(i).getOrderMenuNo();
					break;
				}
			}
		}
		
		result = clientController.modifyOrderMenu(deleteOrderMenuNo);
		if(result > 0) {
			System.out.println("\n\n ※ 선택하신 메뉴가 정상적으로 수정되었습니다.\n\n\n");
		} else {
			System.out.println("\n\n ※ 장바구니 메뉴 수정이 실패했습니다. 다시 시도해주세요.\n\n\n");
		}
	}
	
	/* 회원 정보 확인 */
	public void checkMemberInfo(UserDTO user) {
		user = clientController.selectMemberInfo(user.getUserNo());
		while(true) {
			System.out.println(">>>>         BurgerHI 회원 정보 조회         <<<<");
			System.out.println("=================================================");
			System.out.println();
			System.out.println("▶ 회원번호: " + user.getUserNo());
			System.out.println("▶ 회원이름: " + user.getName());
			System.out.println("▶ 회원ID: " + user.getId());
			System.out.println("▶ 등급: " + user.getGradeNo());
			System.out.println("▶ 보유포인트: " + format.format(user.getUserPoint()));
			System.out.println("▶ 전화번호: " + user.getPhone());
			System.out.println();
			
			System.out.println(" ▷ 1번 : 회원 정보 수정");
			System.out.println(" ▷ 2번 : 회원 탈퇴");
			System.out.println(" ▷ 3번 : 이전 화면 돌아가기");
			
			message = "\n → 번호를 선택해 주세요: ";
			inputSelectMenu = inputMismatchCheck(message);
			
			switch(inputSelectMenu) {
			case 1: modifyMemberInfo(user); break;
			case 2: deleteMemberInfo(user); continue;
			case 3: break;
			default: System.out.println("  → 번호를 잘못 입력하셨습니다! \n\n\n"); continue;
			}
			displayMemberMenu(user);
		}
	}
	
	/* 회원 정보 수정 */
	public void modifyMemberInfo(UserDTO user) {
		System.out.println(">>>>         BurgerHI 회원 정보 수정         <<<<");
		System.out.println("=================================================");
		System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
		System.out.print("\n → 수정하실 회원 pwd를 입력해 주세요: ");
		sc.nextLine();
		String pwd = sc.nextLine();
		System.out.print(" → 수정하실 전화번호를 입력해 주세요: ");
		String phone = sc.nextLine();
		String formatUserPhone = new KioskMain().phoneFormat(phone);
		
		int result = clientController.updateUserInfo(user.getUserNo(), pwd, formatUserPhone);
		if(result > 0) {
			System.out.println("\n\n ※ 회원정보 수정이 정상적으로 처리되었습니다.\n\n\n");
		} else {
			System.out.println("\n\n ※ 회원정보 수정이 실패했습니다. 다시 시도해주세요.\n\n\n");
		}
	}
	
	/* 회원 탈퇴(delete(X) Modify(O)) */
	public void deleteMemberInfo(UserDTO user) {
		System.out.println(">>>>           BurgerHI 회원 탈퇴            <<<<");
		System.out.println("=================================================");
		System.out.println();
		System.out.println("      ★ 고객님의 모든 정보가 사라집니다. ★    ");
		System.out.println("       ★ 모든 정보는 복구되지 않습니다. ★      ");
		System.out.println("           그래도 진행 하시겠습니까?            \n");
		System.out.println("=================================================");
		System.out.println("                       |                       ");
		System.out.println("          1            |           2           ");
		System.out.println("      회원 탈퇴        |         취소          ");
		System.out.println("                       |                       ");
		System.out.println("=================================================");
		
		message = "\n → 번호를 선택해 주세요: ";
		inputSelectMenu = inputMismatchCheck(message);
		
		if(inputSelectMenu == 1) {
			int result = clientController.deleteMemberInfo(user);
			if(result > 0) {
				System.out.println("\n\n ※ BurgerHi의 멤버 탈퇴가 정상적으로 처리되었습니다.");
				System.out.println("\n ※ 그동안 이용해주셔서 감사합니다.\n\n\n");
				new KioskMain().displayMainMenu();
			} else {
				System.out.println("\n\n ※ 시스템 오류로 멤버 탈퇴가 실패했습니다. 다시 시도해주세요.\n\n\n");
			}
		}
	}
	
	/* 카드 결제 */
	public String paymentCard(int totalPrice, int discountSetMenu) {
		double cardDiscount = 0.0;
		int checkCard = 0;
		String paymentCard = "";
		String result = "";
		List<CardDTO> cardList = clientController.selectAllCard();
		splitpay.setGradeNo(gradeNo);
		splitpay.setUserNo(userNo);
		splitpay.setSplitPaymentYn(splitPayYn);
		splitpay.setPaymentBy(paymentBy);
		splitpay.setTotalPrice(totalPrice);
		while(true) {
			if(splitPayYn.equals("N")) {
				System.out.println("\n\n\n\n\n\n\n\n\n\n");
				System.out.println("★★★★      카드 중복 할인 Event!      ★★★★");
				System.out.println("=================================================");
				System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
				for(int i = 0; i < cardList.size(); i++) {
					CardDTO card = cardList.get(i);
					System.out.println("▶ " + card.getBank() + (Integer.valueOf(card.getDiscount()) > 0 ? "의 할인율 : " + card.getDiscount() + "%" : ""
							+ " : 할인 혜택 적용 불가능"));
				}
				System.out.println("=================================================\n\n");
				
				System.out.print("\n →결제하실 카드명을 입력해 주세요: ");
				paymentCard = sc.nextLine();
				
				if(paymentCard.equals("0")) {
					result = "종료";
					break;
				}
				
				if(paymentCard.length() == 2) {
					paymentCard = paymentCard + "카드";
				} else if(!paymentCard.endsWith("카드")) {
					paymentCard = paymentCard.substring(0, 2) + "카드";
				}
				
				for(int i = 0; i < cardList.size(); i++) {
					CardDTO card = cardList.get(i);
					if(card.getBank().equals(paymentCard) && card.getCardable().equals("Y")) {	// cardable 여부로 할인 적용 판정
						splitpay.setCardCode(card.getCode());
						cardDiscount = 0.1;
						checkCard = 1;
						break;
					} else if(card.getBank().equals(paymentCard) && card.getCardable().equals("N")) {
						splitpay.setCardCode(card.getCode());
						checkCard = 1;
						break;
					}
				}
				
				gradeDiscount = totalPrice * (gradeDiscount / 100);
				cardDiscount = cardDiscount * totalPrice;
				int paymentPrice = totalPrice - discountSetMenu - (int)cardDiscount;
				/* 할인 내역 및 결제 금액 모두 출력 */
				System.out.println("\n\n▶ 장바구니 총 금액: " + format.format(totalPrice) + "원");
				System.out.println("▶ 등급 할인 금액: " + format.format(gradeDiscount) + "원");
				System.out.println("▶ 카드사 할인 금액: " + format.format((int)cardDiscount) + "원");
				System.out.println("▶ 세트 할인 금액: " + format.format(discountSetMenu) + "원");
				System.out.println("\n▶ 총 결제 금액은 " + format.format(paymentPrice) + "원 입니다.\n");
				System.out.println("고객님의 " + paymentCard + "로 총" + format.format(paymentPrice) + "원이 결제 되었습니다!");
				System.out.println("주문이 진행되고 있으니 잠시만 기다려 주세요 :)\n\n");
				splitpay.setPaymentPrice(paymentPrice);
			} else {
				splitPayYn = "N";
				splitpay.setSplitPaymentYn(splitPayYn);
				System.out.println("=================================================");
				System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
				for(int i = 0; i < cardList.size(); i++) {
					CardDTO card = cardList.get(i);
					System.out.println("▶ " + card.getBank());
				}
				System.out.println("=================================================\n\n");
				System.out.print("\n →결제하실 카드명을 입력해 주세요: ");
				paymentCard = sc.nextLine();
				
				if(paymentCard.equals("0")) {
					result = "종료";
					break;
				}
				
				if(paymentCard.length() == 2) {
					paymentCard = paymentCard + "카드";
				} else if(!paymentCard.endsWith("카드")) {
					paymentCard = paymentCard.substring(0, 2) + "카드";
				}
				
				for(int i = 0; i < cardList.size(); i++) {
					CardDTO card = cardList.get(i);
					if(card.getBank().equals(paymentCard)) {
						splitpay.setCardCode(card.getCode());
						checkCard = 1;
						break;
					} 
				}
				
				int paymentPrice = Integer.valueOf(price);
				/* 할인 내역 및 결제 금액 모두 출력 */
				System.out.println("\n▶ 추가 결제 금액 " + format.format(paymentPrice) + "원 입니다.\n");
				System.out.println("고객님의 " + paymentCard + "로 총" + format.format(paymentPrice) + "원이 결제 되었습니다!");
				System.out.println("주문이 진행되고 있으니 잠시만 기다려 주세요 :)\n\n");
				splitpay.setPaymentPrice(paymentPrice);
			}
			
			if(checkCard == 0) {
				System.out.println("\n\n\n 저희 매장과 제휴되어 있지 않은 카드 입니다.");
				System.out.println("다시 결제를 시도해 주세요!\n\n\n");
				continue;
			}
			
			
			if(gradeNo != 4) {
				memberGradePoint(userNo, splitpay.getPaymentPrice(), gradeNo); // 멤버쉽 관련 메소드(누적 포인트 및 등급)
			}
			int totalResult = clientController.registPaymentTotal(splitpay);
			if(totalResult > 0) {
				result = "0";
			} else {
				System.out.println("\n\n ※ 결제 오류로 다시 돌아갑니다.");
				result = "1";
			}
			break;
		}
		return result;
	}
	
	/* 현금 결제 */
	public String paymentCash(int totalPrice, int discountSetMenu) {
		String result = "";
		int inputPay = 0;
		splitpay.setGradeNo(gradeNo);
		splitpay.setUserNo(userNo);
		splitpay.setSplitPaymentYn(splitPayYn);
		splitpay.setPaymentBy(paymentBy);
		splitpay.setCardCode(0);
		int paymentPrice = totalPrice - discountSetMenu;
		if(splitPayYn.equals("N")) {
			splitpay.setPaymentPrice(paymentPrice);
			gradeDiscount = totalPrice * (gradeDiscount / 100);
			paymentPrice -= gradeDiscount;
			System.out.println("\n\n▶ 장바구니 총 금액: " + format.format(totalPrice) + "원");
			System.out.println("▶ 등급 할인 금액: " + format.format(gradeDiscount) + "원");
			System.out.println("▶ 세트 할인 금액: " + format.format(discountSetMenu) + "원");
			System.out.println("\n▶ 총 결제 금액은 " + format.format(paymentPrice) + "원 입니다.");
			message = "\n\n\n → 결제하실 금액을 입력해 주세요: ";
			inputPay = inputMismatchCheck(message);
			if(inputPay == paymentPrice) {
				System.out.println("\n결제가 완료 되었습니다!");
				System.out.println(" 주문이 진행되고 있으니 잠시만 기다려 주세요 :)\n\n");
				
				if(gradeNo != 4) {
					memberGradePoint(userNo, paymentPrice, gradeNo); // 멤버쉽 관련 메소드(누적 포인트 및 등급)
				}
				splitpay.setPaymentPrice(paymentPrice);
			} else if(inputPay > paymentPrice) {
				System.out.println("\n 거스름돈은 " + format.format((inputPay - paymentPrice)) + "원 입니다!");
				System.out.println(" 주문이 진행되고 있으니 잠시만 기다려 주세요 :)\n\n");
				
				if(gradeNo != 4) {
					memberGradePoint(userNo, paymentPrice, gradeNo); // 멤버쉽 관련 메소드(누적 포인트 및 등급)
				}
				splitpay.setPaymentPrice(paymentPrice);
			} else {
				splitPayYn = "Y";
				System.out.println(" 결제 금액이 " + format.format((paymentPrice - inputPay)) + "원 부족합니다!");
				System.out.println("\n 추가 금액 " + format.format((paymentPrice - inputPay)) + "원을 결제해 주세요!\n\n\n");
				splitpay.setPaymentPrice(inputPay);
				result = paymentPrice - inputPay + "";
			}
		} else {
			paymentPrice = Integer.valueOf(price);
			splitpay.setPaymentPrice(paymentPrice);
			splitPayYn = "N";
			splitpay.setSplitPaymentYn(splitPayYn);
			System.out.println("\n▶ 추가 결제 금액 " + format.format(paymentPrice) + "원 입니다.\n");
			System.out.println(" 주문이 진행되고 있으니 잠시만 기다려 주세요 :)\n\n");
		}
		int totalResult = clientController.registPaymentTotal(splitpay);
		
		if(splitPayYn.equals("Y")) {
			result = paymentPrice - inputPay + "";
		} else if(splitPayYn.equals("N") && totalResult > 0) {
			result = "0";
		} else {
			System.out.println("\n\n ※ 결제 오류로 다시 돌아갑니다.");
			result = "1";
		}
		return result;
	}
	
	/* 기프티콘 결제 */
	public String paymentGifticon(int totalPrice, int discountSetMenu2) {
		int paymentPrice = totalPrice - discountSetMenu;
		String result = "";
		splitpay.setGradeNo(gradeNo);
		splitpay.setUserNo(userNo);
		splitpay.setSplitPaymentYn(splitPayYn);
		splitpay.setPaymentBy(paymentBy);
		splitpay.setCardCode(0);
		while(true) {
			gradeDiscount = totalPrice * (gradeDiscount / 100);
			paymentPrice -= gradeDiscount;
			System.out.println("\n\n▶ 장바구니 총 금액: " + format.format(totalPrice) + "원");
			System.out.println("▶ 등급 할인 금액: " + format.format(gradeDiscount) + "원");
			System.out.println("▶ 세트 할인 금액: " + format.format(discountSetMenu) + "원");
			System.out.println("\n▶ 총 결제 금액은 " + format.format(paymentPrice) + "원 입니다.");
			
			System.out.print("\n → 사용하실 기프티콘 번호를 입력해 주세요(숫자 14자리): ");
			String gifticonNo = sc.nextLine();
			if(gifticonNo != null) {
				gifticonNo = gifticonNo.replaceAll("[^0-9]", "");
				gifticonNo = gifticonNo.substring(0, 6) + "-" + gifticonNo.substring(6, 10) + "-" + gifticonNo.substring(10);
			}
			while(true) {
				if(gifticonNo.length() > 14) {
					break;
				} else {
					System.out.println("\n\n ※ 기프티콘 번호를 잘못 입력하셨습니다,");
					System.out.println("     이전 페이지로 돌아가기는 9번 입니다.");
					System.out.print("\n → 사용하실 기프티콘 번호를 입력해 주세요: ");
					gifticonNo = sc.nextLine();
					if(gifticonNo.equals("9")) {
						break;
					}
				}
			}
			
			if(gifticonNo.equals("9")) {
				System.out.println("\n\n\n\n\n\n\n\n\n\n");
				result = "종료";
				break;
			}
			
			int gifticonPrice = clientController.selectGifticonBy(gifticonNo);
			if(gifticonPrice == 0) {
				System.out.println("\n 이미 사용된 기프티콘 입니다!");
				System.out.println("\n\n\n\n\n\n\n\n\n\n");
				continue;
			}
			if(splitPayYn.equals("N")) {
				if(gifticonPrice >= paymentPrice) {
					gifticonPrice = gifticonPrice - paymentPrice;
					System.out.println("\n 결제가 완료 되었습니다! 기프티콘 잔액은 " + format.format(gifticonPrice) + "원 입니다!");
					System.out.println("\n 주문이 진행되고 있으니 잠시만 기다려 주세요 :)");
				} else {
					splitPayYn = "Y";
					splitpay.setSplitPaymentYn(splitPayYn);
					System.out.println("\n 기프티콘 사용이 완료 되었습니다!");
					System.out.println("\n 추가 금액 " + format.format((paymentPrice - gifticonPrice)) + "원을 결제해 주세요!\n\n\n");
					gifticonPrice = 0;
				}
			} else {
				paymentPrice = Integer.valueOf(price);
				if(gifticonPrice >= paymentPrice) {
					splitPayYn = "N";
					splitpay.setSplitPaymentYn(splitPayYn);
					gifticonPrice = gifticonPrice - paymentPrice;
					System.out.println("\n 결제가 완료 되었습니다! 기프티콘 잔액은 " + format.format(gifticonPrice) + "원 입니다!");
					System.out.println("\n 주문이 진행되고 있으니 잠시만 기다려 주세요 :)");
				} else {
					splitPayYn = "Y";
					splitpay.setSplitPaymentYn(splitPayYn);
					System.out.println("\n 기프티콘 사용이 완료 되었습니다!");
					System.out.println("\n 추가 금액 " + format.format((paymentPrice - gifticonPrice)) + "원을 결제해 주세요!\n\n\n");
					gifticonPrice = 0;
				}
				
			}
			clientController.modifyGifticonPrice(gifticonNo, gifticonPrice);
			int totalResult = clientController.registPaymentTotal(splitpay);
			if(splitPayYn.equals("Y")) {
				result = paymentPrice - gifticonPrice + "";
			} else if(splitPayYn.equals("N") && totalResult > 0) {
				result = "0";
			} else {
				System.out.println("\n\n ※ 결제 오류로 다시 돌아갑니다.");
				result = "1";
			}
			break;
		}
		return result;
	}
	
	/* 포인트 관련 */
	public void memberGradePoint(int userNo, int paymentPrice, int gradeNo) {
		int newPoint = paymentPrice / 100 * 2;	// 구매 금액의 2%를 적립
		int totalPoint = clientController.memberGradePoint(userNo, newPoint, gradeNo);
		System.out.println("\n\n ※ 현재 " + format.format(newPoint) + "Point 적립되셨습니다." );
		System.out.println(" ※ 고객님의 현재 누적된 멤버쉽은 " + format.format(totalPoint) + "Point 입니다.\n\n\n");
		
		int membershipgrade = 0;
		if(totalPoint >= 4000) {
			System.out.println("\n\n ※ 회원님의 현재 등급은 Gold 입니다.");
			membershipgrade = 3;		// 골드
			if(gradeNo != membershipgrade) {
				clientController.modifyUserGrade(userNo, membershipgrade);
				eventByuserUpgrade(membershipgrade);
			}
		} else if(totalPoint >= 2500) {
			membershipgrade = 2;		// 실버
			System.out.println("\n\n회원님의 현재 등급은 Silver 입니다.");
			System.out.println("다음 등급까지" + format.format((4000 - totalPoint)) + "Point 남았습니다. ^_^");
			if(gradeNo != membershipgrade) {
				clientController.modifyUserGrade(userNo, membershipgrade);
				eventByuserUpgrade(membershipgrade);
			}
		} else if(totalPoint > 0) {
			membershipgrade = 1;		// 패밀리
			System.out.println("\n\n ※ 회원님의 현재 등급은 Family 입니다.");
			System.out.println(" ※ 다음 등급까지 " + format.format((2500 - totalPoint)) + "Point 남았습니다.\n\n\n");
			if(totalPoint == newPoint && gradeNo != 5) {
				eventByuserUpgrade(membershipgrade);
			}
		}
		
		try {
			Thread.sleep(1800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("\n\n\n\n\n\n\n\n\n\n");
	}

	/* 기프티콘 증정 이벤트 */
	public void eventByuserUpgrade(int membershipgrade) {
		int eventPrice = 0;
		if(membershipgrade == 1) {
			eventPrice = 2000;
		} else if(membershipgrade == 2) {
			eventPrice = 5000;
		} else if(membershipgrade == 3) {
			eventPrice = 10000;
		}
		GifticonDTO gifticon = clientController.selectlastgifticon(eventPrice);
		
		System.out.println("★★★★    BurgerHi의 특별한 Event!    ★★★★");
		System.out.println("=================================================");
		System.out.println("\n      BurgerHi에서 준비한 특별한 Event!!!!    ");
		System.out.println("\n       BurgerHi 고객님께 드리는 선물🎁       ");
		System.out.println("\n → 기프티콘 번호:" + gifticon.getNo());
		System.out.println("\n → 기프티콘 금액:" + format.format(gifticon.getPrice()));
		System.out.println("\n 다음 주문부터 사용이 가능하며, 현금으로 교환은 어렵습니다.");
		System.out.println("  기프티콘 금액은 분할로 사용이 가능하며, 유효기간은 1년 입니다.");
		System.out.println("\n\n BurgerHi를 사랑해 주셔서 감사합니다. \n 좋은 하루 보내세요♥ ");
		
	}

	/* 결제 완료 */
	public void closeDisplayMainMenu() {
		System.out.println("\n\n\n주문이 진행되고 있으니 잠시만 기다려 주세요 :)\n\n\n");
		
		try {
			Thread.sleep(1500);
			for (int k = 0; k < 50; k++) {
				System.out.println();
			}
			System.out.println("주문접수 중");
			System.out.println("▷▷▷▷▷▷▷");
			Thread.sleep(700);
			for (int k = 0; k < 50; k++) {
				System.out.println();
			}
			System.out.println("주문접수 중.");
			System.out.println("▷▷▷▷▷▷▷");
			Thread.sleep(700);
			for (int k = 0; k < 50; k++) {
				System.out.println();
			}
			System.out.println("주문접수 중..");
			System.out.println("▶▷▷▷▷▷▷");
			Thread.sleep(700);
			for (int k = 0; k < 50; k++) {
				System.out.println();
			}
			System.out.println("메뉴가 준비중이에요!");
			System.out.println("▶▶▷▷▷▷▷");
			Thread.sleep(700);
			for (int k = 0; k < 50; k++) {
				System.out.println();
			}
			System.out.println("메뉴가 준비중이에요!");
			System.out.println("▶▶▶▷▷▷▷");
			Thread.sleep(700);
			for (int k = 0; k < 50; k++) {
				System.out.println();
			}
			System.out.println("메뉴가 준비중이에요!");
			System.out.println("▶▶▶▶▷▷▷");
			Thread.sleep(700);
			for (int k = 0; k < 50; k++) {
				System.out.println();
			}
			System.out.println("메뉴 포장중.");
			System.out.println("▶▶▶▶▶▷▷");
			Thread.sleep(700);
			for (int k = 0; k < 50; k++) {
				System.out.println();
			}
			System.out.println("메뉴 포장중..");
			System.out.println("▶▶▶▶▶▶▷");
			Thread.sleep(700);
			for (int k = 0; k < 50; k++) {
				System.out.println();
			}
			System.out.println("메뉴 포장중...");
			System.out.println("▶▶▶▶▶▶▶");
			Thread.sleep(700);
			for (int k = 0; k < 50; k++) {
				System.out.println();
			}
			System.out.println("메뉴가 준비됐어요! 🍔");
			System.out.println("▶▶▶▶▶▶▶");
			Thread.sleep(700);
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/* 문자열 예외처리 */
	public int inputMismatchCheck(String message) {
		inputSelectMenu = 0;
		while(true) {
			try {
				System.out.print(message);
				inputSelectMenu = sc.nextInt();
				sc.nextLine();
			} catch(InputMismatchException e) {
				System.out.println("\n 숫자로 입력해 주세요!");
				sc.next();
				continue;
			} break;
		}
		return inputSelectMenu;
	}
}
