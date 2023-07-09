package Interface;

import Info.Data;
import Info.UserInfo;

import java.io.BufferedReader;
import java.io.FileReader;

public class UserMethod implements UserInterface {
	
	Data data = new Data(); //Data객체 생성
	UserInfo userInfo = new UserInfo();
	public UserMethod(Data data) {
		this.data = data;
	}

	@Override//1.회원파일에서 읽어온 정보 -> list,map에 저장
	public void UserInfoReader(String fileName) {
		
		//파일에 저장된 값을 가져오기
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {	
		 
			String line; //파일에서 한줄씩 고객정로를 한문자열로 가져온다.
		 
			while ((line = reader.readLine()) != null) {

				String[] userData = line.split(","); // ","를 기준으로 고객정보의 각 값을 배열에 저장

				// 고객 정보의 각 값들을 적절한 데이터 타입으로 변환하여 변수에 저장
				int uNo = Integer.parseInt(userData[0]);
				String uId = userData[1];
				String uPw = userData[2];
				String uName = userData[3];
				int uCount = Integer.parseInt(userData[4]);
				String uGrade = userData[5];
				String uCategory = userData[6];

				// UserInfo 객체를 생성하고 고객 정보 값들을 사용하여 초기화
				userInfo = new UserInfo(uNo, uId, uPw, uName, uCount, uGrade, uCategory);

				data.getUlist().add(userInfo);
				data.getUmap().put(uId,userInfo);

			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		data.setUlist(data.getUlist()); //파일에서 담아온 모든정보를 저장한 리스트를 저장
		data.setUmap(data.getUmap()); //파일에서 담아온 모든정보를 저장한 맵을 저장

	}

	//회원정보 리스트
	@Override
	public void UserList(UserInfo u) { //UserList메소드에 전달받은 데이터인 UserInfo객체 변수 u를 통해 객체에 저장된 필드값을 사용

			int uNo = u.getuNo();
			String uId = u.getuId();
			String uPw = u.getuPw();
			String uName = u.getuName();
			int uCount = u.getuCount();
			String uGrade = u.getuGrade();
			String uCategory = u.getuCategory();

			System.out.printf("%-12d || %-12s || %-12s || %-12s || %-12d || %-12s || %-12s\n",
					uNo,uId,uPw,uName,uCount,uGrade,uCategory);

	}

	//로그인(id, pw 맞으면 true리턴)
	@Override
	public boolean Login(String uId, String uPw) {

		if(!data.getUmap().containsKey(uId)) {
			System.out.println("\n >>> 아이디가 존재하지 않습니다. 다시 입력해주세요. <<< \n");
			return false;
		}
		else {
			if(!data.getUmap().get(uId).getuPw().equals(uPw)) {
				System.out.println("\n >>> 비밀번호가 틀렸습니다. 다시 입력해주세요. <<< ");
				return false;
			}
			else {
				return true; //로그인 성공시 true를 반환
			}
		}

	}

}