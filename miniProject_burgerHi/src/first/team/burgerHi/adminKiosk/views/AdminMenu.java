package first.team.burgerHi.adminKiosk.views;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import first.team.burgerHi.adminKiosk.controller.AdminController;
import first.team.burgerHi.adminKiosk.model.dto.CategoryDTO;
import first.team.burgerHi.adminKiosk.model.dto.MenuDTO;
import first.team.burgerHi.userKiosk.model.dto.UserDTO;
import first.team.burgerHi.userKiosk.views.MembersMenu;

public class AdminMenu {
	private AdminController adminController = new AdminController();
	private int result = 0;
	private int inputSelectMenu = 0;
	private String message = "";
	Scanner sc = new Scanner(System.in);
	DecimalFormat format = new DecimalFormat("###,###");
	
	public void displayNonMemberMenu(UserDTO admin) {
		while(true) {
			System.out.println(">>>>       BurgerHI 관리자 페이지       <<<<");
			System.out.println("=================================================");
			System.out.println("               |                |               ");
			System.out.println("      1      　|        2       |       3       ");
			System.out.println("판매 순위 확인 |    메뉴 관리   |   매출 확인   ");
			System.out.println("               |                |               ");
			System.out.println("------------------------------------------------");
			System.out.println("                       |                       ");
			System.out.println("           4           |           5           ");
			System.out.println("     회원 정보 확인    |     메뉴 주문 하기    ");
			System.out.println("                       |                       ");
			System.out.println("=================================================");
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. \n");
			
			message = "\n  → 번호를 선택해 주세요: ";
			inputSelectMenu = inputMismatchCheck(message);
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			switch(inputSelectMenu) {
				case 0: result = 0; break;
				case 1: result = salesRanking(); break;
				case 2: result = menuManagement(); break;
				case 3: result = salesConfirmation(); break;
				case 4: result = memberInfoConfirmation(); break;
				case 5: new MembersMenu().memberOrderMenu(admin); break;
				default: System.out.println("번호를 잘못 입력하셨습니다!"); continue;
			}
			if(result == 0) {
				break;
			} else {
				continue;
			}
		}
	}
	
