/*package first.common.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import first.common.common.CommandMap;
import first.common.service.CommonService;

@Controller
public class CommonController {

	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	@RequestMapping(value="/common/downloadFile.do")
	// 기존 첨부파일 업로드 할때는 HttpServletRequest request가 추가 되었는데 이번에는 response가 추가되었다.
	// 화면에서 서버로 어떤 요청을 할때는 request가 전송, 반대로 서버에서 화면으로 응답을 할때는 response 응답내용을 담는다.
	// 즉, 다운로드가 가능한 데이터 전송이라는 것은 파일정보를 response에 담아주는 것을 의미
	public void downloadFile(CommandMap commandMap, HttpServletResponse response) throws Exception {
		//commonService.selectFileInfo를 통해 첨부파일의 정보를 가져온다.
		Map<String, Object> map = commonService.selectFileInfo(commandMap.getMap());
		String storedFileName = (String)map.get("STORED_FILE_NAME");
		String originalFileName = (String)map.get("ORIGINAL_FILE_NAME");
		
		// 실제로 파일이 저장된 위치에서 해당 첨부파일을 읽어서 byte[]형태로 변환.
		// 그리고 여기에 FileUtils 우리가 만든 클래스가 아니라 org.apache.commons.io 패키지의 FileUtils클래스이다.
		// 거기에 byte[]에 readFileToByteArray()메소드를 사용하여 파일을 저장하는 위치에 저장된 파일명을 붙여 가져오도록함.
		byte fileByte[] = FileUtils.readFileToByteArray(new File("C:\\dev\\file\\"+storedFileName));
		
		// 읽어들인 파일정보를 화면에서 다운로드 할 수 있도록 변환을 하는 부분이다.
		// 인터넷을 통해서 데이터를 전송하면 request나 response에는 전송할 데이터 뿐만이 아니라 여러 정보가 담겨있는
		// 것을 설정해 주는 부분이 밑에 5줄 부분이다. 
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		// response.setHeader에 "content-Disposition"("multipart-form/data")이라는 속성을 지정하고,
		// attachment로 설정하고 있다. 이는 첨부파일을 의미한다.
		// 또, fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";"이 부분은 첨부파일의 이름을 지정해주는 역할을 수행 
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		
		response.getOutputStream().flush();  //사용자가 원할때 버퍼를 비워주는 것으로 버퍼를 비우는 것은 IO에서는 출력하는 것 이겠고, 
											 //네트워크에서는 버퍼의 내용을 클라이언트나 서버로 보내는 것.
		response.getOutputStream().close();  //정리가 끝나면 닫아주는 것.
	}
}*/