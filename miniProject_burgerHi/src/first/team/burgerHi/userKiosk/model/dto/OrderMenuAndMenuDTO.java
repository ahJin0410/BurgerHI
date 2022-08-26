package first.team.burgerHi.userKiosk.model.dto;

import java.io.Serializable;

public class OrderMenuAndMenuDTO implements Serializable {
	private static final long serialVersionUID = 2449353793380488279L;
	
	private int orderMenuNo;		// 주문번호(장바구니)
	private int userNo;				// 회원번호
	private int menuCode;			// 메뉴번호
	private String name;			// 메뉴명
	private int price;				// 메뉴가격
	private int orderAmount;		// 주문수량
	private String orderMenuSetYn;	// 세트메뉴 여부

	public OrderMenuAndMenuDTO() {
	}

	public OrderMenuAndMenuDTO(int orderMenuNo, int userNo, int menuCode, String name, int price, int orderAmount,
			String orderMenuSetYn) {
		this.orderMenuNo = orderMenuNo;
		this.userNo = userNo;
		this.menuCode = menuCode;
		this.name = name;
		this.price = price;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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
		return "OrderMenuAndMenuDTO [orderMenuNo=" + orderMenuNo + ", userNo=" + userNo + ", menuCode=" + menuCode
				+ ", name=" + name + ", price=" + price + ", orderAmount=" + orderAmount + ", orderMenuSetYn="
				+ orderMenuSetYn + "]";
	}
}