	/* 판매 순위 확인 */
	private int salesRanking() {
		result = 1;
		while(true) {
			System.out.println(">>>>       BurgerHI 카테고리 랭킹       <<<<");
			System.out.println("=================================================");
			System.out.println("               |                |               ");
			System.out.println("      1      　|        2       |       3      ");
			System.out.println("  햄버거 랭킹  |   음료수 랭킹  |  사이드 랭킹  ");
			System.out.println("               |                |             ");
			System.out.println("=================================================");
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. \n");
			
			message = "\n  → 번호를 선택해 주세요: ";
			int rankNum = inputMismatchCheck(message);
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); 
			
			switch (rankNum) {
				case 0: result = 0; break;
				case 1: selectHambergerRanking(); break;	// 햄버거 랭킹을 보여 줄 메소드
				case 2: selectDrinkRanking(); break;		// 음료수 랭킹을 보여 줄 메소드
				case 3: selectSideRanking(); break;			// 사이드 랭킹을 보여 줄 메소드
				default: System.out.println("번호를 잘못 입력하셨습니다!"); continue;   // 번호 잘못 입력할 경우 다시 처음으로 돌아가게 설정
			}
			break;
		}
        return result;
	}

	/* 메뉴 관리 */
	private int menuManagement() {
		result = 1;
		while(true) {
			System.out.println(">>>>        BurgerHI 메뉴 관리 시스템        <<<<");
			System.out.println("=================================================");
			System.out.println("                       |                       ");
			System.out.println("           1           |           2           ");
			System.out.println("     카테고리 관리     |        메뉴 관리      ");
			System.out.println("                       |                       ");
			System.out.println("=================================================");
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. \n");
			
			message = "\n  → 번호를 선택해 주세요: ";
			int menuAdmin = inputMismatchCheck(message);
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); 
			
			switch (menuAdmin) {
			case 0: result = 0; break;
			case 1: result = categoryDMLManagement(); break;	// 카테고리 관리 메소드
			case 2: result = menuDMLManagement(); break;		// 메뉴 관리 메소드
			default: System.out.println("번호를 잘못 입력하셨습니다!"); continue;   // 번호 잘못 입력할 경우 다시 처음으로 돌아가게 설정
			}
			break;
		}
		
		return result;
	}

	/* 매출 확인 */
	private int salesConfirmation() {
		while(true) {
			System.out.println(">>>>        BurgerHI 매출 관리 시스템        <<<<");
			System.out.println("=================================================");
			System.out.println("                       |                       ");
			System.out.println("           1           |           2           ");
			System.out.println("      날짜별 매출      |     카테고리별 매출   ");
			System.out.println("                       |                       ");
			System.out.println("=================================================");
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. \n");
			
			message = "\n  → 번호를 선택해 주세요: ";
			int salesNum = inputMismatchCheck(message);
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			
			switch(salesNum) {
			case 0: result = 0; break;
			case 1: result = salesBydate(); break;      // 날짜별 매출 확인하는 메소드
			case 2: result = salesCategory(); break;    // 카테고리별 매출 확인하는 메소드
			}
			break;
		}
		
		return result;
	}

	/* 회원 정보 확인 */ 
	private int memberInfoConfirmation() {
		System.out.println(">>>>        BurgerHI 회원 정보 시스템        <<<<");
        System.out.println("=================================================");
        System.out.println("                       |                       ");
        System.out.println("           1           |           2           ");
        System.out.println("       회원 조회       |     회원 등급 수정    ");
        System.out.println("                       |                       ");
        System.out.println("=================================================");
        System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
        
        message = "\n  → 번호를 선택해 주세요: ";
		int selectNum = inputMismatchCheck(message);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		if(selectNum == 0) {
       	 result = 0;
        } else if(selectNum == 1) {
        	System.out.println(">>>>         BurgerHI 회원 전체 조회         <<<<");
    		System.out.println("=================================================");
    		System.out.println();
    		List<Object> user = adminController.selectAllUser();
    		
    		for(int i = 0; i < user.size(); i+=7) {
    			System.out.println("▶ 회원번호: " + user.get(i));
    			System.out.println("▶ 회원이름: " + user.get(i+1));
    			System.out.println("▶ 회원ID: " + user.get(i+2));
    			System.out.println("▶ 등급: " + user.get(i+3));
    			System.out.println("▶ 보유포인트: " + format.format(user.get(i+4)));
    			System.out.println("▶ 전화번호: " + user.get(i+5));
    			System.out.println("▶ 이용여부(Y/N): " + user.get(i+6));
    			System.out.println();
    		}
        } else if (selectNum == 2) {
        	System.out.println(">>>>         BurgerHI 회원 등급 수정         <<<<");
    		System.out.println("=================================================");
    		List<Object> user = adminController.selectAllUser();

    		for (int i = 0; i < user.size(); i += 7) {

    			System.out.println("▶ 회원번호: " + user.get(i));
    			System.out.println("▶ 회원이름: " + user.get(i + 1));
    			System.out.println("▶ 회원ID: " + user.get(i + 2));
    			System.out.println("▶ 등급: " + user.get(i + 3));
    			System.out.println("▶ 보유포인트: " + format.format(user.get(i + 4)));
    			System.out.println("▶ 전화번호: " + user.get(i + 5));
    			System.out.println("▶ 이용여부(Y/N): " + user.get(i + 6));
    			System.out.println();
    		}

    		System.out.println();

    		System.out.print("\n → 수정하실 회원 번호를 입력해 주세요: ");

    		int userNo = sc.nextInt();

    		System.out.print(" → 수정하실 등급 gradeNo를 입력해 주세요(관리자:4): ");
    		int gradeNo = sc.nextInt();

    		result = adminController.updateUserGrade(userNo, gradeNo);
        }
		return result;
	}
	
	/* 햄버거 판매 랭킹 */
	private void selectHambergerRanking() {
		int category = 1;
		Map<Integer, String> hamberger = adminController.selectRanking(category);
		
		System.out.println(">>>>          BurgerHI 햄버거 랭킹           <<<<");
		System.out.println("=================================================");
		System.out.println("\n▶ 판매량 1위 " + (hamberger.get(1) != null ? hamberger.get(1) : "판매 내역 없음"));
		System.out.println("▶ 판매량 2위 " + (hamberger.get(2) != null ? hamberger.get(2) : "판매 내역 없음"));
		System.out.println("▶ 판매량 3위 " + (hamberger.get(3) != null ? hamberger.get(3) : "판매 내역 없음"));
		System.out.println("\n\n\n\n\n");
	}

	/* 음료수 판매 랭킹 */
	private void selectDrinkRanking() {
		int category = 2;
		Map<Integer, String> drink = adminController.selectRanking(category);
		
		System.out.println(">>>>          BurgerHI 음료수 랭킹           <<<<");
		System.out.println("=================================================");
		System.out.println("\n▶ 판매량 1위 " + (drink.get(1) != null ? drink.get(1) : "판매 내역 없음"));
		System.out.println("▶ 판매량 2위 " + (drink.get(2) != null ? drink.get(2) : "판매 내역 없음"));
		System.out.println("▶ 판매량 3위 " + (drink.get(3) != null ? drink.get(3) : "판매 내역 없음"));
		System.out.println("\n\n\n\n\n");
	}

	/* 사이드 판매 랭킹 */
	private void selectSideRanking() {
		int category = 3;
		Map<Integer, String> side = adminController.selectRanking(category);
		
		System.out.println(">>>>          BurgerHI 사이드 랭킹           <<<<");
		System.out.println("=================================================");
		System.out.println("\n▶ 판매량 1위 " + (side.get(1) != null ? side.get(1) : "판매 내역 없음"));
		System.out.println("▶ 판매량 2위 " + (side.get(2) != null ? side.get(2) : "판매 내역 없음"));
		System.out.println("▶ 판매량 3위 " + (side.get(3) != null ? side.get(3) : "판매 내역 없음"));
		System.out.println("\n\n\n\n\n");
	}
	
	/* 카테고리 관리 메소드 */
	private int categoryDMLManagement() {
		result = 1;
		while(true) {
			System.out.println(">>>>      BurgerHI 카테고리 관리 시스템      <<<<");
			System.out.println("=================================================");
			System.out.println("               |                |               ");
			System.out.println("      1      　|       2        |       3      ");
			System.out.println(" 카테고리 추가 | 카테고리 수정  | 카테고리 삭제 ");
			System.out.println("               |                |               ");
			System.out.println("=================================================");
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. \n");
			
			List<CategoryDTO> categoryList = adminController.selectAllCategory();
			for(CategoryDTO cate : categoryList) {
				System.out.println("▶ " + cate.getCode() + ". " + cate.getName() + "(추천 카테고리: " + cate.getRefName() + ")");
			}
			
			message = "\n  → 번호를 선택해 주세요: ";
			int categoryMenu = inputMismatchCheck(message);
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); 
			
			switch(categoryMenu) {
			case 0: result = 0; break;
			case 1: result = registCategory(); continue;            // 카테고리 추가하는 메소드
			case 2: result = modifyCategory(); continue;            // 카테고리 수정하는 메소드
			case 3: result = deleteCategory(); continue;            // 카테고리 삭제하는 메소드
			default : System.out.println("번호를 잘못 입력하셨습니다!"); continue;   // 번호 잘못 입력할 경우 다시 처음으로 돌아가게 설정
			}
			break;
		}
        return result;
	}
	
	/* 카테고리 추가 */
	private int registCategory() {
		System.out.println(">>>>      BurgerHI 카테고리 추가 시스템      <<<<");
		System.out.println("=================================================");
		List<CategoryDTO> categoryList = adminController.selectAllCategory();
		for(CategoryDTO cate : categoryList) {
			System.out.println("▶ " + cate.getCode() + ". " + cate.getName() + "(추천 카테고리: " + cate.getRefName() + ")");
		}
		
		System.out.print("\n  → 추가할 카테고리명을 입력해 주세요: ");
		String categoryName = sc.nextLine();
		message = "\n  → 추천 카테고리 번호를 입력해 주세요: ";
		int refCategory = inputMismatchCheck(message);
		System.out.println("\n\n\n\n\n");
		
		int result = adminController.registCategory(categoryName, refCategory);
		return result;
	}

	/* 카테고리 수정 */
	private int modifyCategory() {
		System.out.println(">>>>      BurgerHI 카테고리 수정 시스템      <<<<");
		System.out.println("=================================================");
		List<CategoryDTO> categoryList = adminController.selectAllCategory();
		for(CategoryDTO cate : categoryList) {
			System.out.println("▶ " + cate.getCode() + ". " + cate.getName() + "(추천 카테고리: " + cate.getRefName() + ")");
		}
		
		message = "\n  → 변경할 카테고리 번호를 입력해 주세요: ";
		int categoryCode = inputMismatchCheck(message);
		System.out.print("\n  → 변경할 카테고리 이름을 입력해 주세요: ");
		sc.nextLine();
		String categoryName = sc.nextLine();
		message = "\n  → 변경할 추천 카테고리 번호를 입력해 주세요: ";
		int refCode = inputMismatchCheck(message);
		System.out.println("\n\n\n\n\n");
		
		CategoryDTO category = new CategoryDTO();
		category.setCode(categoryCode);
		category.setName(categoryName);
		category.setRefCode(refCode);
		
		int result = adminController.modifyCategory(category);
		return result;
	}

	/* 카테고리 삭제 */
	private int deleteCategory() {
		System.out.println(">>>>      BurgerHI 카테고리 삭제 시스템      <<<<");
		System.out.println("=================================================");
		List<CategoryDTO> categoryList = adminController.selectAllCategory();
		for(CategoryDTO cate : categoryList) {
			System.out.println("▶ " + cate.getCode() + ". " + cate.getName() + "(추천 카테고리: " + cate.getRefName() + ")");
		}
		
		System.out.print("\n → 삭제할 카테고리 이름을 입력해 주세요: ");
		String categoryName = sc.nextLine();
		int categoryNo = 0;
		for(CategoryDTO cate : categoryList) {
			if(cate.getName().equals(categoryName)) {
				categoryNo = cate.getCode();
				break;
			}
		}
		int result = adminController.deleteCategory(categoryNo);
		return result;
	}

	/* 메뉴 관리 메소드 */
	private int menuDMLManagement() {
		while(true) {
			System.out.println(">>>>        BurgerHI 메뉴 관리 시스템        <<<<");
			System.out.println("=================================================");
			System.out.println("               |                |               ");
			System.out.println("      1      　|       2        |       3       ");
			System.out.println("　 메뉴 추가 　|   메뉴 수정    |　 메뉴 삭제 　");
			System.out.println("               |                |               ");
			System.out.println("=================================================");
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. \n");
			
			List<MenuDTO> menuList = adminController.selectAllMenu();
			for(MenuDTO menu : menuList) {
				System.out.println("▶ " + menu.getMenuCode() + ". " + menu.getName() + "$" + menu.getPrice());
				System.out.println("　 카테고리 번호: " + menu.getCategoryCode() + " 주문 가능 여부: " + menu.getOrderable());
				System.out.println("　  \"" + menu.getExplain() + "\"");
			}
			
			message = "\n  → 번호를 선택해 주세요: ";
			int menuNum = inputMismatchCheck(message);
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); 
			
			switch(menuNum) {
			case 0: result = 0; break;
			case 1: result = registtMenu(); continue;              // 메뉴 추가하는 메소드
			case 2: result = modifyMenu(); continue;               // 메뉴 수정하는 메소드
			case 3: result = deleteMenu(); continue;               // 메뉴 삭제하는 메소드
			default : System.out.println("번호를 잘못 입력하셨습니다!"); continue;   // 번호 잘못 입력할 경우 다시 처음으로 돌아가게 설정
			}
			break;
		}
		return result;
	}

	/* 메뉴 추가 */
	private int registtMenu() {
		System.out.println(">>>>        BurgerHI 메뉴 추가 시스템        <<<<");
		System.out.println("=================================================");
		System.out.print("\n → 추가할 메뉴명을 입력해 주세요: ");
		String menuName = sc.nextLine();
		System.out.print("\n → 추가할 메뉴의 가격을 입력해 주세요: ");
		int menuPrice = sc.nextInt();
		System.out.print("\n → 추가할 메뉴의 설명을 입력해 주세요: ");
		sc.nextLine();
		String menuExplain = sc.nextLine();
		System.out.print("\n → 추가할 메뉴의 카테고리를 입력해 주세요: ");
		int categoryCode = sc.nextInt();
		System.out.print("\n → 추가할 메뉴의 판매여부를 입력해 주세요: ");
		sc.nextLine();
		String orderable = sc.nextLine().toUpperCase();
		System.out.println("\n\n\n\n\n");
		
		MenuDTO menu = new MenuDTO();
		menu.setName(menuName);
		menu.setPrice(menuPrice);
		menu.setExplain(menuExplain);
		menu.setCategoryCode(categoryCode);
		menu.setOrderable(orderable);
		
		int result = adminController.registtMenu(menu);
		return result;
	}

	/* 메뉴 수정 */
	private int modifyMenu() {
		System.out.println(">>>>        BurgerHI 메뉴 수정 시스템        <<<<");
		System.out.println("=================================================");
		System.out.print("\n → 수정할 메뉴의 번호를 입력해 주세요: ");
		int menuNum = sc.nextInt();
		System.out.print("\n → 수정할 메뉴명을 입력해 주세요: ");
		sc.nextLine();
		String menuName = sc.nextLine();
		System.out.print("\n → 수정할 메뉴의 가격을 입력해 주세요: ");
		int menuPrice = sc.nextInt();
		System.out.print("\n → 수정할 메뉴의 설명을 입력해 주세요: ");
		sc.nextLine();
		String menuExplain = sc.nextLine();
		System.out.print("\n → 수정할 메뉴의 카테고리를 입력해 주세요: ");
		int categoryCode = sc.nextInt();
		System.out.print("\n → 수정할 메뉴의 판매여부를 입력해 주세요: ");
		sc.nextLine();
		String orderable = sc.nextLine().toUpperCase();
		System.out.println("\n\n\n\n\n");
		
		MenuDTO menu = new MenuDTO();
		menu.setMenuCode(menuNum);
		menu.setName(menuName);
		menu.setPrice(menuPrice);
		menu.setExplain(menuExplain);
		menu.setCategoryCode(categoryCode);
		menu.setOrderable(orderable);
		
		int result = adminController.modifyMenu(menu);
		return result;
	}

	/* 메뉴 삭제 */
	private int deleteMenu() {
		System.out.println(">>>>        BurgerHI 메뉴 삭제 시스템        <<<<");
		System.out.println("=================================================");
		System.out.print("\n → 삭제할 메뉴명을 입력해 주세요: ");
		String menuName = sc.nextLine();
		System.out.println("\n\n\n\n\n");
		int result = adminController.deleteMenu(menuName);
		return result;
	}
	
	/* 날짜별 매출 */
	private int salesBydate() {
		result = 1;
		System.out.println(">>>>        BurgerHI 날짜별 매출 확인        <<<<");
		System.out.println("=================================================");
		System.out.print("\n → 매출을 조회할 '월'을 입력하세요: ");
		int month = sc.nextInt();
		System.out.print("※ 월 단위 매출 조회 희망 시 '일'을 0으로 입력 하세요.");
		System.out.print("\n → 매출을 조회할 '일'을 입력하세요: ");
		int date = sc.nextInt();
		
		int monthSales = adminController.selectDateSales(month, date);
		 if(date == 0) {
	         System.out.println("\n ▶ " + month + "월의 매출액은 총 " + format.format(monthSales) + "원 입니다.");
	         System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	      } else {
	         System.out.println("\n ▶ " + month + "월" + date + "일의 매출액은 총 " + format.format(monthSales) + "원 입니다. ");
	         System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	      }
		return result;
	}

	/* 카테고리별 매출 */
	private int salesCategory() {
		while(true) {
			System.out.println(">>>>      BurgerHI 카테고리별 매출 확인      <<<<");
			System.out.println("=================================================");
			System.out.println("              |                |               ");
			System.out.println("       1 　   |        2　　   |       3       ");
			System.out.println(" 총 누적 매출 |회원 등급별 매출|결제 종류별 매출");
			System.out.println("              |                |               ");
			System.out.println("=================================================");
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. \n");
			System.out.print("\n  → 번호를 선택해 주세요: ");
			
			int salesNum = sc.nextInt();
			
			Date now = new Date();
			SimpleDateFormat sdfm = new SimpleDateFormat("yy년 MM월 dd일");
			
			if(salesNum == 1) {
				int totalSales = adminController.selectAllSales();
				System.out.println("\n\n ▶ " + sdfm.format(now) + " 기준 총 매출액은 " + format.format(totalSales) + "원 입니다.");
				break;
			} else if(salesNum == 2) {
				Map<Integer, Integer> gadeSales = adminController.selectGradeSales();
				System.out.println("\n\n ▶ Family등급 매출액: " + format.format(gadeSales.get(0)) + "원");
				System.out.println("\n ▶ Silver등급 매출액: " + format.format(gadeSales.get(1)) + "원");
				System.out.println("\n ▶ Gold등급 매출액: " + format.format(gadeSales.get(2)) + "원");
				System.out.println("\n ▶ 관리자등급 매출액: " + format.format(gadeSales.get(3)) + "원");
				System.out.println("\n ▶ 비회원등급 매출액: " + format.format(gadeSales.get(4)) + "원");
				break;
			} else if(salesNum == 3) {
				Map<String, Integer> methodSales = adminController.selectMethodSales();
				
				System.out.println("\n\n ▶ 기프티콘 결제의 총 매출: " + (methodSales.get("기프티콘") != null ? format.format(methodSales.get("기프티콘")) : 0) + "원" + "\n");
				System.out.println(" ▶ 카드 결제의 총 매출: " + (methodSales.get("카드") != null ? format.format(methodSales.get("카드")) : 0) + "원" + "\n");
				System.out.println(" ▶ 현금 결제의 총 매출: " + (methodSales.get("현금") != null ? format.format(methodSales.get("현금")) : 0) + "원" + "\n");
				break;
			} else if(salesNum == 0) {
				result = 0;
			} else {
				System.out.println("번호를 잘못 입력하셨습니다!");
				continue;
			}
			System.out.println("\n\n\n");
			break;
		}
		return result;
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
