package first.team.burgerHi.adminKiosk.model.dto;

import java.io.Serializable;

public class OrderDTO implements Serializable {
	private static final long serialVersionUID = 1779369780440299020L;

	private int code;		// 주문번호
	private String date;	// 주문일
	private int totalPrice;	// 총금액
	
	public OrderDTO() {
	}
	
	public OrderDTO(int code, String date, int totalPrice) {
		this.code = code;
		this.date = date;
		this.totalPrice = totalPrice;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "OrderDTO [code=" + code + ", date=" + date + ", totalPrice=" + totalPrice + "]";
	}
}
