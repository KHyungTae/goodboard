package first.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

//import first.common.util.FileUtils;
import first.sample.dao.SampleDAO;

//하나의 페이지를 호출할 때 필요한 비지니스 로직을 묶어서 처리하는 곳이다.
//Service인터페이스를 통해 정의된 메서드를 실제로 구현하는 클래스

//@Service애노테이션을 이용하여 Service객체임을 선언하고 객체의 이름은 "sampleService"라고 선언
@Service("sampleService")
public class SampleServiceImpl implements SampleService {

	Logger log = Logger.getLogger(this.getClass());
	
	/* FileUtils클래스에 @Component애노테이션을 이용해 객체의 관리를 스프링이 맡긴다고 적었는데, 따라서
	   클래스를 사용할때 new를 사용하여 객체를 만드는 것이 아니라, @Resource애노테이션을 이용하여 객체의 선언만 해주면된다.
	   객체의 생성 및 정리는 스프링에서 알아서 처리해준다. */
//	@Resource(name="fileUtils")
//	private FileUtils fileUtils;
	
	//Service에서 데이터 접근을 위한 DAO객체를 선언
	//SampleServiceImpl에서 @Resource(name="sampleDAO")로 bean을 수동으로 등록. bean이름이 "sampleDAO"
	@Resource(name="sampleDAO")
	private SampleDAO sampleDAO;

	//서비스의 selectBoardList의 결과값으로 sampleDAO클래스의 selectBoardList메서드를 호출하고, 결과값을 return.
	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return sampleDAO.selectBoardList(map);
	}

	//해당 데이터를 이용하기 위해서 request를 추가로 넘겨주었다.
	//첨부파일은 Multipart형식의 데이터이며, HttpServletRequest에 담겨서 서버로 전송된다.
	@Override   //업로드를 하기위해 HttpServletRequest를 추가
	public void insertBoard(Map<String, Object> map) throws Exception {  //업로드 할때 (, HttpServletRequest request 추가)
		// TODO Auto-generated method stub
		sampleDAO.insertBoard(map);
		
		//FileUtils클래스를 이용하여 파일을 저장하고 그 데이터를 가져온 후 , DB에 저장하는 부분.
//		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(map, request);
//		for(int i=0, size=list.size(); i<size; i++) {
//			sampleDAO.insertFile(list.get(i));
		}
//	}

	/* 2개의 DAO를 호출 (DAO는 단순히 DB에 접속하여 데이터를 조회하는 역할만 수행하는 클래스 이기 때문에 
	 * DAO에서 2개 이상의 동작을 수행하면 안된다.
	 * 그리하여 DAO에서는 위에서 설명한 두가지 동작에 대한 쿼리를 각각 호출하도록 한다. 
	 * (SQL.xml에서 updateHitCnt라는 쿼리와 selectBoardDetail이라는 쿼리를 각각 수행함. */ 
	@Override
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		sampleDAO.updateHitCnt(map); //해당 게시글의 조회수를 1 증가시킨다.
//		Map<String, Object> resultMap = new HashMap<String, Object>(); 
		Map<String, Object> resultMap = sampleDAO.selectBoardDetail(map); //게시글의 상세내용을 조회한다. (tempMap --> resultMap으로 변경 업로드시 다시 바꾸기)
//		resultMap.put("map", tempMap);//"map"이라는 이름으로 resultMap에 저장(SampleController에서 map.get("map")키로 사용)

//		List<Map<String, Object>> list = sampleDAO.selectFileList(map); //게시글의 첨부파일목록을 가져온다.
//		resultMap.put("list", list);//"list"라는 이름으로 resultMap에 저장(SampleController에서 map.get("list")키로 사용)
		
		return resultMap;
	}

	@Override
	public void updateBoard(Map<String, Object> map) throws Exception {  //업로드 할때 (, HttpServletRequest request 추가)
		// TODO Auto-generated method stub
		sampleDAO.updateBoard(map);
		
//		sampleDAO.deleteFileList(map); //게시글에 해당하는 첨부파일을 전부 삭제 처리(DEL_GB = 'Y')를 하는 역할.
		/* fileUtils의 parseUpdateFileInfo메서드를 이용해서 request에 담겨있는 파일 정보를 list로 변환,
		 * 이때, 기존에 저장된 파일 중에서 삭제되지 않은 파일정보도 함께 반환할 것이다. */
//		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		//파일을 하나씩 입력(insert) 또는 수정(update)을 하는데, 이는 list에 담긴 파일정보중에서 IS_NEW라는 값을 이용해 판별.
//		Map<String,Object> tempMap = null;
//		for(int i=0, size=list.size(); i<size; i++){
//			tempMap = list.get(i);
			//IS_NEW라는 값이 "Y"인 경우는 신규 저장될 파일이라는 의미이고, "Y"가 아니면 기존에 저장되어 있던 파일이라는 의미.
//			if(tempMap.get("IS_NEW").equals("Y")){
//				sampleDAO.insertFile(tempMap); //신규저장은 sampleDAO.insertFile을 이용하여 파일정보를 저장.
//			}
//			else{
//				sampleDAO.updateFile(tempMap); //그렇지 않으면 저장된 파일정보는 다시 삭제처리를 바꿔줌.
//			}
//		}
	}

	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		sampleDAO.deleteBoard(map);
		
	}	
}