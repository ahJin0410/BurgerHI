package first.team.burgerHi.adminKiosk.model.dto;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 8747328479048018570L;
  
	private int code;		// 카테고리코드
	private String name;	// 카테고리명
	private int refCode;	// 추천카테고리코드
	private String refName;	// 추천카테고리명
	
	public CategoryDTO() {
	}

	public CategoryDTO(int code, String name, int refCode, String refName) {
		this.code = code;
		this.name = name;
		this.refCode = refCode;
		this.refName = refName;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRefCode() {
		return refCode;
	}

	public void setRefCode(int refCode) {
		this.refCode = refCode;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CategoryDTO [code=" + code + ", name=" + name + ", refCode=" + refCode + ", refName=" + refName + "]";
	}
	
}
