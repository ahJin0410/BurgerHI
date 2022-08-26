package first.team.burgerHi.adminKiosk.model.dto;

import java.io.Serializable;

public class GradeDTO implements Serializable {
	private static final long serialVersionUID = 6123129687179204747L;

	private int no;			// 등급번호
	private String name;	// 등급명
	private int discount;	// 등급별 할인율
	
	public GradeDTO() {
	}
	
	public GradeDTO(int no, String name, int discount) {
		this.no = no;
		this.name = name;
		this.discount = discount;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "GradeDTO [no=" + no + ", name=" + name + ", discount=" + discount + "]";
	}
}
