package Main;

import java.util.*;
import Info.*;
import Interface.*;

public class Main{

	public static Data Setup () {

		Data data = new Data();
		Interface uinterface = new Method();
		UserInterface userinterface = new UserMethod(data);
		ManagerInterface managerinterface = new ManagerMethod(data);

		data.setUserfilename(uinterface.File("UserInfo.txt"));//회원정보파일생성
		data.setProductfilename(uinterface.File("ManagerInfo.txt")); //관리자 파일생성(나중에 상품파일로 이름 변경)

		userinterface.UserInfoReader(data.getUserfilename());
		managerinterface.ProductInfoReader(data.getProductfilename());

		return data;
		//일단 여기까지는 파일생성, 파일이름.
	}
	public static void main(String[] args) {

		Data data = new Data();
		data = Setup();//처음 시작전에 설정해두기

		User user = new User(data);
		Manager manager = new Manager(data);
		Home home = new Home(data);

		Scanner sc = new Scanner(System.in);

		while(true) {

			System.out.println("\n=========================\n");
			System.out.println("1. 회원 가입\n");
			System.out.println("2. 로 그 인\n");
			System.out.println("0. 나 가 기\n");
			System.out.print("원하는 메뉴를 선택하세요. >>> ");

			int number = sc.nextInt();

			switch(number) {

				case 1 : //회원가입으로 이동
					System.out.println("\n회원가입을 선택\n");
					user.SignUp();
					break;

				case 2 : //로그인으로 이동
					System.out.println("로그인");
					user.SignIn();
					break;

				case 0 : // 프로그램 종료
					System.out.println("종 료");
					System.exit(0);

				default :
					System.out.println("\n>>>>> 잘못 입력했습니다.");
					break;
			}

		}

	}

}