package Main;

import Info.*;
import Interface.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.*;


public class Home {

	Data data = new Data();
	BasketInfo basketInfo = new BasketInfo();
	private HomeInterface homeinterface;

	private String LoginId; //로그인상태 아이디
	private String OrderFileName; //주문내역 파일이름
	private String OrderDetailFileName; //주문상세내역 파일이름
	
	private List<UserInfo> uList; //회원정보 리스트
	private List<ProductInfo> pList; //상품정보 리스트
	private List<BasketInfo> bList; //장바구니 리스트
	private List<OrderInfo> oList; //주문내역 리스트
	private List<OrderDetail> odList; //주문상세내역 리스트

	// 주문내역 리스트
	private HashMap<String, UserInfo> uMap; //회원정보 맵
	private HashMap<String, ProductInfo> pMap; //상품정보 맵
	private HashMap<String, BasketInfo> bMap; //장바구니 맵
	private HashMap<Integer, OrderInfo> oMap; //주문내역 맵
	private HashMap<String, HashSet<Integer>> odMap; //주문상세내역 맵

	Scanner sc = new Scanner(System.in);
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public Home(Data data) {
		this.data = data;
		this.uList = data.getUlist();
		this.pList = data.getPlist();
		this.bList = data.getBlist();
		this.uMap = data.getUmap();
		this.pMap = data.getPmap();
		this.bMap = data.getBmap();
		this.oList = data.getOlist();
		this.oMap = data.getOmap();
		this.odList = data.getOdlist();
		this.odMap = data.getOdmap();
		this.LoginId = data.getLoginId();
		this.OrderFileName = data.getOrderfilename();
		this.OrderDetailFileName = data.getOrderdetailfilename();
	}

	public void HomeView() {


		System.out.println("\n\n\n장바구니에 들어있는 삼품의 개수 : " + bList.size()+"\n\n\n");


		System.out.println("\n=============== CAFE ===============\n");
		System.out.println("1.상품목록 || 2.장바구니 || 3.주문 || 4.회원정보 || 5.로그아웃");
		System.out.print("* 원하는 메뉴를 선택하세요. >>> ");
		int n = sc.nextInt();

		switch (n) {
			case 1 :
				System.out.println("상품목록");
				HomeMenuListView(); //1은 처음에 상품목록에 전체상품을 보여주고 이후 2는 커피 3은 에이디 이런식..
				break;
			case 2 :
				System.out.println("장바구니");
				BasketList();
				break;
			case 3 :
				System.out.println("주문");
				BasketList(); //장바구니로 이동해서 주문
				break;
			case 4 :
				System.out.println("회원정보");
				MyInfoList();
				break;
			case 5 :
				System.out.println("로그아웃");
				LoginId = "";
				data.setLoginId(LoginId);
				Main.main(new String[] {}); //메인페이지 이동
				break;
			default :
				System.out.println("[Error!!]");
				return;
		}
	}

