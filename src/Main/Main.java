package Main;

import Info.Data;
import Interface.*;

import java.util.Scanner;

public class Main{

	public static Data Setup () {
		
		Data data = new Data();

		Interface uinterface = new Method();
		UserInterface userinterface = new UserMethod(data);
		ManagerInterface managerinterface = new ManagerMethod(data);
		HomeInterface homeinterface = new HomeMethod(data);

		//회원,상품,주문&상세내역 파일 생성
		data.setUserfilename(uinterface.File(data.getUserfilename()));
		data.setProductfilename(uinterface.File(data.getProductfilename()));
		data.setOrderfilename(uinterface.File(data.getOrderfilename()));
		data.setOrderdetailfilename(uinterface.File(data.getOrderdetailfilename()));

		//파일에서 읽어온 정보를 list,map에 저장
		userinterface.UserInfoReader(data.getUserfilename());
		managerinterface.ProductInfoReader(data.getProductfilename());
		homeinterface.OrderInfoReader(data.getOrderfilename());
		homeinterface.OrderDetailReader(data.getOrderdetailfilename());

		return data;
	}
	public static void main(String[] args) {

		Data data = new Data();
		data = Setup();

		User user = new User(data);
//		Manager manager = new Manager(data);
//		Home home = new Home(data);

		Scanner sc = new Scanner(System.in);

		System.out.println("\n=========================\n");
		System.out.println("1. 회원 가입\n");
		System.out.println("2. 로 그 인\n");
		System.out.println("0. 나 가 기\n");
		System.out.print("* 원하는 메뉴를 선택하세요. >>> ");

		int number = sc.nextInt();
		switch (number) {

			case 1: //회원가입으로 이동
				user.SignUp();
				user.SignIn();
				break;

			case 2: //로그인으로 이동
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