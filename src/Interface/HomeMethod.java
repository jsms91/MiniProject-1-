package Interface;

import Info.*;
import Main.Home;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class HomeMethod implements HomeInterface {

    LocalDate currentDate = LocalDate.now();//현재 날짜 받아오기
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년MM월dd일");//날짜 출력 형식 지정
    String formattedDate = currentDate.format(formatter);//형식에 맞게 날짜 출력

    private  Data data;

    private List<UserInfo> uList;
    private List<ProductInfo> pList;
    private List<BasketInfo> bList;
    private List<OrderInfo> oList;
    private List<OrderDetail> odList;


    private HashMap<String, UserInfo> uMap;
    private HashMap<String, ProductInfo> pMap;
    private HashMap<String, BasketInfo> bMap;
    private HashMap<Integer, OrderInfo> oMap;
    private HashMap<String, HashSet<Integer>> odMap;



    Scanner sc = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public HomeMethod(Data data) {
        this.data = data;
        this.uList = data.getUlist();
        this.pList = data.getPlist();
        this.bList = data.getBlist();
        this.oList = data.getOlist();
        this.odList = data.getOdlist();
        this.uMap = data.getUmap();
        this.pMap = data.getPmap();
        this.bMap = data.getBmap();
        this.oMap = data.getOmap();
        this.odMap = data.getOdmap();
    }

    @Override //0. 주문내역 파일정보 읽어오기
    public void OrderInfoReader(String fileName) {
        //파일에 저장된 값을 가져오기
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line; //파일에서 한줄씩 고객정로를 한문자열로 가져온다.

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // ","를 기준으로 고객정보의 각 값을 배열에 저장

                // 주문 정보의 각 값들을 적절한 데이터 타입으로 변환하여 변수에 저장
                int oNo = Integer.parseInt(data[0]);
                String oId = data[1];
                String oTitle = data[2];
                int oPrice = Integer.parseInt(data[3]);
                String oDay = data[4];

                // UserInfo 객체를 생성하고 고객 정보 값들을 사용하여 초기화
                OrderInfo oi = new OrderInfo(oNo, oId, oTitle, oPrice, oDay);

                oList.add(oi);
                oMap.put(oNo,oi);

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n>>> 파일이름 : " + data.getOrderfilename() + "<<<\n");

        data.setOlist(oList); //파일에서 담아온 모든정보를 저장한 리스트를 저장
        data.setOmap(oMap); //파일에서 담아온 모든정보를 저장한 맵을 저장

    }

    @Override //0. 주문상세내역 파일정보 읽어오기
    public void OrderDetailReader(String fileName) {
        //파일에 저장된 값을 가져오기
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line; //파일에서 한줄씩 고객정로를 한문자열로 가져온다.

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // ","를 기준으로 고객정보의 각 값을 배열에 저장

                // 주문상세 정보의 각 값들을 적절한 데이터 타입으로 변환하여 변수에 저장
                int odNo = Integer.parseInt(data[0]);
                String odName = data[1];
                int odPrice = Integer.parseInt(data[2]);
                int odAmount = Integer.parseInt(data[3]);
                int oNo = Integer.parseInt(data[4]);
                String odId = data[5]; //주문한 고객아이디

                // UserInfo 객체를 생성하고 고객 정보 값들을 사용하여 초기화
                OrderDetail od = new OrderDetail(odNo, odName, odPrice, odAmount, oNo,odId);
                HashSet<Integer> set = new HashSet<Integer>();
                if(odMap.containsKey(odId)) { //같은아이디 주문번호를 담기
                    set = odMap.get(od.getOdId());
                    set.add(oNo); //같은 아이디에 주문번호 중복저장x
                }
                else {
                    set.add(oNo);
                }

                odList.add(od);
                odMap.put(odId,set);

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n>>> 파일이름 : " + data.getOrderdetailfilename() + "<<<\n");

        data.setOdlist(odList); //파일에서 담아온 모든정보를 해당 리스트에 저장
        data.setOdmap(odMap); //파일에서 담아온 모든정보를 해당 맵애 저장
    }

    @Override //1.상품목록리스트
    public void MenuList(List<ProductInfo> pList, String categoryName) {

        Title(categoryName); //위에 카테고리 항목 타이틀 출력

        int n = 0;

        for(ProductInfo pi : pList) {
            if(pi.getpCategory().equals(categoryName) || categoryName.equals("all")) {
                System.out.print(String.format("%s[%d원]   ", pi.getpName(), pi.getpPrice()));
                n++;
            }
            if(n%3==0 && n>0) { //3개 입력할때 마다 줄바꿈
                System.out.println("\n");
            }
        }
        System.out.println();
        if(n==0) { System.out.println(">>> 상품을 준비중입니다.<<<"); }

        showOtherMenu(); //다른메뉴 선택
    }

    @Override//1-1상품목록에서 타이틀(항목)만 출력
    public void Title(String categoryName) {

        switch(categoryName) {
            case "all" :
                System.out.println("All(***)  ||  Coffee  ||  Ade  ||  Dessert  ||  Etc\n");
                break;
            case "coffee" :
                System.out.println("All  ||  Coffee(***)  ||  Ade  ||  Dessert  ||  Etc\n");
                break;
            case "ade" :
                System.out.println("All  ||  Coffee  ||  Ade(***)  ||  Dessert  ||  Etc\n");
                break;
            case "dessert" :
                System.out.println("All  ||  Coffee  ||  Ade  ||  Dessert(***)  ||  Etc\n");
                break;
            case "etc" :
                System.out.println("All  ||  Coffee  ||  Ade  ||  Dessert  ||  Etc(***)\n");
                break;
        }
    }

    @Override //1-2다른메뉴 더보기
    public void showOtherMenu() {

        System.out.println("\n1.다른메뉴 더보기 || 2.상품담기 || 0.뒤로가기 ");
        System.out.print("* 입력 >>> ");
        int menu = sc.nextInt();

        Home home = new Home(data);

        if(menu==1) {
            try {
                System.out.print("\n[ all || coffee || ade || dessert || etc] >>> ");
                String categoryName = br.readLine();

                if (categoryName.equals("all") || categoryName.equals("coffee") || categoryName.equals("ade") ||
                        categoryName.equals("dessert") || categoryName.equals("etc")) {
                    MenuList(pList, categoryName);
                } else {
                    System.out.println(("[Error!!]"));
                    home.HomeMenuListView();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(menu==0) { home.HomeView();}

    }

    //1-3 상품담기
    @Override
    public void BasketAdd(BasketInfo basketInfo, int check) {

        String bpName = basketInfo.getbName();

        if(check==1) { //장바구니에 같은 상품 존재(변경만 해주면됨)
            int index = bList.indexOf(bMap.get(bpName));
            bList.set(index,basketInfo);
            bMap.put(bpName,basketInfo);
        }
        else { //새로운 상품을 저장(새로 추가)
            bList.add(basketInfo);//장바구니리스트 추가
            bMap.put(bpName,basketInfo); //장바구니맵 추가
        }

    }

    @Override //2.주문
    public void Order(int ono) {

        System.out.println("주문들어옴[장바구니 담긴 개수는?]" + bList.size());
        int oPrice = 0;
        Interface inter = new Method();
        String odId = data.getLoginId();

        //장바구니에서 상세내역먼저 저장
        for(BasketInfo bi : bList) {
            int odNo = odList.size() == 0 ? 1 : (odList.get(odList.size()-1).getOdNo())+1;
            String odName = bi.getbName();
            int odPrice = bi.getbPrice();
            int odAmount = bi.getaMount();
            int oNo = ono;

            oPrice += odPrice; //총 주문금액

            OrderDetail od = new OrderDetail(odNo,odName,odPrice,odAmount,oNo,odId);
            odList.add(od); //주문상세내역에 더하기
            HashSet<Integer> set = odMap.get(odId); //한아이디가 주문한 주문번호만 입력(set으로 중목저장x)
            set.add(oNo);
            odMap.put(odId,set);

            //재고조정
            int index = pList.indexOf(pMap.get(odName)); //해당상품의 객체정보가 저장된 index번호
            ProductInfo pi = pList.get(index); //일단 기존의 정보를  pi에 저장
            pi.setpStack(pi.getpStack()-odAmount); //기존재고 - 주문한 상품재고
            
            pList.set(index,pi); //수정한 정보를 저장(기존거는 없어짐)
            pMap.put(odName,pi); //덮어씌우기

            String info = String.format("%d,%s,%d,%d,%d,%s",odNo,odName,odPrice,odAmount,oNo,odId);
            inter.Insert(info, data.getOrderdetailfilename());//주문상세내역파일에 저장
        }

        //최종 재고가 조정된 수정값을 파일에 새로 업로드
        inter.MenuUpload(pList,data.getProductfilename());

        //주문내역에 저장
        int oNo = ono; //주문번호
        String oTitle =  bList.size()>1 ? String.format("%s외 %d개",bList.get(0).getbName(),(bList.size()-1)) : bList.get(0).getbName();
        String oDay = formattedDate; //Today

        String info = String.format("%d,%s,%s,%d,%s",oNo,odId,oTitle,oPrice,oDay);
        inter.Insert(info,data.getOrderfilename()); //주문내역 파일에 저장



        //구매회수 1증가 회원정보 변경
        int index = uList.indexOf(uMap.get(odId));
        UserInfo ui = uList.get(index);
        ui.setuCount(ui.getuCount() + 1);

        if(ui.getuCount()>3) { //5회이상 주문시
            ui.setuGrade("level_2");
        }
        //리스트,맵 업로드
        uList.set(index,ui);
        uMap.put(odId,ui);
        inter.UserUpload(uList,data.getUserfilename());//파일에 수정된 회원정보 새로 업로드

        System.out.println("주문완료.");
        System.out.println("장바구니 크기 : " + bList.size() + " ,, " + bMap.size());
        bMap.clear();
        bList.clear();
        System.out.println("초기화한 장바구니 크기" + bList.size() + " ,, " + bMap.size());

    }

    @Override //5. 회원정보(주문내역&상세내역)
    public void OrderList() {
        System.out.println("\n=============== 주문 내역 ===============\n");



    }
}
