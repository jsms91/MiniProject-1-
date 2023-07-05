package Interface;

import Info.ProductInfo;
import Info.UserInfo;

import java.util.*;

public interface Interface {
	
	//1.파일생성(회원정보, 상품정보, 주문내역)
    public String File(String fileName);
	
	//2.입력(회원가입, 상품등록) 파일에 저장
    public void Insert(String Info, String fileName);

    //3-1 회원정보수정
    public void UserUpload(List<UserInfo> ulist, String fileName);

	//3-2.파일수정(회원정보.상품정보) 파일에 재업로드
    public void MenuUpload(List<ProductInfo> plist, String fileName);


	//수정
	
	//삭제


}
