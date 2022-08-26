package first.team.burgerHi.userKiosk.model.dto;

import java.io.Serializable;

public class SplitPaymentDTO implements Serializable {
	private static final long serialVersionUID = -8656613330077733870L;
	
	private int userNo;
	private String splitPaymentYn;
	private String splitPayment;
	private int paymentPrice;
	private String paymentBy;
	private int totalPrice;
	private int gradeNo;
	private int cardCode;
	
	public SplitPaymentDTO() {
	}

	public SplitPaymentDTO(int userNo, String splitPaymentYn, String splitPayment, int paymentPrice, String paymentBy,
			int totalPrice, int gradeNo, int cardCode) {
		this.userNo = userNo;
		this.splitPaymentYn = splitPaymentYn;
		this.splitPayment = splitPayment;
		this.paymentPrice = paymentPrice;
		this.paymentBy = paymentBy;
		this.totalPrice = totalPrice;
		this.gradeNo = gradeNo;
		this.cardCode = cardCode;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getSplitPaymentYn() {
		return splitPaymentYn;
	}

	public void setSplitPaymentYn(String splitPaymentYn) {
		this.splitPaymentYn = splitPaymentYn;
	}

	public String getSplitPayment() {
		return splitPayment;
	}

	public void setSplitPayment(String splitPayment) {
		this.splitPayment = splitPayment;
	}

	public int getPaymentPrice() {
		return paymentPrice;
	}

	public void setPaymentPrice(int paymentPrice) {
		this.paymentPrice = paymentPrice;
	}

	public String getPaymentBy() {
		return paymentBy;
	}

	public void setPaymentBy(String paymentBy) {
		this.paymentBy = paymentBy;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getGradeNo() {
		return gradeNo;
	}

	public void setGradeNo(int gradeNo) {
		this.gradeNo = gradeNo;
	}

	public int getCardCode() {
		return cardCode;
	}

	public void setCardCode(int cardCode) {
		this.cardCode = cardCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SplitPaymentDTO [userNo=" + userNo + ", splitPaymentYn=" + splitPaymentYn + ", splitPayment="
				+ splitPayment + ", paymentPrice=" + paymentPrice + ", paymentBy=" + paymentBy + ", totalPrice="
				+ totalPrice + ", gradeNo=" + gradeNo + ", cardCode=" + cardCode + "]";
	}

}
