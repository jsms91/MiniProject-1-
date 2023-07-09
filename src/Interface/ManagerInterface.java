package Interface;

import Info.ProductInfo;

public interface ManagerInterface {

    //1.상품정보 파일에서 정보 읽어와서 list에 저장
    public void ProductInfoReader(String fileName);
    //2.상품리스트
    public void ProductList(ProductInfo productinfo);
    //3.상품수정
    public void MenuModifyDelete(ProductInfo productinfo, String check);

}