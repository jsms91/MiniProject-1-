package Info;

import java.util.*;
//주문내역
public class OrderInfo {
    private int oNo;//주문번호
    private String oId; //주문고객아이디
    private String oTitle; //주문한 상품명(1개면 1개, 2개이상이면  첫번째 상품명 외 1(사이즈-1)
    private int oPrice; //총주문금액
    private String oDay; //주문일

    public OrderInfo() {
        super();
    }

    public OrderInfo(int oNo, String oId, String oTitle, int oPrice, String oDay) {
        super();
        this.oNo = oNo;
        this.oId = oId;
        this.oTitle = oTitle;
        this.oPrice = oPrice;
        this.oDay = oDay;
    }

    public int getoNo() {
        return oNo;
    }

    public void setoNo(int oNo) {
        this.oNo = oNo;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getoTitle() {
        return oTitle;
    }

    public void setoTitle(String oTitle) {
        this.oTitle = oTitle;
    }

    public int getoPrice() {
        return oPrice;
    }

    public void setoPrice(int oPrice) {
        this.oPrice = oPrice;
    }

    public String getoDay() {
        return oDay;
    }

    public void setoDay(String oDay) {
        this.oDay = oDay;
    }

}