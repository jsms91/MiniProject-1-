package Main;

import Info.*;
import Interface.HomeInterface;
import Interface.HomeMethod;

import java.util.*;


public class Home {

	private Data data;

	private HomeInterface homeinterface;

	private String LoginId; //로그인상태 아이디
	private List<UserInfo> uList; //회원정보 리스트
	private List<ProductInfo> pList; //상품정보 리스트
	// 주문내역 리스트
	//장바구니 리스트

	private HashMap<String, UserInfo> uMap;
	private HashMap<String, ProductInfo> pMap;


	Scanner sc = new Scanner(System.in);

	public Home(Data data) {
		this.data = data;
		this.uList = data.getUlist();
		this.pList = data.getPlist();
		this.uMap = data.getUmap();
		this.pList = data.getPlist();
	}

	
	public void HomeView() {
		System.out.println("\n=============== CAFE ===============\n");
		System.out.println("1.상품목록 || 2.상품검색 || 3.장바구니 || 4.주문 || 5.회원정보 || 6.로그아웃\n");
		System.out.print("원하는 메뉴를 선택하세요. >>> ");
		int n = sc.nextInt();

		switch (n) {
			case 1 :
				System.out.println("상품목록");
				MallProductListView(0); //1은 처음에 상품목록에 전체상품을 보여주고 이후 2는 커피 3은 에이디 이런식..
				break;
			case 2 :
				System.out.println("상품검색");
				break;
			case 3 :
				System.out.println("장바구니");
				break;
			case 4 :
				System.out.println("주문");
				break;
			case 5 :
				System.out.println("회원정보");
				break;
			case 6 :
				System.out.println("로그아웃");
				break;
			case 0 :
				System.out.println("종료");
				return;
			default :
				break;


		}


	}

	public void MallProductListView(int i) {

		int categoryNum = i; // 카테고리별로 리스트 출력
		int num=0;

		homeinterface = new HomeMethod(data);

		//System.out.println(pList.size());

		if(categoryNum==0) {System.out.println("All(***)  ||  Coffee  ||  Ade  ||  Desert  ||  Etc\n");}
		else if(categoryNum==1) {System.out.println("All  ||  Coffee(***)  ||  Ade  ||  Desert  ||  Etc\n");}
		else if(categoryNum==2) {System.out.println("All  ||  Coffee  ||  Ade(***)  ||  Desert  ||  Etc\n");}
		else if(categoryNum==3) {System.out.println("All  ||  Coffee  ||  Ade  ||  Desert(***)  ||  Etc\n");}
		else{System.out.println("All  ||  Coffee  ||  Ade  ||  Desert  ||  Etc(***)\n");}

		for(ProductInfo pi : pList) {

			num+=homeinterface.MallProductList(pi, categoryNum);

			if(num%3==0&&num>0) {
				System.out.println("\n"); //한줄에 5개씩만 출력
			}
		}


		System.out.print("\n\n1.다른메뉴 더보기 || 2.상품담기 >>> ");
		int n = sc.nextInt();

		if(n==1) {
			num=0; //다른메뉴 더보면 초기화해서 줄 맞춤
			System.out.print("[0.All || 1.Coffee || 2.Ade || 3.Desert || 4.Etc] >>> ");
			n = sc.nextInt();
			MallProductListView(n);
		}
		else {
			System.out.println("담을 상품을 입력하세요.");
		}






	}

}