	//1.상품목록
	public void HomeMenuListView() {

		System.out.println("\n=============== 상품 목록 ===============\n");

		String categoryName = "all"; // 카테고리별로 리스트 출력(처음에는 All로 설정)

		homeinterface = new HomeMethod(data);
		homeinterface.MenuList(pList,categoryName); //상품리스트 출력

		//다른메뉴더보기 or 뒤로가기가 아닌이상 상품담기 실행
		System.out.println("현재 장바구니 크기 : " + bList.size());//제거할거
		try {
			System.out.println("구매할 상품을 입력하세요.");
			System.out.print("* 상품명 : ");
			String bpName = br.readLine();

			while (!pMap.containsKey(bpName)) {
				System.out.println(">>> 담으려는 상품이 없습니다. 다시입력해주세요. <<<");
				System.out.print("* 상품명 : ");
				bpName = br.readLine(); //상품명
			}

			int check = bMap.containsKey(bpName) ? 1 : 0; //장바구니에 동일 상품이 존재(1), 없으면(0);
			int pStack = pMap.get(bpName).getpStack(); //담으려는 상품의 재고
			int stack = check == 1 ? pStack - bMap.get(bpName).getaMount() : pStack; //장바구니에 동일 상품이 존재:구매가능수량은 상품수량-담은수량

			System.out.print(String.format("* 상품개수[재고량: %d개] : ", stack));
			int bpQuantity = Integer.parseInt(br.readLine()); //구매수량

			while (bpQuantity > stack) { //구매수량 > 재고
				System.out.println(">>> 구매량이 재고수량보다 많습니다.(다시 입력해주세요.) <<<");
				System.out.print("* 상품개수 : ");
				bpQuantity = Integer.parseInt(br.readLine());
			}

			bpQuantity += check == 1 ? bMap.get(bpName).getaMount() : 0; //기존 장바구니에 있으면 더하고 없으면 새로저장
			int price = pMap.get(bpName).getpPrice(); //상품가격
			int no = pMap.get(bpName).getpNo(); //담으려는 상품번호

			basketInfo = new BasketInfo(no, bpName, bpQuantity, bpQuantity * price); //해당 상품의 최종 장바구니 정보

			homeinterface = new HomeMethod(data);
			homeinterface.BasketAdd(basketInfo, check);//장바구니에 추가 or 기존 정보 업로드

			System.out.println("추가되었는지 확인-->" + bList.size() + " ||| " + bMap.size());//제거할거

			BasketList(); //장바구니로 이동
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	//2.장바구니
	public void BasketList() {
		System.out.println("\n=============== 장바구니 ===============\n");
		System.out.println("\n     상품명     ||     수량     ||     가격      \n");
		int TotalPrice = 0;
		for(BasketInfo bi : bList) {
			String name = bi.getbName();
			int amount = bi.getaMount();
			int price = bi.getbPrice();
			TotalPrice += price;
			System.out.println(String.format("%10s %10d %10d\n", name,amount, price));
		}

		System.out.println("총 주문 가격 : " + TotalPrice + "\n\n");
		System.out.println("1.주문 || 2.삭제 || 0.뒤로가기");
		System.out.print("* 원하는 메뉴를 선택하세요. >>> ");
		int menu = sc.nextInt();

		HomeInterface homeinterface = new HomeMethod(data);

		switch (menu) {
			case 1:
				if(bList.size()==0) {
					System.out.println("\n>>> 장바구니에 상품이 없습니다. 상품을 먼저 담아주세요. <<<\n");
					HomeMenuListView();
				}
				System.out.println("주문으로 이동");
				int ono = oList.size() == 0 ? 1 : oList.get(oList.size()-1).getoNo()+1;
				homeinterface.Order(ono);
				HomeView();
				break;
			case 2:
				try {
					System.out.print("* 삭제할 상품 입력 : ");
					String bName = br.readLine();
					if(!bMap.containsKey(bName)) {
						System.out.println("[Error!!]");
						BasketList();//장바구니에 없는 상품 검색시 장바구니 처음화면으로 이동
					}
					else {
						bList.remove(bMap.get(bName));
						bMap.remove(bName);
						BasketList();
					}
					HomeView();
				}catch(Exception e ) {
					e.printStackTrace();
				}
				break;
			case 0:
				System.out.println("뒤로가기");
				HomeView();
				break;
			default:
				System.out.println("[Error!!]");
				System.exit(0); //에러는 그냥 종료
		}
	}

	// TODO: 2023-07-05 회원정보수정(아직 미완성)
	//4.회원정보 -> 회원정보[1.수정 | 2.탈퇴(탈퇴후 파일에서 삭제 -> 초기화면으로이동)
	public void MyInfoList() {

		int uNo = uMap.get(LoginId).getuNo();
		String uName = uMap.get(LoginId).getuName();
		int uCount = uMap.get(LoginId).getuCount();
		String uGrade = uMap.get(LoginId).getuGrade();
		String uCategroy = uMap.get(LoginId).getuCategory();

		System.out.println("\n=============== 회원정보 ===============\n");
		System.out.println("회원번호 | 회원이름 | 구매횟수 | 회원등급 | 회원분류");
		System.out.println(String.format("%-8d %-10s %-7d %-10s %-10s\n",uNo,uName,uCount,uGrade,uCategroy));

		OrderListView(); //주문내역
	}

	//4-1 주문내역
	public void OrderListView() {

		System.out.println("=============== 주문내역 ===============\n");

		if(!odMap.containsKey(LoginId)) {
			System.out.println(">>>>>>> 주문내역이 없습니다. <<<<<<<");
			System.out.println("\n[2.회원정보수정 | 3.탈퇴 | 0. 뒤로가기");
			System.out.print("* 원하는 메뉴를 선택하세요.[번호로 입력] >>> ");
		}
		else {
			System.out.println(String.format("%-8s | %-15s | %-7s | %s ", "주문번호", "상품명", "결제금액", "주문일"));
			for (int ono : odMap.get(LoginId)) {
				String oTitle = oMap.get(ono).getoTitle();
				int oPrice = oMap.get(ono).getoPrice();
				String oDay = oMap.get(ono).getoDay();
				System.out.println(String.format("%-10d | %-15s | %-10d | %s", ono, oTitle, oPrice, oDay));
			}

		}

		System.out.println("\n[1.주문상세내역 | 2.회원정보수정 | 3.탈퇴 | 0. 뒤로가기");
		System.out.print("* 원하는 메뉴를 선택하세요.[번호로 입력] >>> ");

		int menu = sc.nextInt();

		switch (menu) {
			case 1:
				System.out.println("주문상세내역");
				OrderDetailView();
				break;
			case 2: //회원정보수정
				UserModify();
				break;
			case 3: //회원정보탈퇴(삭제)
				homeinterface.UserModifyDelete(uMap.get(LoginId), "D");
				LoginId = "";
				data.setLoginId(LoginId);
				Main.main(new String[]{}); //메인페이지 이동
				break;
			case 0 : //뒤로가기
				HomeView();
				break;
			default:
				System.out.println("[Error!!]");
				HomeView();
				break;
		}
	}
	// TODO: 2023-07-06 주문상세내역
	//4-3 주문상세내역
	public void OrderDetailView() {

		System.out.println(String.format("%-10s | %-15s | %-7s | %-7s | %s ", "주문상세번호", "상품명", "상품가격", "상품개수","주문번호"));
		for(OrderDetail od : odList) {
			if(od.getOdId().equals(LoginId)) {
				int odNo = od.getOdNo();
				String odName = od.getOdName();
				int odPrice = od.getOdPrice();
				int odAmount = od.getOdAmount();
				int oNo = od.getoNo();
				System.out.println(String.format("%-10d | %-15s | %-10d | %-7d | %d", odNo, odName, odPrice, odAmount,oNo));
			}
		}

		System.out.print("[0. 뒤로가기] >>>");
		int n = sc.nextInt(); //형식상 입력만 받기
		MyInfoList(); //어떤값을 입력받아도 회원정보로 이동

	}


	//4-3 회원정보_수정(비밀번호, 이름만 변경가능)
	public void UserModify() {

		System.out.println("\n=============== 회원정보 수정 ===============\n");

		try {

			System.out.print("* 비밀번호 : " );
			String uPw = br.readLine();
			while(!uPw.equals(uMap.get(LoginId).getuPw())) {
				System.out.println("비밀번호를 다시입력해주세요.");
				System.out.print("* 비밀번호 : " );
				uPw = br.readLine();
			}

			System.out.println(String.format("* 아이디 : %s ", LoginId));
			System.out.print("* 비밀번호 : ");
			uPw = br.readLine();
			System.out.print(String.format("* 회원명[%s] :  ", uMap.get(LoginId).getuName()));
			String uName = br.readLine();

			int uNo = uMap.get(LoginId).getuNo();
			int uCount = uMap.get(LoginId).getuCount();
			String uGrade = uMap.get(LoginId).getuGrade();
			String uCategory = uMap.get(LoginId).getuCategory();

			UserInfo userInfo = new UserInfo(uNo,LoginId,uPw,uName,uCount,uGrade,uCategory);

			homeinterface = new HomeMethod(data);
			homeinterface.UserModifyDelete(userInfo,"M");//수정은 M,삭제는 D
			HomeView();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}


}