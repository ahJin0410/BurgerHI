package first.team.burgerHi.userKiosk.model.dto;

import java.io.Serializable;

public class MenuDTO implements Serializable {
	private static final long serialVersionUID = -470200833766239082L;

	private int menuCode;		// 메뉴번호
	private String name;		// 메뉴명
	private int price;			// 메뉴가격
	private String explain;		// 메뉴설명
	private int categoryCode;	// 카테고리번호
	private String orderable;	// 주문가능여부
	
	public MenuDTO() {
	}
	
	public MenuDTO(int menuCode, String name, int price, String explain, int categoryCode, String orderable) {
		this.menuCode = menuCode;
		this.name = name;
		this.price = price;
		this.explain = explain;
		this.categoryCode = categoryCode;
		this.orderable = orderable;
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
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public int getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getOrderable() {
		return orderable;
	}
	public void setOrderable(String orderable) {
		this.orderable = orderable;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "MenuDTO [menuCode=" + menuCode + ", name=" + name + ", price=" + price + ", explain=" + explain
				+ ", categoryCode=" + categoryCode + ", orderable=" + orderable + "]";
	}
}
