package first.team.burgerHi.userKiosk.model.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 9136861611705599384L;

	private int userNo;		// 회원번호
	private String name;	// 회원이름
	private String id;		// 회원ID
	private String pwd;		// 회원PWD
	private int gradeNo;	// 회원등급
	private int userPoint;	// 보유포인트
	private String phone;	// 전화번호
	
	public UserDTO() {
	}
	
	public UserDTO(int userNo, String name, String id, String pwd, int gradeNo, int userPoint, String phone) {
		this.userNo = userNo;
		this.name = name;
		this.id = id;
		this.pwd = pwd;
		this.gradeNo = gradeNo;
		this.userPoint = userPoint;
		this.phone = phone;
	}
	
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(int gradeNo) {
		this.gradeNo = gradeNo;
	}
	public int getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(int userPoint) {
		this.userPoint = userPoint;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "UserDTO [userNo=" + userNo + ", name=" + name + ", id=" + id + ", pwd=" + pwd + ", gradeNo=" + gradeNo
				+ ", userPoint=" + userPoint + ", phone=" + phone + "]";
	}
}
