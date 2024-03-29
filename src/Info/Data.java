package Info;

import java.util.*;

public class Data {
    private String userfilename = "UserInfo.txt"; //회원정보파일이름
    private String productfilename = "ManagerInfo.txt"; //상품정보 파일이름
    private String orderfilename = "OrderInfo.txt"; //주문내역 파일이름
    private String orderdetailfilename = "OrderDetail.txt"; //주문상세내역 파일이름
    private List<UserInfo> ulist = new ArrayList<>(); //회원정보리스트
    private List<ProductInfo> plist = new ArrayList<>(); //상품목록 리스트
    private List<BasketInfo> blist = new ArrayList<>(); //장바구니 리스트
    private List<OrderInfo> olist = new ArrayList<>(); //주문내역 리스트
    private List<OrderDetail> odlist = new ArrayList<>(); //주문상세내역 리스트

    private HashMap<String,UserInfo> umap = new HashMap<String,UserInfo>(); //회원정보맵
    private HashMap<String,ProductInfo> pmap = new HashMap<String,ProductInfo>();//상품정보맵
    private HashMap<String,BasketInfo> bmap = new HashMap<String, BasketInfo>();//장바구니맵
    private HashMap<Integer,OrderInfo> omap = new HashMap<Integer,OrderInfo>(); //주문내역맵
    private HashMap<String,HashSet<Integer>> odmap = new HashMap<String,HashSet<Integer>>(); //주문상세내역맵

    private String LoginId; //로그인 중인 아이디

    public Data() {
        super();
    }

    public Data(String userfilename, String productfilename, String orderfilename, String orderdetailfilename,
                List<UserInfo> ulist, List<ProductInfo> plist, List<BasketInfo> blist, List<OrderInfo> olist,
                List<OrderDetail> odlist, HashMap<String, UserInfo> umap, HashMap<String, ProductInfo> pmap,
                HashMap<String, BasketInfo> bmap, HashMap<Integer, OrderInfo> omap, HashMap<String, HashSet<Integer>> odmap,
                String loginId) {
        this.userfilename = userfilename;
        this.productfilename = productfilename;
        this.orderfilename = orderfilename;
        this.orderdetailfilename = orderdetailfilename;
        this.ulist = ulist;
        this.plist = plist;
        this.blist = blist;
        this.olist = olist;
        this.odlist = odlist;
        this.umap = umap;
        this.pmap = pmap;
        this.bmap = bmap;
        this.omap = omap;
        this.odmap = odmap;
        this.LoginId = loginId;
    }

    public String getUserfilename() {
        return userfilename;
    }

    public void setUserfilename(String userfilename) {
        this.userfilename = userfilename;
    }

    public String getProductfilename() {
        return productfilename;
    }

    public void setProductfilename(String productfilename) {
        this.productfilename = productfilename;
    }

    public String getOrderfilename() {
        return orderfilename;
    }

    public void setOrderfilename(String orderfilename) {
        this.orderfilename = orderfilename;
    }

    public String getOrderdetailfilename() {
        return orderdetailfilename;
    }

    public void setOrderdetailfilename(String orderdetailfilename) {
        this.orderdetailfilename = orderdetailfilename;
    }

    public List<UserInfo> getUlist() {
        return ulist;
    }

    public void setUlist(List<UserInfo> ulist) {
        this.ulist = ulist;
    }

    public List<ProductInfo> getPlist() {
        return plist;
    }

    public void setPlist(List<ProductInfo> plist) {
        this.plist = plist;
    }

    public List<BasketInfo> getBlist() {
        return blist;
    }

    public void setBlist(List<BasketInfo> blist) {
        this.blist = blist;
    }

    public List<OrderInfo> getOlist() {
        return olist;
    }

    public void setOlist(List<OrderInfo> olist) {
        this.olist = olist;
    }

    public List<OrderDetail> getOdlist() {
        return odlist;
    }

    public void setOdlist(List<OrderDetail> odlist) {
        this.odlist = odlist;
    }

    public HashMap<String, UserInfo> getUmap() {
        return umap;
    }

    public void setUmap(HashMap<String, UserInfo> umap) {
        this.umap = umap;
    }

    public HashMap<String, ProductInfo> getPmap() {
        return pmap;
    }

    public void setPmap(HashMap<String, ProductInfo> pmap) {
        this.pmap = pmap;
    }

    public HashMap<String, BasketInfo> getBmap() {
        return bmap;
    }

    public void setBmap(HashMap<String, BasketInfo> bmap) { this.bmap = bmap; }

    public HashMap<Integer, OrderInfo> getOmap() {
        return omap;
    }

    public void setOmap(HashMap<Integer, OrderInfo> omap) {
        this.omap = omap;
    }

    public HashMap<String, HashSet<Integer>> getOdmap() {
        return odmap;
    }

    public void setOdmap(HashMap<String, HashSet<Integer>> odmap) {
        this.odmap = odmap;
    }

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

}