package Main;

import Info.*;
import Interface.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class Home {

	private Data data = new Data();
	private HomeInterface homeinterface;

	Scanner sc = new Scanner(System.in);
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public Home(Data data) {
		this.data = data;
	}

	public void HomeView() {

		System.out.println("\n=============== CAFE ===============\n");
		System.out.println("1.상품목록 || 2.장바구니 || 3.주문 || 4.회원정보 || 5.로그아웃");
		System.out.print("* 원하는 메뉴를 선택하세요. >>> ");
		int n = sc.nextInt();

		switch (n) {
			case 1 :
				HomeMenuListView(); //1은 처음에 상품목록에 전체상품을 보여주고 이후 2는 커피 3은 에이디 이런식..
				break;
			case 2 :
				BasketList(); //장바구니 이동
				break;
			case 3 :
				BasketList(); //장바구니로 이동해서 주문
				break;
			case 4 :
				MyInfoList();
				break;
			case 5 :
				System.out.println("로그아웃");
				data.setLoginId("");
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
		homeinterface.MenuList(data.getPlist(),categoryName); //상품리스트 출력

		//다른메뉴더보기 or 뒤로가기가 아닌이상 상품담기 실행
		try {
			System.out.println("구매할 상품을 입력하세요.");
			System.out.print("* 상품명 : ");
			String bpName = br.readLine();

			while (!data.getPmap().containsKey(bpName)) {
				System.out.println(">>> 담으려는 상품이 없습니다. 다시입력해주세요. <<<");
				System.out.print("* 상품명 : ");
				bpName = br.readLine(); //상품명
			}

			int check = data.getBmap().containsKey(bpName) ? 1 : 0; //장바구니에 동일 상품이 존재(1), 없으면(0);
			int pStack = data.getPmap().get(bpName).getpStack(); //담으려는 상품의 재고
			int stack = check == 1 ? pStack - data.getBmap().get(bpName).getbAmount() : pStack; //장바구니에 동일 상품이 존재:구매가능수량은 상품수량-담은수량

			System.out.print(String.format("* 상품개수[재고량: %d개] : ", stack));
			int bpQuantity = Integer.parseInt(br.readLine()); //구매수량

			while (bpQuantity > stack) { //구매수량 > 재고
				System.out.println(">>> 구매량이 재고수량보다 많습니다.(다시 입력해주세요.) <<<");
				System.out.print("* 상품개수 : ");
				bpQuantity = Integer.parseInt(br.readLine());
			}

			bpQuantity += check == 1 ? data.getBmap().get(bpName).getbAmount() : 0; //기존 장바구니에 있으면 더하고 없으면 새로저장
			int price = data.getPmap().get(bpName).getpPrice(); //상품가격
			int no = data.getPmap().get(bpName).getpNo(); //담으려는 상품번호

			BasketInfo basketInfo = new BasketInfo(no, bpName, bpQuantity, bpQuantity * price); //해당 상품의 최종 장바구니 정보

			homeinterface = new HomeMethod(data);
			homeinterface.BasketAdd(basketInfo, check);//장바구니에 추가 or 기존 정보 업로드

			System.out.println("추가되었는지 확인-->" + data.getBlist().size() + " ||| " + data.getBmap().size());//제거할거

			BasketList(); //장바구니로 이동
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	//2.장바구니
	public void BasketList() {
		System.out.println("\n=============== 장바구니 ===============\n");
		System.out.println("\n     상품명     ||     수량     ||     가격      \n");
		int TotalPrice = 0; //장바구니에 담긴 상품에 총가격(초기값은 0으로 설정 목록을 불러올때 마다 +해서 최종가격을 구함)
		for(BasketInfo bi : data.getBlist()) {
			String name = bi.getbName();
			int amount = bi.getbAmount();
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
				if(data.getBlist().size()==0) {
					System.out.println("\n>>> 장바구니에 상품이 없습니다. 상품을 먼저 담아주세요. <<<\n");
					HomeMenuListView();
				}
				int ono = data.getOlist().size() == 0 ? 1 : data.getOlist().get(data.getOlist().size()-1).getoNo()+1; //주문번호 설정
				homeinterface.Order(ono); //주문
				HomeView();
				break;
			case 2:
				try {
					System.out.print("* 삭제할 상품 입력 : ");
					String bName = br.readLine();
					if(!data.getBmap().containsKey(bName)) {
						System.out.println("[Error!!]");
						BasketList();//장바구니에 없는 상품 검색시 장바구니 처음화면으로 이동
					}
					else {
						data.getBlist().remove(data.getBmap().get(bName));
						data.getBmap().remove(bName);
						BasketList();
					}
					HomeView();
				}catch(Exception e ) {
					e.printStackTrace();
				}
				break;
			case 0:
//				System.out.println("뒤로가기");
				HomeView();
				break;
			default:
				System.out.println("[Error!!]");
				System.exit(0); //에러는 그냥 종료
		}
	}

	//4.회원정보 -> 회원정보[1.수정 | 2.탈퇴(탈퇴후 파일에서 삭제 -> 초기화면으로이동)
	public void MyInfoList() {

		int uNo = data.getUmap().get(data.getLoginId()).getuNo();
		String uName = data.getUmap().get(data.getLoginId()).getuName();
		int uCount = data.getUmap().get(data.getLoginId()).getuCount();
		String uGrade = data.getUmap().get(data.getLoginId()).getuGrade();
		String uCategroy = data.getUmap().get(data.getLoginId()).getuCategory();

		System.out.println("\n=============== 회원정보 ===============\n");
		System.out.println("회원번호 | 회원이름 | 구매횟수 | 회원등급 | 회원분류");
		System.out.println(String.format("%-8d %-10s %-7d %-10s %-10s\n",uNo,uName,uCount,uGrade,uCategroy));

		OrderListView(); //주문내역
	}

	//4-1 주문내역
	public void OrderListView() {

		System.out.println("=============== 주문내역 ===============\n");

		if(!data.getOdmap().containsKey(data.getLoginId())) {
			System.out.println(">>>>>>> 주문내역이 없습니다. <<<<<<<");
			System.out.println("\n[2.회원정보수정 | 3.탈퇴 | 0. 뒤로가기");
			System.out.print("* 원하는 메뉴를 선택하세요.[번호로 입력] >>> ");
		}
		else {
			System.out.println(String.format("%-8s | %-15s | %-7s | %s ", "주문번호", "상품명", "결제금액", "주문일"));
			for (int ono : data.getOdmap().get(data.getLoginId())) {
				String oTitle = data.getOmap().get(ono).getoTitle();
				int oPrice = data.getOmap().get(ono).getoPrice();
				String oDay = data.getOmap().get(ono).getoDay();
				System.out.println(String.format("%-10d | %-15s | %-10d | %s", ono, oTitle, oPrice, oDay));
			}

			System.out.println("\n[1.주문상세내역 | 2.회원정보수정 | 3.탈퇴 | 0. 뒤로가기");
			System.out.print("* 원하는 메뉴를 선택하세요.[번호로 입력] >>> ");

		}

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
				homeinterface.UserModifyDelete(data.getUmap().get(data.getLoginId()), "D");
				data.setLoginId("");
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

	//4-3 주문상세내역
	public void OrderDetailView() {

		System.out.println(String.format("%-10s | %-15s | %-7s | %-7s | %s ", "주문상세번호", "상품명", "상품가격", "상품개수","주문번호"));
		for(OrderDetail od : data.getOdlist()) {
			if(od.getOdId().equals(data.getLoginId())) {
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
			while(!uPw.equals(data.getUmap().get(data.getLoginId()).getuPw())) {
				System.out.println("비밀번호를 다시입력해주세요.");
				System.out.print("* 비밀번호 : " );
				uPw = br.readLine();
			}

			System.out.println(String.format("* 아이디 : %s ", data.getLoginId()));
			System.out.print("* 비밀번호 : ");
			uPw = br.readLine();
			System.out.print(String.format("* 회원명[%s] :  ", data.getUmap().get(data.getLoginId()).getuName()));
			String uName = br.readLine();

			int uNo = data.getUmap().get(data.getLoginId()).getuNo();
			int uCount = data.getUmap().get(data.getLoginId()).getuCount();
			String uGrade = data.getUmap().get(data.getLoginId()).getuGrade();
			String uCategory = data.getUmap().get(data.getLoginId()).getuCategory();

			UserInfo userInfo = new UserInfo(uNo,data.getLoginId(),uPw,uName,uCount,uGrade,uCategory);

			homeinterface = new HomeMethod(data);
			homeinterface.UserModifyDelete(userInfo,"M");//수정은 M,삭제는 D
			HomeView();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}


}