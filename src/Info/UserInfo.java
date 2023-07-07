package Info;//ok

import Interface.*;
import java.util.*;

public class UserInfo { //고객정보 필드가 저장
	
	private int uNo; //회원번호
	private String uId; //아이디
	private String uPw; //비밀번호
	private String uName; //이름
	private int uCount; //구매횟수
	private String uGrade; //회원등급
	private String uCategory; //회원분류(Admin vs Member)

	public UserInfo() {
		super();
	}
	
	public UserInfo(int uNo, String uId, String uPw, String uName, int uCount, String uGrade, String uCategory) {
		super();
		this.uNo = uNo;
		this.uId = uId;
		this.uPw = uPw;
		this.uName = uName;
		this.uCount = uCount;
		this.uGrade = uGrade;
		this.uCategory = uCategory;
	}

	public int getuNo() {
		return uNo;
	}

	public void setuNo(int uNo) {
		this.uNo = uNo;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getuPw() {
		return uPw;
	}

	public void setuPw(String uPw) {
		this.uPw = uPw;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public int getuCount() {
		return uCount;
	}

	public void setuCount(int uCount) {
		this.uCount = uCount;
	}

	public String getuGrade() {
		return uGrade;
	}

	public void setuGrade(String uGrade) {
		this.uGrade = uGrade;
	}

	public String getuCategory() {
		return uCategory;
	}

	public void setuCategory(String uCategory) {
		this.uCategory = uCategory;
	}
	
}