package first.sample.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//비즈니스 로직의 수행을 위한 메서드를 정의
public interface SampleService {

	//변수 이름을 map으로 바꾸고, 뒤에 throws Exception을 붙임.
	List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception;
	
	//업로드를 하기위해 HttpServletRequest를 추가
	//첨부파일은 Multipart형식의 데이터이며, HttpServletRequest에 담겨서 서버로 전송된다.
	void insertBoard(Map<String, Object> map) throws Exception;  //업로드 할때 (, HttpServletRequest request 추가)
	
	Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception;
	
	//다중업로드를 위해 첨부파일 정보를 포함한 HttpServletRequest를 추가
	void updateBoard(Map<String, Object> map) throws Exception;  //업로드 할때 (, HttpServletRequest request 추가)
	
	void deleteBoard(Map<String, Object> map) throws Exception;
	
}