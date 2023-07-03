package Interface;

import Info.*;
import Main.User;

import java.util.*;
public class HomeMethod implements HomeInterface {

    private Data data;

    private List<UserInfo> uList;
    private List<ProductInfo> pList;


    private HashMap<String, UserInfo> uMap;
    private HashMap<String, ProductInfo> pMap;


    public HomeMethod(Data data) {
        this.data = data;
        this.uList = data.getUlist();
        this.pList = data.getPlist();
        this.uMap = data.getUmap();
        this.pMap = data.getPmap();
    }
    //상품목록리스트
    @Override
    public int MallProductList(ProductInfo pi, int categoryNum) {

        //System.out.println("상품명 --> " + pi.getpName() + "|| categoryNum");

        int n = 0;

        switch (categoryNum) {
            case 0 : //전체보기
                System.out.print(pi.getpName()+"["+pi.getpPrice()+"원]   ");
                n=1;
                break;
            case 1: //coffee
                if(pi.getpCategoryNumber()==1){
                    System.out.print(pi.getpName()+"["+pi.getpPrice()+"원]   ");
                }
                n=1;
                break;
            case 2 : //ade
                if(pi.getpCategoryNumber()==2){
                    System.out.print(pi.getpName()+"["+pi.getpPrice()+"원]   ");
                }
                n=1;
                break;
            case 3 : //desert
                if(pi.getpCategoryNumber()==3){
                    System.out.print(pi.getpName()+"["+pi.getpPrice()+"원]   ");
                }
                n=1;
                break;
            default : //etc
                if(pi.getpCategoryNumber()==4){
                    System.out.print(pi.getpName()+"["+pi.getpPrice()+"원]   ");
                }
                n=1;
                break;
        }
        return n;

    }
}
