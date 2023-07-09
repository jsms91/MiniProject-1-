package Interface;

import Info.Data;
import Info.ProductInfo;

import java.io.BufferedReader;
import java.io.FileReader;

public class ManagerMethod implements  ManagerInterface {
    Data data;
    ProductInfo productinfo = new ProductInfo();

    public ManagerMethod(Data data) {
        this.data = data;
    }

    @Override //1. 파일에서 상품정보 읽어와서 list, map에 저장
    public void ProductInfoReader(String fileName) {
        //파일에 저장된 값을 가져오기
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line; //파일에서 한줄씩 고객정로를 한문자열로 가져온다.

            while ((line = reader.readLine()) != null) {
                String[] productData = line.split(","); // ","를 기준으로 고객정보의 각 값을 배열에 저장

                // 상품 정보의 각 값들을 적절한 데이터 타입으로 변환하여 변수에 저장
                int pNo = Integer.parseInt(productData[0]);
                String pName = productData[1];
                String pCategory = productData[2];
                int pCategoryNumber = Integer.parseInt(productData[3]);
                int pPrice = Integer.parseInt(productData[4]);
                int pStack = Integer.parseInt(productData[5]);
                String pDescription = productData[6];

                // ProductInfo 객체를 생성하고 고객 정보 값들을 사용하여 초기화
                productinfo = new ProductInfo(pNo, pName, pCategory, pCategoryNumber, pPrice, pStack, pDescription);
                
                //상품 list,map에 새로운 상품정보 추가
                data.getPlist().add(productinfo);
                data.getPmap().put(pName,productinfo);

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        data.setPlist(data.getPlist()); //파일에서 담아온 모든정보를 저장한 리스트를 저장
        data.setPmap(data.getPmap()); //파일에서 담아온 모든정보를 저장한 맵을 저장

    }

    @Override//2.상품리스트
    public void ProductList(ProductInfo productinfo) {

        int pNo = productinfo.getpNo(); //상품번호
        String pName = productinfo.getpName(); //상품명
        String pCategory = productinfo.getpCategory(); //카테고리
        int pPrice = productinfo.getpPrice(); //가격
        int pStack = productinfo.getpStack(); //재고
        String pDescription = productinfo.getpDescription();   //설명

        System.out.printf("%-12d || %-12s || %-12s || %-12d || %-12d || %-50s\n",
                pNo,pName,pCategory,pPrice,pStack,pDescription);

    }

    @Override //4. 상품수정 및 삭제
    public void MenuModifyDelete(ProductInfo productinfo, String check) {

        String pname = productinfo.getpName();

        if(check.equals("M")) { //상품수정
            int index=-1;
            for(int i=0; i< data.getPlist().size(); i++) {
                if(data.getPlist().get(i).getpName().equals(pname)) {
                    index = i;
                    break;
                }
            }
            data.getPmap().put(pname,productinfo); //기존에 있던 수정전에다 수정후를 덮기
            data.getPlist().set(index,productinfo); //해당 정보가 있던 자리에 list의 값을 수정후로 바꾸기
        }
        else {//상품삭제
            data.getPlist().remove(data.getPmap().get(pname));
            data.getPmap().remove(pname);
        }

        Interface ifc = new Method();

        ifc.MenuUpload(data.getPlist(),data.getProductfilename()); //List, Map에 최종적으로 수정한 정보 업로드후 파일에도 업로드

    }

}