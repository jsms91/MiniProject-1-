package Interface;

import java.util.*;
import Info.*;
public interface UserInterface {

	//파일로부터 저장된 값을 읽어와서 리스트&맵에 저장 후 Data에 리스트와 맵에 저장
    void UserInfoReader(String fileName);
	
	//UserInfoList에서 파일의 정보를 불러올때 List와 Map에 값을 저장(ok)
    void Add(UserInfo userinfo, String uid);

	//회원목록 리스트
    void UserList(UserInfo u);

	//로그인
    boolean Login(String uid, String upw);

}
