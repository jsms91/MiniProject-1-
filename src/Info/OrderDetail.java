package Info;
//주문상세내역
public class OrderDetail {
    private int odNo; //주문상세번호
    private String odName;//주문한 상품명
    private int odPrice; //각 상품의 가격
    private int odAmount;//각 상품개수
    private int oNo;//주문번호(중복가능)
    private String odId; //주문고객 아이디(이걸로 고객아이디로 주문한 주문번호 받아서 주문목록 띄우기)

    public OrderDetail() {
        super();
    }

    public OrderDetail(int odNo, String odName, int odPrice, int odAmount, int oNo,String odId) {
        super();
        this.odNo = odNo;
        this.odName = odName;
        this.odPrice = odPrice;
        this.odAmount = odAmount;
        this.oNo = oNo;
        this.odId = odId;
    }

    public int getOdNo() {
        return odNo;
    }

    public void setOdNo(int odNo) {
        this.odNo = odNo;
    }

    public String getOdName() {
        return odName;
    }

    public void setOdName(String odName) {
        this.odName = odName;
    }

    public int getOdPrice() {
        return odPrice;
    }

    public void setOdPrice(int odPrice) {
        this.odPrice = odPrice;
    }

    public int getOdAmount() {
        return odAmount;
    }

    public void setOdAmount(int odAmount) {
        this.odAmount = odAmount;
    }

    public int getoNo() {
        return oNo;
    }

    public void setoNo(int oNo) {
        this.oNo = oNo;
    }

    public String getOdId() {
        return odId;
    }

    public void setOdId(String odId) {
        this.odId = odId;
    }
}