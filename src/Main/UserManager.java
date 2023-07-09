package Main;

import Info.*;
import Interface.*;
import java.util.*;
import java.io.*;

public class UserManager {
	
    private Data data;
    private UserInterface userinterface;
    
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private Scanner sc = new Scanner(System.in);
    
    public UserManager(Data data) {
    	this.data = data;
    }

    public void UserManagerView() { //관리자 페이지

        System.out.println("\n=============== 회원관리 페이지 ===============\n");

        while(true) {
            System.out.println("1. 회원목록 || 2. 회원검색  || 0. 뒤로가기\n");
            System.out.print("* 원하는 메뉴를 선택하세요. >>> ");
            int n = sc.nextInt();

            switch (n) {
                case 1:
                    UserInfoList(data.getUlist());//회원목록으로 이동
                    break;
                case 2:
                    UserSearch(); //회원검색으로 이동
                    break;
                case 0:
                    Manager manager = new Manager(data);
                    manager.ManagerView(); //처음 관리자 화면으로 이동
                    return;
                default:
                    System.out.println("\n[Error!!]\n");
                    break;
            }
        }
    }

    //회원목록
    public void UserInfoList(List<UserInfo> ulist) {

        System.out.println("\n=============== 회원정보 리스트 ===============\n");
        System.out.printf("%-12s||%-12s||%-12s||%-12s||%-12s||%-12s||%-12s%n",
                "회원번호","아이디","패스워드","이름","누적구매량","회원등급","회원분류\n\n");

        userinterface = new UserMethod(data);
        
        for(UserInfo userinfo1 : ulist) {
            userinterface.UserList(userinfo1); //리스트에 저장된 각 회원정보 객체를 UserList 메소드를 사용해서 출력
        }

        System.out.println("\n\n");
    }

    //회원검색
    public void UserSearch() {
        try {
            System.out.println("\n=============== 회원 찾기 ===============\n");
            System.out.print("* 회원 아이디 : ");
            String userId = br.readLine();

            while(!data.getUmap().containsKey(userId)) { //회원아이디가 키값인 회원정보 맵을 통해 입력받은 값의 회원유무 확인
                System.out.println(">>> 찾으려고 하는 아이디가 없습니다.<<< \n");
                System.out.print(">>> 아이디를 다시 입력하세요.('stop' 입력시 종료) <<< ");
                userId = br.readLine();
                if(userId.equals("stop")) {return;}
            }

            System.out.printf("\n%-12s||%-12s||%-12s||%-12s||%-12s||%-12s||%-12s%n",
                    "회원번호","아이디","패스워드","이름","누적구매량","회원등급","회원분류\n");

            userinterface = new UserMethod(data);
            userinterface.UserList(data.getUmap().get(userId)); //회원아이디 존재시 입력받은 회원아이디에 객체값을 통해 정보를 출력

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}