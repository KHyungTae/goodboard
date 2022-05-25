package first.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.plexus.util.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

//DAO는 데이터베이스에 접근하여 데이터를 조작하는(가져오거나 입력하는 등) 역할만 수행.
//보기 편하게 로그를 남기기위해 AbstractDAO에 insert,delete,update,select메서드를 재정의 한 클래스이다.
public class AbstractDAO {

	protected Log log = LogFactory.getLog(AbstractDAO.class);
	
	//SqlSessionTemplate를 선언하고 여기서 @Autovired애노테이션을 통해서 .xml에 선언했던 의존관계를 자동으로 주입
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	protected void printQueryId(String queryId) {
		if(log.isDebugEnabled()) {
			log.debug("\t QueryId \t:  " + queryId);
		}
	}
	
	public Object insert(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}
	
	public Object update(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}
	
	public Object delete(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}
	
	public Object selectOne(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}
	
	public Object selectOne(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
		}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId, params);
	}
	//페이징을 처리하는 로직
	@SuppressWarnings("nuchecked")
	public Object selectPagingList(String queryId, Object params) {
		printQueryId(queryId);
		Map<String, Object> map = (Map<String, Object>)params;
		
		//현재 페이지 번호와 한 페이지에 보여줄 행의 개수를 계산하는 부분
		String strPageIndex = (String)map.get("PAGE_INDEX"); //화면에서 PAGE_INDEX 값을 보내주도록함.
		String strPageRow = (String)map.get("PAGE_ROW"); //화면에서 PAGE_ROW 값을 보내주도록함.
		//혹시모를 예외상황에 대비하여 해당 값을 각각 0과 20으로 설정
		int nPageIndex = 0;
		int nPageRow = 10;
		
		if(StringUtils.isEmpty(strPageIndex) == false) {
			nPageIndex = Integer.parseInt(strPageIndex)-1;
		}
		if(StringUtils.isEmpty(strPageRow) == false) {
			nPageRow = Integer.parseInt(strPageRow);
		}

		map.put("START", (nPageIndex * nPageRow) +1); //페이징 쿼리의 시작값을 계산
		map.put("END", (nPageIndex * nPageRow) +nPageRow ); //페이징 쿼리의 끝값을 계산
		
		return sqlSession.selectList(queryId, map);  //일반적인 리스트 조회를 호출
	}
}
