package Info;

import java.util.*;

public class Data {

    public String userfilename; //회원정보파일이름
    public List<UserInfo> ulist = new ArrayList<>(); //회원정보리스트
    public HashMap<String,UserInfo> umap = new HashMap<String,UserInfo>(); //회원정보맵
    public String LoginId; //로그인 중인 아이디

    public String productfilename; //상품정보 파일이름
    public List<ProductInfo> plist = new ArrayList<>();; //상품목록 리스트
    public HashMap<String,ProductInfo> pmap = new HashMap<String,ProductInfo>();


    public Data() {
        super();
    }

    public Data(String userfilename, List<UserInfo> ulist, HashMap<String, UserInfo> umap, String loginId, String productfilename, List<ProductInfo> plist, HashMap<String, ProductInfo> pmap) {
        this.userfilename = userfilename;
        this.ulist = ulist;
        this.umap = umap;
        LoginId = loginId;
        this.productfilename = productfilename;
        this.plist = plist;
        this.pmap = pmap;
    }

    public String getUserfilename() {
        return userfilename;
    }

    public void setUserfilename(String userfilename) {
        this.userfilename = userfilename;
    }

    public List<UserInfo> getUlist() {
        return ulist;
    }

    public void setUlist(List<UserInfo> ulist) {
        this.ulist = ulist;
    }

    public HashMap<String, UserInfo> getUmap() {
        return umap;
    }

    public void setUmap(HashMap<String, UserInfo> umap) {
        this.umap = umap;
    }

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public String getProductfilename() {
        return productfilename;
    }

    public void setProductfilename(String productfilename) {
        this.productfilename = productfilename;
    }

    public List<ProductInfo> getPlist() {
        return plist;
    }

    public void setPlist(List<ProductInfo> plist) {
        this.plist = plist;
    }

    public HashMap<String, ProductInfo> getPmap() {
        return pmap;
    }

    public void setPmap(HashMap<String, ProductInfo> pmap) {
        this.pmap = pmap;
    }
}