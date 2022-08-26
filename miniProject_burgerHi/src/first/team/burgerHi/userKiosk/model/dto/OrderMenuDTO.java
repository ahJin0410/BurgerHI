package first.team.burgerHi.userKiosk.model.dto;

import java.io.Serializable;

public class OrderMenuDTO implements Serializable {
	private static final long serialVersionUID = 3963982027066769899L;

	private int orderMenuNo;		// 주문번호(장바구니)
	private int userNo;				// 회원번호
	private int menuCode;			// 메뉴번호
	private int orderAmount;		// 주문수량
	private String orderMenuSetYn;	// 세트메뉴 여부
	
	public OrderMenuDTO() {
	}

	public OrderMenuDTO(int orderMenuNo, int userNo, int menuCode, int orderAmount, String orderMenuSetYn) {
		this.orderMenuNo = orderMenuNo;
		this.userNo = userNo;
		this.menuCode = menuCode;
		this.orderAmount = orderAmount;
		this.orderMenuSetYn = orderMenuSetYn;
	}

	public int getOrderMenuNo() {
		return orderMenuNo;
	}

	public void setOrderMenuNo(int orderMenuNo) {
		this.orderMenuNo = orderMenuNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(int menuCode) {
		this.menuCode = menuCode;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderMenuSetYn() {
		return orderMenuSetYn;
	}

	public void setOrderMenuSetYn(String orderMenuSetYn) {
		this.orderMenuSetYn = orderMenuSetYn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "OrderMenuDTO [orderMenuNo=" + orderMenuNo + ", userNo=" + userNo + ", menuCode=" + menuCode
				+ ", orderAmount=" + orderAmount + ", orderMenuSetYn=" + orderMenuSetYn + "]";
	}

}
