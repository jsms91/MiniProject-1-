package Interface;

import Info.UserInfo;

public interface UserInterface {

	//1,회원파일에서 읽어온 정보 -> list,map에 저장
    void UserInfoReader(String fileName);

	//2.회원목록 리스트
    void UserList(UserInfo u);

	//3.로그인
    boolean Login(String uid, String upw);

}