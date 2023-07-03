package Main;

import Info.*;
import Interface.*;
import java.util.*;

public class Manager {

	Data data = new Data();
	
	UserInfo userInfo = new UserInfo(); // UserInfo 클래스의 인스턴스 생성

	private Interface minterface = new Method();   // Method 인터페이스의 인스턴스 생성
	//private ManagerInterface managerinterface = new ManagerMethod(data);  // ManagerMethod 인터페이스의 인스턴스 생성
	private List<UserInfo> uList;// = data.getUlist(); //회원정보 리스트
	private List<ProductInfo> pList;// = data.getPlist(); //상품정보 리스트
	private HashMap<String,UserInfo> uMap;// = data.getUmap(); // 회원정보 map
	private HashMap<String,ProductInfo> pMap;// = data.getPmap(); // 상품명, 정보 담긴 HashMap 클래스의 인스턴스 선언
	private String productfileName;// = data.getProductfilename(); // 파일명
	private String LoginId; // = data.getLoginId();//로그인한 회원 정보(아이디)
	
	Scanner sc = new Scanner(System.in);
	
	public Manager(Data data) {
		this.data = data;
		this.uList = data.getUlist();
		this.pList = data.getPlist();
		this.uMap = data.getUmap();
		this.pMap = data.getPmap();
		this.productfileName = data.getProductfilename();
		this.LoginId = data.getLoginId();
	}
	
	public void ManagerView() { //관리자 페이지

		System.out.println("\n=============== 관리자 페이지 ===============\n");
		while(true) {

			System.out.println("1. 회원관리 || 2. 상품관리 || 3. 로그아웃 || 0. 뒤로가기\n");
			System.out.print("원하는 메뉴를 선택하세요. >>> ");

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
					System.out.println("로그아웃");
					LoginId = "";
					data.setLoginId("");
					Main.main(new String[] {}); //메인페이지 이동
					break;

				case 0:
					if(!LoginId.equals("")) {
						System.out.println("로그아웃이 되지 않았습니다. 먼저 로그아웃을 해주세요.\n");
						ManagerView();
					}
					else {
						return;
					}

				default:
					System.out.println("\n>>>>> 잘못 입력했습니다.");
					break;
			}
		}
	}

}