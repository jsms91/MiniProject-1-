package Main;

import java.io.*;
import Interface.*;
import Info.*;
import java.util.*;


public class User {
	private Data data;
	private List<UserInfo> uList;// = data.getUlist(); //회원정보 List
	private HashMap<String,UserInfo> uMap;// = data.getUmap(); //회원정보 Map
	private HashMap<String, HashSet<Integer>> odMap; //주문상세내역(아이디중복체크에 필요)
	private String fileName;// = data.getUserfilename(); //회원정보 파일이름
	private String LoginId;// = data.getLoginId(); //로그인중인 아이디
	
	private Interface uinterface = new Method(); // UserMethod 클래스를 구현한 객체를 UserInterface 타입으로 참조하는 userinterface 변수 생성
	private UserInterface userinterface;// = new UserMethod(data);
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public User(Data data) {
		this.data=data;
		this.uList = data.getUlist();
		this.uMap = data.getUmap();
		this.fileName = data.getUserfilename();
		this.LoginId = data.getLoginId();
		this.odMap = data.getOdmap();
	}

	//회원가입
	public void SignUp() {

		try {

	        System.out.println("=============== 회원가입 ===============");
	        
	        int uno = uList.size() == 0 ? 1 : uList.get(uList.size() - 1).getuNo() + 1; //고객번호(기존에 회원정보가 없으면 1, 있으면 기존에 고객번호 +1)

	        System.out.print("* 아이디 : ");
	        String uid = br.readLine(); // 사용자로부터 아이디 입력 받기
	        
	        while (uMap.containsKey(uid) || odMap.containsKey(uid) ) { // 아이디 중복 체크(기존에 주문내역에 있는 아이디이면 가입x)
	            System.out.println("\n>>> 동일한 아이디가 존재합니다. 다시 입력하세요. ('stop' 입력시 나가기) <<<\n");
	            System.out.print("* 아이디 : ");
	            uid = br.readLine();
	            if (uid.equals("stop")) {
	                return;
	            }
	        }

	        System.out.print("* 비밀번호 : ");
	        String upw = br.readLine(); // 사용자로부터 비밀번호 입력 받기

	        System.out.print("* 이름 : ");
	        String uname = br.readLine(); // 사용자로부터 이름 입력 받기

	        int ucount = 0; //구매회수
	        String ugrade = "level_0"; //고객등급은 처음에 level0으로 시작

	        System.out.print("* 회원유형[1.Admin||2.Member] : ");
	        int n = Integer.parseInt(br.readLine()); // 사용자로부터 회원 유형 입력 받기
	        while (n != 1 && n != 2) { // 유효한 회원 유형인지 확인
	            System.out.println("\n>>> 잘못 입력되었습니다. 다시 입력해주세요. <<<");
	            System.out.print("* 회원유형[1.Admin||2.Member] : ");
	            n = Integer.parseInt(br.readLine());
	        }
	        String ucategory = n == 1 ? "Admin" : "Member"; //회원분류(관리자 | 일반회원)

			UserInfo userinfo = new UserInfo(uno, uid, upw, uname, ucount, ugrade, ucategory); //입력한 회원정보를 생성한 UserInfo객체 변수 userinfo에 할당

			uList.add(userinfo);//리스트에 회원정보 추가
			uMap.put(uid,userinfo); //맵에 회원정보 추가

			data.setUmap(uMap); //data객체의 uMap 속성에 추가된 uMap으로 설정
			data.setUlist(uList); //data객체의 uList 속성에 추가된 uList으로 설정

	        String info = uno + "," + uid + "," + upw + "," + uname + "," + ucount + "," + ugrade + "," + ucategory;
			//System.out.println("회원가입 : " + info);
			uinterface.Insert(info, fileName); // 파일에 회원 정보 저장

		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	//로그인
	public void SignIn()  {
		System.out.println("=============== 로그인 ===============");
		String uid;
		String upw;

		try {

			while(true) {
				System.out.print("* 아이디 : ");
				uid = br.readLine();
				System.out.print("\n* 비밀번호 : ");
				upw = br.readLine();

				userinterface = new UserMethod(data);
				if(userinterface.Login(uid,upw)) {break;} //아이디 비민번호가 맞으면 true 반복문 종료
			}

			System.out.println(">>>로그인 성공<<<\n");
			data.setLoginId(uid); //로그인한 회원의 아이디를 저장

			if(uMap.get(uid).getuCategory().equals("Admin")) {
				Manager manager = new Manager(data);
				manager.ManagerView();
			}
			else {
				Home home = new Home(data);
				home.HomeView();
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}