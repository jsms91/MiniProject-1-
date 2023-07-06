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
		HomeInterface homeinterface = new HomeMethod(data);

		data.setUserfilename(uinterface.File("UserInfo.txt"));//회원정보 파일생성
		data.setProductfilename(uinterface.File("ManagerInfo.txt")); //관리자 파일생성(나중에 상품파일로 이름 변경)
		data.setOrderfilename(uinterface.File("OrderInfo.txt")); //주문내역 파일생성
		data.setOrderdetailfilename(uinterface.File("OrderDetail.txt")); //주문상세내역 파일생성

		userinterface.UserInfoReader(data.getUserfilename());
		managerinterface.ProductInfoReader(data.getProductfilename());
		homeinterface.OrderInfoReader(data.getOrderfilename());
		homeinterface.OrderDetailReader(data.getOrderdetailfilename());

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

		System.out.println("로그인아이디 체크 : " + data.LoginId);

		System.out.println("\n=========================\n");
		System.out.println("1. 회원 가입\n");
		System.out.println("2. 로 그 인\n");
		System.out.println("0. 나 가 기\n");
		System.out.print("* 원하는 메뉴를 선택하세요. >>> ");

		int number = sc.nextInt();
		//프로젝트 시작
		switch (number) {

			case 1: //회원가입으로 이동
				System.out.println("[회원가입]\n");
				user.SignUp();
				user.SignIn();
				break;

			case 2: //로그인으로 이동
				System.out.println("[로그인]");
				user.SignIn();
				break;

			case 0: // 프로그램 종료
				System.out.println("[종 료]");
				System.exit(0);
				break;

			default:
				System.out.println("[Error!!]");
				System.exit(0);
				break;
		}

	}

}