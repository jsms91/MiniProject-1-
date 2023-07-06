package Interface;

import Info.*;
import Main.*;

import java.util.List;

public interface ManagerInterface {

    //상품정보 파일에서 정보 읽어와서 list에 저장
    public void ProductInfoReader(String fileName);

    //파일에 저장되어있는 상품정보를 List,Map에 저장
    public void Add(ProductInfo productinfo, String pname);

    //상품리스트
    public void ProductList(ProductInfo productinfo);

    //상품수정
    public void MenuModifyDelete(ProductInfo productinfo, String check);
}
