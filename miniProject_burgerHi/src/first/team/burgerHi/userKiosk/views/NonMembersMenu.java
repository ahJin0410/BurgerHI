package first.team.burgerHi.userKiosk.views;

import first.team.burgerHi.userKiosk.controller.ClientController;
import first.team.burgerHi.userKiosk.model.dto.UserDTO;

public class NonMembersMenu {
	private ClientController clientController = new ClientController();
	private UserDTO nonMember = clientController.registNonMember();
	
	public void displayNonMemberMenu() {
		new MembersMenu().memberOrderMenu(nonMember);
		clientController.deleteOrderMenu(nonMember.getUserNo());
	}
	
}
