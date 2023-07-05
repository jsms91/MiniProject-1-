package Info;

public class ProductInfo implements Comparable<ProductInfo> {
    private int pNo; //상품번호
    private String pName; //상품명
    private String pCategory; //카테고리명
    private int pCategoryNumber; //카테고리 no
    private int pPrice; //가격
    private int pStack; //재고
    private String pDescription;   //설명

    public ProductInfo() {
        super();
    }

    public ProductInfo(int pNo, String pName, String pCategory, int pCategoryNumber, int pPrice, int pStack, String pDescription) {
        super();
        this.pNo = pNo;
        this.pName = pName;
        this.pCategory = pCategory;
        this.pCategoryNumber = pCategoryNumber;
        this.pPrice = pPrice;
        this.pStack = pStack;
        this.pDescription = pDescription;
    }

    public int getpNo() {
        return pNo;
    }

    public void setpNo(int pNo) {
        this.pNo = pNo;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public int getpCategoryNumber() {
        return pCategoryNumber;
    }

    public void setpCategoryNumber(int pCategoryNumber) {
        this.pCategoryNumber = pCategoryNumber;
    }

    public int getpPrice() {
        return pPrice;
    }

    public void setpPrice(int pPrice) {
        this.pPrice = pPrice;
    }

    public int getpStack() {
        return pStack;
    }

    public void setpStack(int pStack) {
        this.pStack = pStack;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    @Override
    public int compareTo(ProductInfo other) {
        return Integer.compare(this.pCategoryNumber, other.getpCategoryNumber());
    }
}