package Main;

import java.io.*;
import Interface.*;
import Info.*;
import java.util.*;


public class User {
	private Data data;
	private List<UserInfo> uList;// = data.getUlist(); //회원정보 List
	private HashMap<String,UserInfo> uMap;// = data.getUmap(); //회원정보 Map
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
	}

	//회원가입
	public void SignUp() {

		try {
			System.out.println("회원가입 : " + data.getUlist());
	        System.out.println("=============== 회원가입 ===============");
			System.out.println("123 : " + data.getUlist());
	        
	        int cno = uList.size() == 0 ? 1 : uList.get(uList.size() - 1).getuNo() + 1;

	        System.out.print("> 아이디 : ");
	        String uid = br.readLine(); // 사용자로부터 아이디 입력 받기
	        
	        while (uMap.containsKey(uid)) { // 아이디 중복 체크
	            System.out.println("\n*** 동일한 아이디가 존재합니다. 다시 입력하세요. ('stop' 입력시 나가기)***\n");
	            System.out.print("> 아이디 : ");
	            uid = br.readLine();
	            if (uid.equals("stop")) {
	                return;
	            }
	        }

	        System.out.print("> 비밀번호 : ");
	        String upw = br.readLine(); // 사용자로부터 비밀번호 입력 받기

	        System.out.print("> 이름 : ");
	        String uname = br.readLine(); // 사용자로부터 이름 입력 받기

	        int ucount = 0;
	        String ugrade = "level0";

	        System.out.print("회원유형[1.Admin||2.Member] : ");
	        int n = Integer.parseInt(br.readLine()); // 사용자로부터 회원 유형 입력 받기
	        while (n != 1 && n != 2) { // 유효한 회원 유형인지 확인
	            System.out.println("\n잘못 입력되었습니다. 다시 입력해주세요.");
	            System.out.print("회원유형[1.Admin||2.Member] : ");
	            n = Integer.parseInt(br.readLine());
	        }
	        String ucategory = n == 1 ? "Admin" : "Member";

			UserInfo userinfo = new UserInfo(cno, uid, upw, uname, ucount, ugrade, ucategory);

			uList.add(userinfo);//처음에 파일에 저장된 값을 저장한 리스트에 추가로 저장
			uMap.put(uid,userinfo); //추가로 저장된 값을 저장

			data.setUmap(uMap); //data에도 똑같이 업로드
			data.setUlist(uList); //data에도 똑같이 업로드

	        String info = cno + "," + uid + "," + upw + "," + uname + "," + ucount + "," + ugrade + "," + ucategory;
			System.out.println("회원가입 : " + info);
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
				System.out.print("> 아이디 : ");
				uid = br.readLine();
				System.out.print("\n\n> 비밀번호 : ");
				upw = br.readLine();

				userinterface = new UserMethod(data);
				if(userinterface.Login(uid,upw)) {break;} //아이디 비민번호가 맞으면 true 반복문 종료
			}

			System.out.println("로그인 성공");
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