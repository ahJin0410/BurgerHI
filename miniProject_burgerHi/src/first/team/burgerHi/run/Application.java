package first.team.burgerHi.run;

import first.team.burgerHi.userKiosk.views.KioskMain;

public class Application {
	public static void main(String[] args) {
		/* view 화면이 보여지도록 인스턴스 생성 및 메소드 호출 */
		KioskMain orderMenu = new KioskMain();
		orderMenu.displayMainMenu(); 
	}
}
