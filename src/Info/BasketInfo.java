package Info;

//장바구니
public class BasketInfo {

    private int bNo; //장바구니에 담긴 상품번호
    private String bName; //장바구니에 담은 상품명
    private int bAmount; //장바구니에 담은 상품수량
    private int bPrice; //가격

    public BasketInfo() {
        super();
    }
    public BasketInfo(int bNo, String bName, int bAmount, int bPrice) {
        super();
        this.bNo = bNo;
        this.bName = bName;
        this.bAmount = bAmount;
        this.bPrice = bPrice;
    }

    public int getbNo() {
        return bNo;
    }

    public void setbNo(int bNo) {
        this.bNo = bNo;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public int getaMount() {
        return bAmount;
    }

    public void setaMount(int bAmount) {
        this.bAmount = bAmount;
    }

    public int getbPrice() {
        return bPrice;
    }

    public void setbPrice(int bPrice) {
        this.bPrice = bPrice;
    }

}