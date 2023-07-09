package Main;

import Info.*;
import Interface.*;
import java.io.*;


public class User {
	private Data data;
	private Interface uinterface = new Method(); // UserMethod 클래스를 구현한 객체를 UserInterface 타입으로 참조하는 userinterface 변수 생성
	private UserInterface userinterface; //userinterface변수를 선언 아래서 메소드 객체를 참조할때 사용
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //BufferedReader를 사용해서 아래서 문자열을 입력받음

	public User(Data data) {
		this.data=data;
	} //기본 생성자에서 앞에서 받은 data값을 User클래스에서 선언한 data변수에 저장해서 User클래스에서 사용할수 있슴

	//회원가입
	public void SignUp() {

		try {

	        System.out.println("=============== 회원가입 ===============");
	        
	        int uNo = data.getUlist().size() == 0 ? 1 : data.getUlist().get(data.getUlist().size() - 1).getuNo() + 1; //고객번호(기존에 회원정보가 없으면 1, 있으면 기존에 고객번호 +1)

	        System.out.print("* 아이디 : ");
	        String uId = br.readLine(); // 사용자로부터 아이디 입력 받기
	        
	        while (data.getUmap().containsKey(uId) || data.getOdmap().containsKey(uId) ) { // 아이디 중복 체크(기존에 주문내역에 있는 아이디여도 가입x, 주문할때 문제발생)
	            System.out.println("\n>>> 동일한 아이디가 존재합니다. 다시 입력하세요. ('stop' 입력시 나가기) <<<\n");
	            System.out.print("* 아이디 : ");
	            uId = br.readLine();
	            if (uId.equals("stop")) {
	                return;
	            }
	        }

	        System.out.print("* 비밀번호 : ");
	        String uPw = br.readLine(); // 사용자로부터 비밀번호 입력 받기

	        System.out.print("* 이름 : ");
	        String uName = br.readLine(); // 사용자로부터 이름 입력 받기

	        int uCount = 0; //구매회수
	        String uGrade = "level_0"; //고객등급은 처음에 level0으로 시작

	        System.out.print("* 회원유형[1.Admin||2.Member] : ");
	        int n = Integer.parseInt(br.readLine()); // 사용자로부터 회원 유형 입력 받기
	        while (n != 1 && n != 2) { // 유효한 회원 유형인지 확인
	            System.out.println("\n>>> 잘못 입력되었습니다. 다시 입력해주세요. <<<");
	            System.out.print("* 회원유형[1.Admin||2.Member] : ");
	            n = Integer.parseInt(br.readLine());
	        }
	        String ucategory = n == 1 ? "Admin" : "Member"; //회원분류(관리자 | 일반회원)

			UserInfo userinfo = new UserInfo(uNo, uId, uPw, uName, uCount, uGrade, ucategory); //입력한 회원정보를 생성한 UserInfo객체 변수 userinfo에 할당

			data.getUlist().add(userinfo);//리스트에 회원정보 추가
			data.getUmap().put(uId,userinfo); //맵에 회원정보 추가

			data.setUmap(data.getUmap()); //data객체의 uMap 속성에 추가된 uMap으로 설정
			data.setUlist(data.getUlist()); //data객체의 uList 속성에 추가된 uList으로 설정

	        String info = uNo + "," + uId + "," + uPw + "," + uName + "," + uCount + "," + uGrade + "," + ucategory;
			//System.out.println("회원가입 : " + info);
			uinterface.Insert(info, data.getUserfilename()); // 파일에 회원 정보 저장

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
				else {
					System.out.print("stop입력시 종료(계속진행은 아무거나 입력) : ");
					if(br.readLine().equals("stop")) {
						System.exit(0);
					}
				}
			}

			System.out.println(">>>로그인 성공<<<\n");
			data.setLoginId(uid); //로그인한 회원의 아이디를 저장

			if(data.getUmap().get(uid).getuCategory().equals("Admin")) {
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