package first.team.burgerHi.userKiosk.model.dto;

import java.io.Serializable;

public class PaymentDTO implements Serializable {
	private static final long serialVersionUID = 8824026504447223732L;

	private int orderCode;		// 주문번호
	private int userNo;			// 회원번호
	private int totalPrice;		// 주문금액
	private int gradeNo;		// 등급별 할인
	private int cardCode;		// 카드별 할인
	private int paymentPrice;	// 결제금액
	private String paymentBy;	// 결제방식
	
	public PaymentDTO() {
	}
	
	public PaymentDTO(int orderCode, int userNo, int totalPrice, int gradeNo, int cardCode, int paymentPrice,
			String paymentBy) {
		this.orderCode = orderCode;
		this.userNo = userNo;
		this.totalPrice = totalPrice;
		this.gradeNo = gradeNo;
		this.cardCode = cardCode;
		this.paymentPrice = paymentPrice;
		this.paymentBy = paymentBy;
	}
	
	public int getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "PaymentDTO [orderCode=" + orderCode + ", userNo=" + userNo + ", totalPrice=" + totalPrice + ", gradeNo="
				+ gradeNo + ", cardCode=" + cardCode + ", paymentPrice=" + paymentPrice + ", paymentBy=" + paymentBy
				+ "]";
	}
}
