package Interface;

import Info.*;
import Main.Manager;

import java.io.*;
import java.util.*;
public class ManagerMethod implements  ManagerInterface {
    Data data = new Data();
    ProductInfo productinfo = new ProductInfo();
    private List<ProductInfo> pList = new ArrayList<>();
    private HashMap<String,ProductInfo> pMap;// = new HashMap<String, ProductInfo>();
    
    public ManagerMethod(Data data) {
        this.data = data;
        this.pMap = data.getPmap();
        this.pList = data.getPlist();
    }

    @Override
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
        System.out.println("mmmmmmmmmmmmmmm :" + data.getPmap().size());

    }

    @Override
    public void Add(ProductInfo productinfo, String pname) {
        pList.add(productinfo);
        pMap.put(pname, productinfo);
    }

    //상품리스트
    @Override
    public void ProductList(ProductInfo productinfo) {

        int pno = productinfo.getpNo(); //상품번호
        String pname = productinfo.getpName(); //상품명
        String pcategory = productinfo.getpCategory(); //카테고리
        int pprice = productinfo.getpPrice(); //가격
        int pstack = productinfo.getpStack(); //재고
        String pdescription = productinfo.getpDescription();   //설명

        System.out.println(String.format("%-12d || %-12s || %-12s || %-12d || %-12d || %-50s\n",
                pno,pname,pcategory,pprice,pstack,pdescription));

    }

    //상품수정
    @Override
    public void ProductModifyDelete(ProductInfo productinfo, String check) {

        System.out.println("plist 크기 : " + pList.size() );
        System.out.println("pMap 크기 : " + pMap.size());

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

        Collections.sort(pList);//카테고리 번로를 기준으로 오름차순 정렬

        Interface ifc = new Method();

        ifc.Modify(pList,data.getProductfilename());

        System.out.println("수정&삭제한 상품 파일에 새로 업로드");

    }

}
