package Main;

import Info.*;
import Interface.*;
import java.io.*;
import java.util.*;


public class ProductManager {

    private Data data;
    private Interface uinterface = new Method(); // UserMethod 클래스를 구현한 객체를 UserInterface 타입으로 참조하는 userinterface 변수 생성
    private ProductInfo productinfo = new ProductInfo();
    private ManagerInterface managerinterface;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Scanner sc = new Scanner(System.in);


    public ProductManager(Data data) {
    	this.data = data;
    }

    public void ProductManagerView() { //상품관리 페이지

        System.out.println("\n=============== 상품관리 페이지 ===============\n");
        while(true) {

            System.out.println("1.상품등록 || 2.상품검색 || 3.상품목록 || 4.상품수정 || 5.상품삭제 || 0.뒤로가기\n");
            System.out.print("* 원하는 메뉴를 선택하세요. >>> ");
            int n = sc.nextInt();

            switch (n) {
                case 1 :
                    MenuRegister(); //상품등록 이동
                    break;

                case 2 :
                    MenuSearch(); //상품검색 이동
                    break;

                case 3 :
                    MenuListView(); //상품목록 이동
                    break;

                case 4 :
                    MenuModifyView(); //상품수정 이동
                    break;

                case 5 :
                    ProductDeleteView(); //상품삭제 이동
                    break;
                case 0:
                    Manager manager = new Manager(data);
                    manager.ManagerView(); //처음관리자 페이지로 이동
                    return;

                default:
                    System.out.println("\n[Error!!]\n");
                    break;

            }
        }
    }

    //상품등록
    public void MenuRegister() { //상품등록
        try {
        	
            System.out.println("\n=============== 상품 등록 ===============\n");
            
            int pno = data.getPlist().size()==0 ? 1 : (data.getPlist().get(data.getPlist().size()-1).getpNo()+1); //상품번호

            System.out.print("* 상품명 : ");
            String pname = br.readLine();
            
            if(data.getPmap().containsKey(pname)) { //기존 상품정보 리스트에 입력한 상품 존재 확인
            	System.out.println(" >>> 동일한 상품이 이미 등록되어 있습니다. <<< \n");
            	return;
            }

            System.out.print("* Category [1. coffee | 2. ade | 3.dessert](1~3번만 입력하세요. 다른번호는 etc로 분류) : ");
            int n = sc.nextInt();

            String pcategory = "";
            int pcategorynumber = -1;

            switch(n) { //입력한 번호에 맞는 카레고리값을 설정
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

            productinfo = new ProductInfo(pno,pname,pcategory,pcategorynumber,price,pstack,pdescription); //productinfo 객체변수에 입력한 상품정보를 저장

            data.getPlist().add(productinfo);
            data.getPmap().put(pname,productinfo);
            data.setPlist(data.getPlist());
            data.setPmap(data.getPmap());

            //파일에 저장하기 위해 문자열 pinfo변수에 저장
            String pinfo = pno + "," + pname + "," + pcategory  + "," + pcategorynumber + "," + price + "," +pstack + "," + pdescription;

            uinterface.Insert(pinfo, data.getProductfilename()); // 파일에 상품 정보 저장
            System.out.println("[상품 등록 완료!!]");

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

            while(!data.getPmap().containsKey(pname)) {
                System.out.println(">>> 찾으려고 하는 상품이 없습니다. <<<\n");
                System.out.print("* 상품명을 다시 입력하세요.('stop' 입력시 종료) : ");
                pname = br.readLine();
                if(pname.equals("stop")) {return;}
            }

            System.out.printf("\n%-12s||%-12s||%-12s||%-12s||%-12s||%-50s%n",
                    "상품번호","상품명","카테고리","가격","재고량","상품설명\n");
            managerinterface = new ManagerMethod(data);
            managerinterface.ProductList(data.getPmap().get(pname)); //pMap 키값에 저장된 객체를 통해 해당 상품명의 정보를 ProductList()메소드를 호출해 출력

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    //상품 목록
    public void MenuListView() {

        System.out.println("\n=============== 상품 목록 ===============\n");
        System.out.printf("%-12s||%-12s||%-12s||%-12s||%-12s||%-50s%n",
                "상품번호","상품명","카테고리","가격","재고량","상품설명\n");

        managerinterface = new ManagerMethod(data);

        Collections.sort(data.getPlist());//상품을 카테고리번호 기준 오름차순 정렬(1.coffee 2.ade 3.dessert ...)

        for(ProductInfo pi : data.getPlist()) {
            managerinterface.ProductList(pi);
        }
        System.out.println("\n");
    }

    //상품수정
    public void MenuModifyView() {

        System.out.println("\n=============== 상품 수정 ===============\n");

        try {
            String pname = "";
            while(true) {
                System.out.print("* 수정_상품명 : ");
                pname = br.readLine();
                if(data.getPmap().containsKey(pname)) {
                    break;
                }
                else {
                    System.out.println(">>> 존재하지 않는 상품입니다. 다시 입력해주세요. <<<\n");
                }
            }

            managerinterface = new ManagerMethod(data);
            managerinterface.ProductList(data.getPmap().get(pname)); //수정할 상품의 정보를 불러옴

            System.out.println("각 항목의 수정할 내용을 입력하세요.\n");

            int pno = data.getPmap().get(pname).getpNo(); //해당 상품의 상품번호

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

            //수정할지 check하기위해 메소드에 'M'과 함께 새롭게 업로드할 상품정보를 메소드를 통해 수정
            managerinterface.MenuModifyDelete(new ProductInfo(pno,pname,pcategory,pcategorynumber,price,pstack,pdescription),"M");

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
                if(!data.getPmap().containsKey(pname)) {
                    System.out.println(">>> 입력한 상품은 없습니다. 다시 입력하세요. <<<\n");
                }
                else {
                    //삭제 실행
                    managerinterface = new ManagerMethod(data);
                    managerinterface.MenuModifyDelete(data.getPmap().get(pname),"D"); //'D'를 넣어서 삭제할지 조건을 채워주고 해당 상품명을 키값으로 해당 상품의 정보가 저장된 객체를 메소드를 통해 삭제
                    System.out.println("\n[삭제 성공]\n");
                    break;
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}