package Interface;

import Info.*;
import Main.Home;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HomeMethod implements HomeInterface {

    LocalDate currentDate = LocalDate.now();//현재 날짜 받아오기
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년MM월dd일");//날짜 출력 형식 지정
    String formattedDate = currentDate.format(formatter);//형식에 맞게 날짜 출력

    private  Data data;

    private HashSet<Integer> set;

    Scanner sc = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public HomeMethod(Data data) {
        this.data = data;
    }

    @Override //0. 주문내역 파일정보 읽어오기
    public void OrderInfoReader(String fileName) {
        //파일에 저장된 값을 가져오기
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line; //파일에서 한줄씩 고객정로를 한문자열로 가져온다.

            while ((line = reader.readLine()) != null) {
                String[] orderData = line.split(","); // ","를 기준으로 고객정보의 각 값을 배열에 저장

                // 주문 정보의 각 값들을 적절한 데이터 타입으로 변환하여 변수에 저장
                int oNo = Integer.parseInt(orderData[0]);
                String oId = orderData[1];
                String oTitle = orderData[2];
                int oPrice = Integer.parseInt(orderData[3]);
                String oDay = orderData[4];

                // UserInfo 객체를 생성하고 고객 정보 값들을 사용하여 초기화
                OrderInfo oi = new OrderInfo(oNo, oId, oTitle, oPrice, oDay);

                data.getOlist().add(oi);
                data.getOmap().put(oNo,oi);

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        data.setOlist(data.getOlist()); //파일에서 담아온 모든정보를 저장한 리스트를 저장
        data.setOmap(data.getOmap()); //파일에서 담아온 모든정보를 저장한 맵을 저장

    }

    @Override //0. 주문상세내역 파일정보 읽어오기
    public void OrderDetailReader(String fileName) {
        //파일에 저장된 값을 가져오기
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line; //파일에서 한줄씩 고객정로를 한문자열로 가져온다.

            while ((line = reader.readLine()) != null) {
                String[] orderdetailData = line.split(","); // ","를 기준으로 고객정보의 각 값을 배열에 저장

                // 주문상세 정보의 각 값들을 적절한 데이터 타입으로 변환하여 변수에 저장
                int odNo = Integer.parseInt(orderdetailData[0]);
                String odName = orderdetailData[1];
                int odPrice = Integer.parseInt(orderdetailData[2]);
                int odAmount = Integer.parseInt(orderdetailData[3]);
                int oNo = Integer.parseInt(orderdetailData[4]);
                String odId = orderdetailData[5]; //주문한 고객아이디

                // UserInfo 객체를 생성하고 고객 정보 값들을 사용하여 초기화
                OrderDetail od = new OrderDetail(odNo, odName, odPrice, odAmount, oNo,odId);
                
                set = new HashSet<Integer>();
                
                if(data.getOdmap().containsKey(odId)) { //같은아이디 주문번호를 담기
                    set = data.getOdmap().get(od.getOdId());
                    set.add(oNo); //같은 아이디에 주문번호 중복저장x
                }
                else {
                    set.add(oNo);
                }

                data.getOdlist().add(od);
                data.getOdmap().put(odId,set);

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        data.setOdlist(data.getOdlist()); //파일에서 담아온 모든정보를 해당 리스트에 저장
        data.setOdmap(data.getOdmap()); //파일에서 담아온 모든정보를 해당 맵애 저장
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

        Home home = new Home(data);

        try {
            System.out.println("\n1.다른메뉴 더보기 || 2.상품담기 || 0.뒤로가기 ");
            System.out.print("* 입력 >>> ");
            int menu = Integer.parseInt(br.readLine());

            if(menu==1) {

                System.out.print("\n[ all || coffee || ade || dessert || etc] >>> ");
                String categoryName = br.readLine();

                if (categoryName.equals("all") || categoryName.equals("coffee") || categoryName.equals("ade") ||
                        categoryName.equals("dessert") || categoryName.equals("etc")) {
                    MenuList(data.getPlist(), categoryName);
                } else {
                    System.out.println(("[Error!!]"));
                    home.HomeMenuListView();
                }
            }
            else if(menu==0) { home.HomeView();}
        } catch (Exception e) {
            System.out.println("[Error!!]");
            home.HomeMenuListView();
            e.printStackTrace();
        }
    }

    //1-3 상품담기
    @Override
    public void BasketAdd(BasketInfo basketInfo, int check) {

        String bpName = basketInfo.getbName();

        if(check==1) { //장바구니에 같은 상품 존재(변경만 해주면됨)
            int index = data.getBlist().indexOf(data.getBmap().get(bpName));
            data.getBlist().set(index,basketInfo);
            data.getBmap().put(bpName,basketInfo);
        }
        else { //새로운 상품을 저장(새로 추가)
            data.getBlist().add(basketInfo);//장바구니리스트 추가
            data.getBmap().put(bpName,basketInfo); //장바구니맵 추가
        }

    }

    @Override //2.주문
    public void Order(int ono) {

        int oPrice = 0;
        Interface inter = new Method();
        String odId = data.getLoginId();

        //장바구니에서 상세내역먼저 저장
        for (BasketInfo bi : data.getBlist()) {
            int odNo = data.getOdlist().size() == 0 ? 1 : (data.getOdlist().get(data.getOdlist().size() - 1).getOdNo()) + 1;
            String odName = bi.getbName();
            int odPrice = bi.getbPrice();
            int odAmount = bi.getbAmount();
            int oNo = ono;

            oPrice += odPrice; //총 주문금액

            OrderDetail od = new OrderDetail(odNo, odName, odPrice, odAmount, oNo, odId);

            data.getOdlist().add(od); //주문상세내역에 더하기

            if (data.getOdmap().get(odId) == null) {
                set = new HashSet<Integer>();
            } else {
                set = data.getOdmap().get(odId); //한아이디가 주문한 주문번호만 입력(set으로 중목저장x)
            }
            set.add(oNo);
            data.getOdmap().put(odId, set);

            //재고조정
            int index = data.getPlist().indexOf(data.getPmap().get(odName)); //해당상품의 객체정보가 저장된 index번호
            ProductInfo pi = data.getPlist().get(index); //일단 기존의 정보를  pi에 저장
            pi.setpStack(pi.getpStack() - odAmount); //기존재고 - 주문한 상품재고

            data.getPlist().set(index, pi); //수정한 정보를 저장(기존거는 없어짐)
            data.getPmap().put(odName, pi); //덮어씌우기

            String info = String.format("%d,%s,%d,%d,%d,%s", odNo, odName, odPrice, odAmount, oNo, odId);
            inter.Insert(info, data.getOrderdetailfilename());//주문상세내역파일에 저장
        }

        //최종 재고가 조정된 수정값을 파일에 새로 업로드
        inter.MenuUpload(data.getPlist(), data.getProductfilename());

        //주문내역에 저장
        int oNo = ono; //주문번호
        String oTitle = data.getBlist().size() > 1 ? String.format("%s외 %d개", data.getBlist().get(0).getbName(), (data.getBlist().size() - 1)) : data.getBlist().get(0).getbName();
        String oDay = formattedDate; //Today

        String info = String.format("%d,%s,%s,%d,%s", oNo, odId, oTitle, oPrice, oDay);
        inter.Insert(info, data.getOrderfilename()); //주문내역 파일에 저장
        OrderInfo oderinfo = new OrderInfo(oNo, odId, oTitle, oPrice, oDay);

        data.getOlist().add(oderinfo);//주문내역 리스트에 새로운 주문정보 추가
        data.getOmap().put(oNo,oderinfo);


        //구매회수 1증가 회원정보 변경
        int index = data.getUlist().indexOf(data.getUmap().get(odId));
        UserInfo ui = data.getUlist().get(index);
        ui.setuCount(ui.getuCount() + 1);

        if (ui.getuCount() >= 3) { //3회이상 주문시
            ui.setuGrade("level_2");
        }
        //리스트,맵 업로드
        data.getUlist().set(index, ui);
        data.getUmap().put(odId, ui);
        inter.UserUpload(data.getUlist(), data.getUserfilename());//파일에 수정된 회원정보 새로 업로드

        //장바구니 초기화
        data.getBmap().clear();
        data.getBlist().clear();
    }

    @Override //5-1 회원정보 수정&삭제
    public void UserModifyDelete(UserInfo userInfo, String check) {

        if(check.equals("M")) { //수정일 경우
            int index = data.getUlist().indexOf(data.getUmap().get(userInfo.getuId())); //해당 객체가 저장된 index값 찾기
            //리스트,맵에 새로운 객쳊어보 저장(set을 통해 해당자리에 수정)
            data.getUlist().set(index,userInfo);
            data.getUmap().put(userInfo.getuId(),userInfo);
        }
        else { //탈퇴일 경우
            //회원리스트,맵에서 해당객체정보 삭제
            data.getUlist().remove(data.getUmap().get(userInfo.getuId()));
            data.getPmap().remove(userInfo.getuId());
        }
        
        Interface inter = new Method();
        inter.UserUpload(data.getUlist(),data.getUserfilename()); //파일에 최종적으로 업로드

    }
}