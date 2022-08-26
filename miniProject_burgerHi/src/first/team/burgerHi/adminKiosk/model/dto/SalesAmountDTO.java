package first.team.burgerHi.adminKiosk.model.dto;

import java.io.Serializable;

public class SalesAmountDTO implements Serializable {
	private static final long serialVersionUID = 1373423477140302645L;

	private int orderCode;		// 주문번호
	private int menuCode;		// 메뉴번호
	private int menuAmount;		// 메뉴수량
	private int menuPrice;		// 메뉴금액
	
	public SalesAmountDTO() {
	}
	
	public SalesAmountDTO(int orderCode, int menuCode, int menuAmount, int menuPrice) {
		this.orderCode = orderCode;
		this.menuCode = menuCode;
		this.menuAmount = menuAmount;
		this.menuPrice = menuPrice;
	}
	
	public int getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}
	public int getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(int menuCode) {
		this.menuCode = menuCode;
	}
	public int getMenuAmount() {
		return menuAmount;
	}
	public void setMenuAmount(int menuAmount) {
		this.menuAmount = menuAmount;
	}
	public int getMenuPrice() {
		return menuPrice;
	}
	public void setMenuPrice(int menuPrice) {
		this.menuPrice = menuPrice;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "SalesAmountDTO [orderCode=" + orderCode + ", menuCode=" + menuCode + ", menuAmount=" + menuAmount
				+ ", menuPrice=" + menuPrice + "]";
	}
}
