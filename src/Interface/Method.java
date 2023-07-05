package Interface;

import Info.ProductInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Method implements Interface {

	@Override//1. 파일생성(파일이름으로 해당 파일생성 - 회원정보, 상품정보, 주문내역..)
	public String File(String fileName) {
		
		File dir = new File("C:\\Data"); // 디렉터리 경로

		// 디렉터리 생성
	    if (!dir.exists()) { // 해당 디렉터리가 존재하지 않으면
	        if (dir.mkdirs()) { // 디렉터리 생성 시도
	            System.out.println("디렉터리 생성 성공!!!");
	        } else {
	            System.out.println("디렉터리를 생성할 수 없습니다.");
	            System.exit(0); // 프로그램 종료
	        }
	    } else {
	        System.out.println("디렉터리가 이미 존재합니다.");
	    }

	    File file = new File(dir, fileName); // 파일 객체 생성

	    // 파일 생성
	    try {
	        if (file.createNewFile()) {
	            System.out.println(fileName+"파일이 생성되었습니다.");
	        } else {
	            System.out.println("파일을 생성할 수 없습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return file.getPath();

	}

	//2. 회원가입, 상품등록, 주문(주문내역, 상세내역 각 각 업로드)
	@Override //해당 파일의 정보를 저장(회원파일-회원정보, 상품파일-상품정보, 주문내역파일-주문내역, 주문상세파일-주문상세내역)
	public void Insert(String Info, String fileName) {
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {	 
			
			 // 입력받은 고객정보를 파일에 쓰기		    
			 writer.write(Info);		    
			 writer.newLine(); // 새로운 줄로 이동
		    
			 System.out.println("\n"+fileName+" 파일에 저장되었습니다.\n");
			 
		    
		} catch (IOException e) {		  
			System.out.println("파일에 고객정보를 저장하는 중에 오류가 발생했습니다.");		        
			e.printStackTrace();		    
		}
		
	}

	@Override //3-2 상품수정 후 파일에 업로드
	public void MenuUpload(List<ProductInfo> plist, String fileName) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

			for(ProductInfo p : plist) {

				int pNo = p.getpNo(); //상품번호
				String pName = p.getpName(); //상품명
				String pCategory = p.getpCategory(); //카테고리
				int pCategoryNumber = p.getpCategoryNumber(); //카테고리 no
				int pPrice = p.getpPrice(); //가격
				int pStack = p.getpStack(); //재고
				String pDescription = p.getpDescription();//상품설명

				String info = pNo + "," + pName + "," + pCategory + "," + pCategoryNumber + "," + pPrice + "," + pStack + "," + pDescription;

				// 입력받은 고객정보를 파일에 쓰기
				writer.write(info);
				writer.newLine(); // 새로운 줄로 이동
			}

			System.out.println("\n"+fileName+" 파일에 저장되었습니다.\n");

		} catch (IOException e) {
			System.out.println("파일에 고객정보를 저장하는 중에 오류가 발생했습니다.");
			e.printStackTrace();
		}

	}

}