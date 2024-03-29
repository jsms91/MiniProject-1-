package Interface;

import Info.BasketInfo;
import Info.ProductInfo;
import Info.UserInfo;
import java.util.*;

public interface HomeInterface {
    
    //0.주문내역 파일읽어오기
    public void OrderInfoReader(String fileName);
    //0.주문상세내역 파일읽어오기
    public void OrderDetailReader(String fileName);
    //1.상품목록
    public void MenuList(List<ProductInfo> pList, String categoryName);
    //1-1 상품목록에서 타이틀(항목)만 출력
    public void Title(String categoryName);
    //1-2 다른메뉴 더보기
    public void showOtherMenu();
    //1-3 상품 담기
    public void BasketAdd(BasketInfo basketInfo, int check);

    //3~4 주문(장바구니)
    public void Order(int ono);

    //5 회원정보 수정
    public void UserModifyDelete(UserInfo userInfo, String check); //수정한 회원정보와 check(수정,삭제 선택)

}
