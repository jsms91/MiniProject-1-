package Main;

import Info.*;
import Interface.*;
import java.io.*;
import java.util.*;


public class ProductManager {
    Scanner sc = new Scanner(System.in);

    Data data = new Data();
    Interface uinterface = new Method(); // UserMethod 클래스를 구현한 객체를 UserInterface 타입으로 참조하는 userinterface 변수 생성
    ProductInfo productinfo = new ProductInfo();
    ManagerInterface managerinterface;// = new ManagerMethod(data);

    private String LoginId;// = data.getLoginId(); //로그인정보
    private List<ProductInfo> pList;// = data.getPlist(); //상품정보 리스트
    private HashMap<String, ProductInfo> pMap;//; //상품 Map(상품명,
    private String fileName;// = data.getProductfilename(); //상품정보 파일이름
    
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public ProductManager(Data data) {
    	this.data = data;
    	this.LoginId = data.getLoginId();
    	this.pList = data.getPlist();
    	this.pMap = data.getPmap();
    	this.fileName = data.getProductfilename();
    	
    }

    public void ProductManagerView() {

        System.out.println("\n=============== 상품관리 페이지 ===============\n");
        while(true) {

            System.out.println("1.상품등록 || 2.상품검색 || 3.상품목록 || 4.상품수정 || 5.상품삭제 || 0.뒤로가기\n");
            System.out.print("* 원하는 메뉴를 선택하세요. >>> ");
            int n = sc.nextInt();

            switch (n) {
                case 1 :
                    System.out.println("[상품등록]");
                    MenuRegister();
                    break;

                case 2 :
                    System.out.println("[상품검색]");
                    MenuSearch();
                    break;

                case 3 :
                    System.out.println("[상품목록]");
                    MenuListView();
                    break;

                case 4 :
                    System.out.println("[상품수정]");
                    MenuModifyView();
                    break;

                case 5 :
                    System.out.println("[상품삭제]");
                    ProductDeleteView();
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

    //상품등록
    public void MenuRegister() {
        try {
        	
            System.out.println("\n=============== 상품 등록 ===============\n");
            
            int pno = pList.size()==0 ? 1 : (pList.get(pList.size()-1).getpNo()+1); //상품번호

            System.out.print("* 상품명 : ");
            String pname = br.readLine();
            
            if(pMap.containsKey(pname)) {
            	System.out.println(" >>> 동일한 상품이 이미 등록되어 있습니다. <<< \n");
            	return;
            }

            System.out.print("* Category [1. coffee | 2. ade | 3.dessert](1~3번만 입력하세요. 다른번호는 etc로 분류) : ");
            int n = sc.nextInt();

            String pcategory = "";
            int pcategorynumber = -1;

            switch(n) {
                case 1 :
                    pcategory = "coffee";
                    pcategorynumber = n;
                    break;
                case 2:
                    pcategory = "ade";
                    pcategorynumber = n;
                    break;
                case 3:
                    pcategory = "dessert";
                    pcategorynumber = n;
                    break;
                default:
                    pcategory = "etc";
                    pcategorynumber = 4;
                    break;
            }

            System.out.print("* 가격 : ");
            int price = Integer.parseInt(br.readLine());

            System.out.print("* 재고 : " );
            int pstack = Integer.parseInt(br.readLine());

            System.out.print("* 상품설명 : ");
            String pdescription = br.readLine();

            productinfo = new ProductInfo(pno,pname,pcategory,pcategorynumber,price,pstack,pdescription);

            pList.add(productinfo);
            pMap.put(pname,productinfo);
            data.setPlist(pList);
            data.setPmap(pMap);

            String pinfo = pno + "," + pname + "," + pcategory  + "," + pcategorynumber + "," + price + "," +pstack + "," + pdescription;
            //System.out.println("상품등록 : " + pinfo);
            uinterface.Insert(pinfo, fileName); // 파일에 상품 정보 저장

            System.out.println("\n[상품등록 완료.]\n");

        } catch(Exception e ){
            e.printStackTrace();
        }
    }

    //상품검색
    public void MenuSearch() {

        try {
            System.out.println("\n=============== 상품 검색 ===============\n");
            System.out.print("* 상품명 : ");
            String pname = br.readLine();

            //System.out.println("맵사이즈 : " + pMap.size());

            while(!pMap.containsKey(pname)) {
                System.out.println(">>> 찾으려고 하는 상품이 없습니다. <<<\n");
                System.out.print("* 상품명을 다시 입력하세요.('stop' 입력시 종료) : ");
                pname = br.readLine();
                if(pname.equals("stop")) {return;}
            }

            System.out.printf("\n%-12s||%-12s||%-12s||%-12s||%-12s||%-50s%n",
                    "상품번호","상품명","카테고리","가격","재고량","상품설명\n");
            managerinterface = new ManagerMethod(data);
            managerinterface.ProductList(pMap.get(pname));

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    //상품 목록
    public void MenuListView() {

        Collections.sort(pList); //상품을 카테고리 번호를 기준으로 오름차순 정렬

        System.out.println("\n=============== 상품 목록 ===============\n");
        System.out.printf("%-12s||%-12s||%-12s||%-12s||%-12s||%-50s%n",
                "상품번호","상품명","카테고리","가격","재고량","상품설명\n");
        managerinterface = new ManagerMethod(data);

        for(ProductInfo pi : pList) {
            managerinterface.ProductList(pi);
        }
        System.out.println("\n\n");
    }

    //상품수정
    public void MenuModifyView() {

        System.out.println("\n=============== 상품 수정 ===============\n");

        try {
            String pname = "";
            while(true) {
                System.out.print("* 수정_상품명 : ");
                pname = br.readLine();
                if(pMap.containsKey(pname)) {
                    break;
                }
                else {
                    System.out.println(">>> 존재하지 않는 상품입니다. 다시 입력해주세요. <<<\n");
                }
            }

            managerinterface = new ManagerMethod(data);
            managerinterface.ProductList(pMap.get(pname)); //수정할 상품의 정보를 불러옴

            System.out.println("각 항목의 수정할 내용을 입력하세요.\n");

            int pno = pMap.get(pname).getpNo();

            System.out.print("* Category [1.coffee | 2.ade | 3.dessert](1~3번만 입력하세요. 다른번호는 etc로 분류)  >>");

            int n = sc.nextInt();

            String pcategory = "";
            int pcategorynumber = -1;

            switch(n) {
                case 1 :
                    pcategory = "coffee";
                    pcategorynumber = n;
                    break;
                case 2:
                    pcategory = "ade";
                    pcategorynumber = n;
                    break;
                case 3:
                    pcategory = "dessert";
                    pcategorynumber = n;
                    break;
                default:
                    pcategory = "etc";
                    pcategorynumber = 4;
                    break;
            }

            System.out.print("* 수정_가격 : ");
            int price = Integer.parseInt(br.readLine());

            System.out.print("* 수정_재고 : " );
            int pstack = Integer.parseInt(br.readLine());

            System.out.print("* 수정_상품설명 : ");
            String pdescription = br.readLine();

            managerinterface.ProductModifyDelete(new ProductInfo(pno,pname,pcategory,pcategorynumber,price,pstack,pdescription),"M");

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //상품삭제
    public void ProductDeleteView() {

        System.out.println("\n=============== 상품 삭제 ===============\n");

        try {
            while(true) {
                System.out.print("* 삭제할 상품명을 입력하세요. >>> ");
                String pname = br.readLine();
                if(!pMap.containsKey(pname)) {
                    System.out.println(">>> 입력한 상품은 없습니다. 다시 입력하세요. <<<\n");
                }
                else {
                    //삭제 실행
                    managerinterface = new ManagerMethod(data);
                    managerinterface.ProductModifyDelete(pMap.get(pname),"D");
                    System.out.println("\n[삭제 성공]\n");
                    break;
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}