package first.sample.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import first.common.common.CommandMap;
import first.sample.service.SampleService;

//Controller는 단순히 웹 클라이언트에서 들어온 요청에 해당하는 비지니스 로직을 호출하고 응답해주는 Dispatcher역할만 한다.
@Controller
public class SampleController {

	Logger log = Logger.getLogger(this.getClass());
	
	//Service영역의 접근을 위한 선언 
	/* (@Resource애노테이션을 통해서 필요한 bean을 수동으로 등록, bean이름이 "sampleService",
	   @Service("sampleService")라고 선언했을 때의 그 이름인 것을 확인) */
	@Resource(name="sampleService")
	private SampleService sampleService;
	
	//@RequestMapping은 요청 URL을 의미하는데 밑에 주소를 호출한다. (애노테이션과 매핑되어, 해당 메서드 실행)
	@RequestMapping(value="/sample/openBoardList.do")  //boardList를 호출 (목록보기)
	//commandMap에 사용자가 넘겨준 파라미터가 저장되어 있다.
	public ModelAndView openBoardList(CommandMap commandMap) throws Exception {
		//화면에 보여줄 .jsp파일을 의미한다.
		ModelAndView mv = new ModelAndView("/sample/boardList");
		
		// 게시판 목록을 보여주기 때문에, 목록을 저장할 수 있는 List를 선언
		
		/* List의 형식은 Map<String, Object>인데, 하나의 게시글 목록에도 여러가지 정보가 존재
		 * (글번호, 글제목, 작성일 등의 내용을 Map에 저장하려는 것. (Map은 키(key), 값(value)로 구분) */
		
		/* sampleService.selectBoardList(commandMap.getMap()); 부분은 단순희 게시글을 조회하는 역할을 한다.
		 * 하지만, 나중에 상세조회의 경우, 조회수 증가와 게시글 상세내용을 조회하는 두가지 기능이 필요한데, 
		 * 이를 Service에서 처리해주게 된다. (CommandMap을 map과 똑같이 사용할 수 있도록 getMap메서드를 추가함)*/
/* 기존 페이징하기 전 코드 List<Map<String, Object>> list = sampleService.selectBoardList(commandMap.getMap()); */
		/* mv.addObject("list", list); 부분은 서비스로직의 결과를 ModelAndView객체에 담아서 클라이언트,
		 * 즉 jsp에서 그 결과를 사용할 수 있도록 한다.
		 * mv에 값을 저장하는 것은 map과 똑같이 키(key), 값(value)로 구성되어 sampleService.selectBoardList메서드를
		 * 통해 얻어온 결과 "list"라는 이름으로 저장하고 있다.  */
/* 기존 페이징하기 전 코드 mv.addObject("list", list); */
		
		return mv;
	}
	//
	@RequestMapping(value="/sample/selectBoardList.do")
	public ModelAndView selectBoardList(CommandMap commandMap) throws Exception {
		/* action-servlet.xml파일에 bean설정으로 id를 jsonView로 선언되어 있어 여기서 선언된 bean을 사용.
		 * jsonView는 데이터를 json형식으로 변환해주는 역할을 수행. */
		ModelAndView mv = new ModelAndView("jsonView");
		
		//common.js파일에 ComAjax에서 callback을 수행할 때, data라는 이름으로 보내주도록 해놨기 때문에 
		//data.TOTAL과 data.list가 Controller에서 json형식의 데이터를 화면에 전달하는데 그 값으 data라는 
		//이름으로 화면에 전달하고 mv에는 각각 list와 TOTAL이라는 key로 값을 보내주고 이는 다시 화면에서
		//각각 data.list, data.TOTAL이라는 형식으로 값에 접근할 수 있다.
		List<Map<String, Object>> list = sampleService.selectBoardList(commandMap.getMap());
		mv.addObject("list", list);
		if(list.size() > 0) {
			mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
		}
		else {
			mv.addObject("TOTAL", 0);
		}
		
		return mv;
	}
	
	@RequestMapping(value="/sample/openBoardWrite.do")  //boardWrite를 호출 (글쓰기)
	public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/boardWrite");
		
		return mv;
	}
	//HttpServletRequest에는 모든 텍스트 데이터 뿐만이 아니라 화면에서 전송된 파일정보도 함께 담겨있다.
	//업로드를 하기위해 HttpServletRequest를 추가
	@RequestMapping(value="/sample/insertBoard.do")  //작성버튼을 누르면 저장되는 경로
	//CommomandMap을 이용하여 텍스트 데이터는 처리하기 때문에, HttpServletRequest는 파일정보만 활용.
	//첨부파일은 Multipart형식의 데이터이며, HttpServletRequest에 담겨서 서버로 전송된다.
	public ModelAndView insertBoard(CommandMap commandMap) throws Exception { //업로드 할때 (, HttpServletRequest request)
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
		
		sampleService.insertBoard(commandMap.getMap());  //업로드 할때 (, request 추가)
	//	System.out.println(commandMap.getMap());
		return mv;
	}
	
	@RequestMapping(value="/sample/openBoardDetail.do")  //boardDetail을 호출 (상세보기)
	public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/boardDetail");
		
		//기존 sampleService.selectBoardDetail()의 리턴값을 그대로 map이라는 이름으로 바로 화면으로 전송하였다.
		Map<String, Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map);  //기존의 게시글 상세정보  (map.get("map"); --> 값을 못가져옴.)
//		mv.addObject("list", map.get("list"));  //추가: 첨부파일 목록
//		System.out.println(mv);
		return mv; //각각 게시글 상세정보와 첨부파일 정보를 보내주고 있다.
	}
	
	@RequestMapping(value="sample/openBoardUpdate.do")  //boardUpdate를 호출 (수정하기)
	public ModelAndView openBoardUpdate(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/boardUpdate");
		
		Map<String, Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map);  //map.get("map"); --> 값을 못가져옴.
//		mv.addObject("list", map.get("list"));
		
		return mv;
	}
	
	@RequestMapping(value="/sample/updateBoard.do") //(수정완료)
	//기존의  updateBoard.do에서 첨부파일 정보를 포함한 HttpServletRequest를 추가함.
	public ModelAndView updateBoard(CommandMap commandMap) throws Exception {  //업로드 할때 (, HttpServletRequest request 추가)
		//게시글을 수정하고 나면, 해당 게시글의 상세화면으로 이동하도록 함. 
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
		
		sampleService.updateBoard(commandMap.getMap());  //업로드 할때 (, request 추가)
		//해당 게시글의 글 번호를 mv.addObject메서드를 이용하여 다시 전송하도록 함.
		mv.addObject("IDX", commandMap.get("IDX"));
		System.out.println(mv);
		return mv;
	}
	
	@RequestMapping(value="/sample/deleteBoard.do") //(삭제하기)
	public ModelAndView deleteBoard(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
		
		sampleService.deleteBoard(commandMap.getMap());
		
		return mv;
	}
}