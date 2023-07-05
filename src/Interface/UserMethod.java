package Interface;

import java.io.*;
import java.util.*;

import Info.*;

public class UserMethod implements UserInterface {
	
	Data data = new Data(); //Data객체 생성
	UserInfo userinfo = new UserInfo();

	private final List<UserInfo> uList;// = new ArrayList<>();
	private final HashMap<String,UserInfo> uMap;// = new HashMap<String, UserInfo>();
	
	public UserMethod(Data data) {
		this.data = data;
		this.uList = data.getUlist();
		this.uMap = data.getUmap();
	}

	@Override//파일로부터 값을 읽어와서 save메소드를 통해 list와 map에 저장(맨처음에 실행할때 한번 불러온다.)
	public void UserInfoReader(String fileName) {
		
		//파일에 저장된 값을 가져오기
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {	
		 
			String line; //파일에서 한줄씩 고객정로를 한문자열로 가져온다.
		 
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(","); // ","를 기준으로 고객정보의 각 값을 배열에 저장

				// 고객 정보의 각 값들을 적절한 데이터 타입으로 변환하여 변수에 저장
				int uno = Integer.parseInt(data[0]);
				String uid = data[1];
				String upw = data[2];
				String uname = data[3];
				int ucount = Integer.parseInt(data[4]);
				String ugrade = data[5];
				String ucategory = data[6];

				// UserInfo 객체를 생성하고 고객 정보 값들을 사용하여 초기화
				userinfo = new UserInfo(uno, uid, upw, uname, ucount, ugrade, ucategory);

				Add(userinfo, uid); // Add 메서드를 호출하여 UserInfo 객체와 uid 값을 전달하여 저장

			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\n>>> 파일이름 : " + data.getUserfilename() + "<<<\n");

		data.setUlist(uList); //파일에서 담아온 모든정보를 저장한 리스트를 저장
		data.setUmap(uMap); //파일에서 담아온 모든정보를 저장한 맵을 저장

	}	
	
	@Override //불러온 파일의 값을 List와 Map에 저장
	public void Add(UserInfo userinfo, String uid) {
		uList.add(userinfo);
		uMap.put(uid, userinfo);
	}

	//회원정보 리스트
	@Override
	public void UserList(UserInfo u) {

			int uno = u.getuNo();
			String uid = u.getuId();
			String upw = u.getuPw();
			String uname = u.getuName();
			int ucount = u.getuCount();
			String ugrade = u.getuGrade();
			String ucategory = u.getuCategory();

			System.out.printf("%-12d || %-12s || %-12s || %-12s || %-12d || %-12s || %-12s\n\n",
					uno,uid,upw,uname,ucount,ugrade,ucategory);

	}

	//로그인(id, pw 맞으면 true리턴)
	@Override
	public boolean Login(String uid, String upw) {

		if(!uMap.containsKey(uid)) {
			System.out.println("\n >>> 아이디가 존재하지 않습니다. 다시 입력해주세요. <<< \n");
			return false;
		}
		else {
			if(!uMap.get(uid).getuPw().equals(upw)) {
				System.out.println("\n >>> 비밀번호가 틀렸습니다. 다시 입력해주세요. <<< ");
				return false;
			}
			else {
				return true; //로그인 성공시 true를 반환
			}
		}

	}

}