package Interface;

import Info.ProductInfo;

import java.util.*;

public interface Interface {
	
	//파일생성(회원정보, 상품정보, 주문내역)
	public String File(String fileName);
	
	//입력(회원가입, 상품등록)
	public void Insert(String Info, String fileName);

	//수정(회원정보.상품정보)
	public void Modify(List<ProductInfo> plist, String fileName);


	//수정
	
	//삭제


}
