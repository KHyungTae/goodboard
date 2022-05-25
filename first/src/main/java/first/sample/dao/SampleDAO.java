package first.sample.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import first.common.dao.AbstractDAO;

//DAO는 데이터베이스에 접근하여 데이터를 조작하는(가져오가너 입력하는 등) 역할만 수행.
//@Repository애노테이션을 통해서 클래스가 DAO임을 선언하고 이름을 "sampleDAO"로 작성.
@Repository("sampleDAO")
public class SampleDAO extends AbstractDAO{

	//selectBoardList쿼리를 호출
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		//selectList는 MyBatis의 기본기능으로, 리스트를 조회할때 사용.
		//결과값은 List<Map<String, Object>>형식으로 반활할 수 있도록 형변환을 함.
		//selectList(쿼리 이름, 쿼리가 실행되는데 필요한 변수들)
		return (List<Map<String, Object>>) selectPagingList("sample.selectBoardList", map);
	}
	
	//insertBoard쿼리를 호출
	public void insertBoard(Map<String, Object> map) throws Exception {
		insert("sample.insertBoard", map);
	}

	//updateHitCnt쿼리를 호출 (조회수)
	public void updateHitCnt(Map<String, Object> map) throws Exception {
		update("sample.updateHitCnt", map);
	}
	//selectBoardDetail쿼리를 호출 (게시글 상세 조회)
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectOne("sample.selectBoardDetail", map);
	}
	
	//updateBoard쿼리를 호출
	public void updateBoard(Map<String, Object> map) throws Exception {
		update("sample.updateBoard", map);
	}
	
	//deleteBoard쿼리문을 호출
	public void deleteBoard(Map<String, Object> map) throws Exception {
		update("sample.deleteBoard", map);
	}
	
	//업로드 insertFile쿼리문 호출 
//	public void insertFile(Map<String, Object> map) throws Exception {
//		insert("sample.insertFile", map);
//	}
	
	//첨부파일 selectFilelist쿼리를 호출
//	@SuppressWarnings("unchecked")
//	public List<Map<String, Object>> selectFileList(Map<String, Object> map) throws Exception {
//		return (List<Map<String, Object>>) selectList("sample.selectFileList", map);
//	}
	
	//다중업로드 삭제 deleteFileList쿼리를 호출
//	public void deleteFileList(Map<String, Object> map) throws Exception {
//		update("sample.deleteFileList", map);
//	}
	
	//다중업로드 수정 updateFile쿼리를 호출
//	public void updateFile(Map<String, Object> map) throws Exception {
//		update("sample.updateFile", map);
//	}
}
