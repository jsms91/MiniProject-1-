package Main;

import Info.*;
import Interface.*;
import java.util.*;
import java.io.*;

public class UserManager {
	
    Data data = new Data();
    UserInterface userinterface;// = new UserMethod(data);

    private List<UserInfo> uList; // = data.getUlist(); //회원정보
    private List<ProductInfo> pList; //= data.getPlist(); //상품정보

    private HashMap<String,UserInfo> uMap; // = data.getUmap(); //회원아이디(key), 회원정보(value)
    private HashMap<String,ProductInfo> pMap; //= data.getPmap(); //상품이름(key), 상품정보(value)

    private String LoginId;// = data.getLoginId(); //user에서 로그인한 회원의 정보
    
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Scanner sc = new Scanner(System.in);
    
    public UserManager(Data data) {
    	this.data = data;
    	this.uList = data.getUlist();
		this.uMap = data.getUmap();
		this.pList = data.getPlist();
		this.pMap = data.getPmap();
		this.LoginId = data.getLoginId();
    }

    public void UserManagerView() {
        System.out.println("\n=============== 회원관리 페이지 ===============\n");
        while(true) {
            System.out.println("1. 회원목록 || 2. 회원검색  || 0. 뒤로가기\n");
            System.out.print("* 원하는 메뉴를 선택하세요. >>> ");
            int n = sc.nextInt();

            switch (n) {
                case 1:
                    System.out.println("[회원목록]");
                    UserInfoList(uList);//회원목록으로 이동
                    break;
                case 2:
                    System.out.println("[회원검색]");
                    UserSearch();
                    break;
                case 0:
                    Manager manager = new Manager(data);
                    manager.ManagerView();
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
            userinterface.UserList(userinfo1);
        }

        System.out.println("\n\n");
    }

    //회원검색
    public void UserSearch() {
        try {
            System.out.println("\n=============== 회원 찾기 ===============\n");
            System.out.print("* 회원 아이디 : ");
            String userid = br.readLine();

            while(!uMap.containsKey(userid)) {
                System.out.println(">>> 찾으려고 하는 아이디가 없습니다.<<< \n");
                System.out.print(">>> 아이디를 다시 입력하세요.('stop' 입력시 종료) <<< ");
                userid = br.readLine();
                if(userid.equals("stop")) {return;}
            }

            System.out.printf("\n%-12s||%-12s||%-12s||%-12s||%-12s||%-12s||%-12s%n",
                    "회원번호","아이디","패스워드","이름","누적구매량","회원등급","회원분류\n");

            userinterface = new UserMethod(data);
            userinterface.UserList(uMap.get(userid));

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}