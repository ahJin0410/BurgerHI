package first.team.burgerHi.userKiosk.views;

import java.util.InputMismatchException;
import java.util.Scanner;

import first.team.burgerHi.adminKiosk.views.AdminMenu;
import first.team.burgerHi.userKiosk.controller.ClientController;
import first.team.burgerHi.userKiosk.model.dto.UserDTO;

public class KioskMain {
	private ClientController clientController = new ClientController();
	
	Scanner sc = new Scanner(System.in);
	
	public void displayMainMenu() {
		
		do {
			/* BurgerHI 메인 주문 화면(첫 화면) */
			System.out.println();
			System.out.println(">>>>       어서오세요 BurgerHI 입니다.       <<<<");
			System.out.println("=================================================");
			System.out.println("               |                  |             ");
			System.out.println("      1        |        2         |      3     ");
			System.out.println(" 회원 주문하기 |  비회원 주문하기 |   회원가입 ");
			System.out.println("               |                  |             ");
			System.out.println("=================================================");
			int menuSelect = 0;
			
			while(true) {
				try {		// 실수로 문자열을 입력했을 경우의 예외처리
					System.out.print("\n  → 번호를 선택해 주세요: ");
					menuSelect = sc.nextInt();
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				} catch(InputMismatchException e) {
					System.out.println("\n ※ 숫자로 입력해 주세요!");
					sc.next();
					continue;
				} break;
			}
			
			switch(menuSelect) {
				case 1: memberLogin(); continue;
				case 2: new NonMembersMenu().displayNonMemberMenu(); continue;
				case 3: registMember(); continue;
				default: System.out.println("  → 번호를 잘못 입력하셨습니다!\n\n\n"); continue;
			}
		} while(true);
	}
	
	/* 로그인 */
	public void memberLogin() {
		while(true) {
			System.out.println(">>>>            BurgerHI 회원 주문           <<<<");
			System.out.println("=================================================");
			System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
			System.out.print("\n  →  ID를 입력해 주세요: ");
			String id = sc.nextLine();
			closeMenu(id);
			System.out.print("\n  →  PassWord를 입력해 주세요: ");
			String pwd = sc.nextLine();
			closeMenu(pwd);
			
			UserDTO user = clientController.memberLogin(id, pwd);
			
			if(user.getGradeNo() == 0) {
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println("\n  ※ 아이디와 비밀번호가 일치하지 않습니다!\n\n\n ");
				continue;
			} else if(user.getGradeNo() == 4) {
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				new AdminMenu().displayNonMemberMenu(user);
				break;
			} else {
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				new MembersMenu().displayMemberMenu(user);
				break;
			}
		}
	}
	
	/* 회원가입 */
	public void registMember() {
		String memberId = "";
		
		System.out.println(">>>>         BurgerHI 회원가입 안내         <<<<");
		System.out.println("=================================================");
		System.out.println(" * 프 로 그 램 종 료 는 0 번 을 눌 러 주 세 요. ");
		System.out.print("\n →  본인의 성함을 입력해 주세요: ");
		sc.nextLine();
		String name = sc.nextLine();
		closeMenu(name);
		
		/* 아이디 중복 확인 */
		while(true) {
			System.out.print("\n →  사용하실 아이디를 입력해 주세요: ");
			memberId = sc.nextLine();
			closeMenu(memberId);
			int checkResult = clientController.checkMemberId(memberId);
			if(checkResult == 1) {
				System.out.println("\n※ 이미 사용중인 아이디 입니다! 다시 입력해 주세요. ※\n\n\n");
				continue;
			} else {
				break;
			}
		}
		System.out.print("\n →  사용하실 비밀번호를 입력해 주세요: ");
		String memberPwd = sc.nextLine();
		closeMenu(memberPwd);
		System.out.print("\n →  사용하시는 휴대폰 번호를 입력해 주세요: ");
		String memberPhone = sc.nextLine();
		closeMenu(memberPhone);
		String formatMemberPhone = phoneFormat(memberPhone);		// 휴대폰 번호의 경우 일정한 format으로 가공하여 DB Insert
		System.out.println("\n\n\n\n\n\n\n\n\n\n");
		
		int result = clientController.registMember(name, memberId, memberPwd, formatMemberPhone);
		
		if(result > 0) {
			System.out.println(" ※ 회원가입이 정상적으로 처리되었습니다.\n\n\n");
		}else {
			System.out.println(" ※ 회원가입에 실패하셨습니다.\n\n\n");
		}
	}
	
	/* 전화번호가 일정한 format으로 들어갈 수 있도록 하는 메소드 */
	public String phoneFormat(String memberPhone) {
		if(memberPhone != null) {
			memberPhone = memberPhone.replaceAll("[^0-9]", "");
			if(memberPhone.length() == 11) {
				memberPhone = memberPhone.substring(0, 3) + "-" + memberPhone.substring(3, 7) + "-" + memberPhone.substring(7);
			} else if(memberPhone.length() == 8){
				memberPhone = memberPhone.substring(0, 4) + "-" + memberPhone.substring(4);
			} else {
				if(memberPhone.startsWith("02")) {
					memberPhone = memberPhone.substring(0, 2) + "-" + memberPhone.substring(2, 5) + "-" + memberPhone.substring(5);
				} else {
					memberPhone = memberPhone.substring(0, 3) + "-" + memberPhone.substring(3, 6) + "-" + memberPhone.substring(7);
				}
			}
		}
		return memberPhone;
	}
	
	/* 0번을 누를 경우 키오스크 첫 페이지로 이동 */
	public void closeMenu(String check) {
		if(check.equals("0")) {
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			displayMainMenu();
		}
	}
}
