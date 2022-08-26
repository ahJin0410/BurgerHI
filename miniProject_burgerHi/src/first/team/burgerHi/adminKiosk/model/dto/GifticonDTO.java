package first.team.burgerHi.adminKiosk.model.dto;

import java.io.Serializable;

public class GifticonDTO implements Serializable{
	private static final long serialVersionUID = -4514878043531717510L;
	
	private String no;		// 기프티콘 번호
	private int price;		// 기프티콘 잔액
	public GifticonDTO() {
	}
	public GifticonDTO(String no, int price) {
		this.no = no;
		this.price = price;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "GifticonDTO [no=" + no + ", price=" + price + "]";
	}
	
}
