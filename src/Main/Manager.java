package Main;

import Info.*;
import Interface.*;
import java.util.*;

public class Manager {

	private Data data;

	private UserInfo userInfo = new UserInfo(); // UserInfo 클래스의 인스턴스 생성

	private Interface inter = new Method();   // Method 인터페이스의 인스턴스 생성
	
	Scanner sc = new Scanner(System.in);
	
	public Manager(Data data) {
		this.data = data;
	}
	
	public void ManagerView() { //관리자 페이지

		System.out.println("\n=============== 관리자 페이지 ===============\n");
		System.out.println("1. 회원관리 || 2. 상품관리 || 3. 로그아웃 \n");
		System.out.print("* 원하는 메뉴를 선택하세요. >>> ");

		int n = sc.nextInt();

		switch (n) {
			case 1:
				UserManager usermanager = new UserManager(data);
				usermanager.UserManagerView(); //회원관리 페이지 이동
				break;

			case 2:
				ProductManager productManager = new ProductManager(data);
				productManager.ProductManagerView(); //상품관리 페이지 이동
				break;

			case 3 :
				System.out.println("[로그아웃]");
				data.setLoginId("");
				Main.main(new String[] {}); //메인페이지 이동
				break;

			default:
				System.out.println("\n[Error!!]\n");
				ManagerView();
				break;

		}
	}

}