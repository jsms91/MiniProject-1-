package Interface;

import Info.*;
import Main.Manager;

import java.io.*;
import java.util.*;
public class ManagerMethod implements  ManagerInterface {
    Data data = new Data();
    ProductInfo productinfo = new ProductInfo();
    private List<ProductInfo> pList = new ArrayList<>();
    private final HashMap<String,ProductInfo> pMap;// = new HashMap<String, ProductInfo>();
    
    public ManagerMethod(Data data) {
        this.data = data;
        this.pMap = data.getPmap();
        this.pList = data.getPlist();
    }

    @Override //1. 파일에서 상품정보 읽어와서 list, map에 저장
    public void ProductInfoReader(String fileName) {
        //파일에 저장된 값을 가져오기
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line; //파일에서 한줄씩 고객정로를 한문자열로 가져온다.

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // ","를 기준으로 고객정보의 각 값을 배열에 저장

                // 상품 정보의 각 값들을 적절한 데이터 타입으로 변환하여 변수에 저장
                int pNo = Integer.parseInt(data[0]);
                String pName = data[1];
                String pCategory = data[2];
                int pCategoryNumber = Integer.parseInt(data[3]);
                int pPrice = Integer.parseInt(data[4]);
                int pStack = Integer.parseInt(data[5]);
                String pDescription = data[6];

                // ProductInfo 객체를 생성하고 고객 정보 값들을 사용하여 초기화
                productinfo = new ProductInfo(pNo, pName, pCategory, pCategoryNumber, pPrice, pStack, pDescription);

                Add(productinfo, pName); // Add 메서드를 호출하여 ProductInfo 객체와 pName 값을 전달하여 저장

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        data.setPlist(pList); //파일에서 담아온 모든정보를 저장한 리스트를 저장
        data.setPmap(pMap); //파일에서 담아온 모든정보를 저장한 맵을 저장

    }

    @Override //2.파일에서 읽어오는 정보들 한줄마다 추가
    public void Add(ProductInfo productinfo, String pname) {
        pList.add(productinfo);
        pMap.put(pname, productinfo);
    }

    @Override//3.상품리스트
    public void ProductList(ProductInfo productinfo) {

        int pno = productinfo.getpNo(); //상품번호
        String pname = productinfo.getpName(); //상품명
        String pcategory = productinfo.getpCategory(); //카테고리
        int pprice = productinfo.getpPrice(); //가격
        int pstack = productinfo.getpStack(); //재고
        String pdescription = productinfo.getpDescription();   //설명

        System.out.printf("%-12d || %-12s || %-12s || %-12d || %-12d || %-50s\n%n",
                pno,pname,pcategory,pprice,pstack,pdescription);

    }

    @Override //4. 상품수정 및 삭제
    public void MenuModifyDelete(ProductInfo productinfo, String check) {

        String pname = productinfo.getpName();

        if(check.equals("M")) {
            System.out.println("*******************상품수정에 들어옴");
            int index=-1;
            for(int i=0; i< pList.size(); i++) {
                if(pList.get(i).getpName().equals(pname)) {
                    index = i;
                    break;
                }
            }
            pMap.put(pname,productinfo); //기존에 있던 수정전에다 수정후를 덮기
            pList.set(index,productinfo); //해당 정보가 있던 자리에 list의 값을 수정후로 바꾸기
        }
        else {
            System.out.println("*******************상품삭제에 들어옴");
            pList.remove(pMap.get(pname));
            pMap.remove(pname);
            System.out.println(pList.size() + ",,,,,, " + pMap.size());
        }

        Interface ifc = new Method();

        ifc.MenuUpload(pList,data.getProductfilename()); //List, Map에 최종적으로 수정한 정보 업로드후 파일에도 업로드

        System.out.println("수정&삭제한 상품 파일에 새로 업로드");

    }

}