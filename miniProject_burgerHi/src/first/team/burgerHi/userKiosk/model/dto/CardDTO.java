package first.team.burgerHi.userKiosk.model.dto;

import java.io.Serializable;

public class CardDTO implements Serializable {
	private static final long serialVersionUID = -9182573456339995102L;

	private int code;			// 카드코드
	private String bank;		// 은행명
	private String discount;	// 할인율
	private String cardable;	// 제휴여부
	
	public CardDTO() {
	}
	
	public CardDTO(int code, String bank, String discount, String cardable) {
		this.code = code;
		this.bank = bank;
		this.discount = discount;
		this.cardable = cardable;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getCardable() {
		return cardable;
	}
	public void setCardable(String cardable) {
		this.cardable = cardable;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "CardDTO [code=" + code + ", bank=" + bank + ", discount=" + discount + ", cardable=" + cardable + "]";
	}
}
