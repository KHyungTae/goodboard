/*package first.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

//업로드 기능에 사용할 코드
@Component("fileUtils") //@Component애노테이션을 이용하여 이 객체의 관리를 스프링이 담당하도록 하기 위함
public class FileUtils {

	파일이 저장될 위치를 선언
	private static final String filePath = "C:\\dev\\file\\";
	
	public List<Map<String, Object>> parseInsertFileInfo(Map<String, Object> map,
			HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		//클라이언트에서 전송된 파일 정보를 담아서 반환을 해줄 list. (다중파일전송을 하도록 구성)
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> listMap = null;
		
		//ServiceImpl영역에서 전달해준 map에서 신규 생성되는 게시글의 번호를 받아오도록함.
		String boardIdx = (String)map.get("IDX");
		//파일을 저장할 경로에 해당 폴더가 없으면 폴더를 생성하도록 하였다.
		File file = new File(filePath);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		while(iterator.hasNext()) {
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			//파일의 정보를 받아서 새로운 이름으로 변형하는 부분이다.
			if(multipartFile.isEmpty() == false) {
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				//getRandomString()메서드를 이용하여 32자리의 랜덤한 파일이름을 생성하고 원본파일의 확장자를 다시 붙여줌.
				storedFileName = CommonUtils.getRandomString() + originalFileExtension;
				
				//서버에 실제 파일을 저장하는 부분
				file = new File(filePath + storedFileName);
				//multipartFile.transferTo()메서드를 이용하여 지정된 경로에 파일을 생성
				multipartFile.transferTo(file);
				
				//위에 만든 정보를 list에 추가하는 부분
				listMap = new HashMap<String, Object>();
				listMap.put("BOARD_IDX", boardIdx);
				listMap.put("ORIGINAL_FILE_NAME", originalFileName);
				listMap.put("STORED_FILE_NAME", storedFileName);
				listMap.put("FILE_SIZE", multipartFile.getSize());
				list.add(listMap);
			}
		}
		
		return list;
	}
	
	public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
    	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
    	
    	MultipartFile multipartFile = null;
    	String originalFileName = null;
    	String originalFileExtension = null;
    	String storedFileName = null;
    	
    	//클라이언트에서 전송된 파일 정보를 담아서 반환을 해줄 list
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null; 
        
        //serviceImpl영역에서 전달해준 map에서 신규 생성되는 게시글의 번호를 받아오도록함.
        String boardIdx = (String)map.get("IDX");
        
        String requestName = null;
        String idx = null;
        
        
        while(iterator.hasNext()){
        	//파일의 정보를 받아서 새로운 이름으로 변형하는 부분이다.
        	multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        	if(multipartFile.isEmpty() == false){
        		originalFileName = multipartFile.getOriginalFilename();
        		originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        		//getRandomString()메서드를 이용하여 32자리의 랜덤한 파일이름을 생성하고 원본파일의 확장자를 다시 붙여줌.
        		storedFileName = CommonUtils.getRandomString() + originalFileExtension;
        		
        		multipartFile.transferTo(new File(filePath + storedFileName));
        		
        		listMap = new HashMap<String,Object>();
        		listMap.put("IS_NEW", "Y");
        		listMap.put("BOARD_IDX", boardIdx);
        		listMap.put("ORIGINAL_FILE_NAME", originalFileName);
        		listMap.put("STORED_FILE_NAME", storedFileName);
        		listMap.put("FILE_SIZE", multipartFile.getSize());
        		list.add(listMap);
        	}
        	 //else문은 즉, multipartFile이 비어있는 경우(multipartFile.isEmpty() == true)이다.
        	  //파일정보가 없는 경우에는 해당 파일정보가 기존에 저장이 되어있던 내용인지 아니면 단순히 빈 파일인지 구분해야한다.
        	  //이 것을 구분하는게 아래 코드이다.
        	else{
        		 //html태그에서 file태그의 name값을 가져온다. jsp에서 name="file_숫자"으로 만든 값을 가져오는 
        		 // 메서드가 multipartFile.getName()이다. 
        		requestName = multipartFile.getName();
        		 //이 name에서 뒤에 있는 숫자를 가져오게 되면, map에서 IDX_숫자 값이 있는지 여부를 판별한다.
        		  //(IDX_"라는 키값에 해당 태그의 네임에서 숫자를 가져와서 합쳐준다 -->예: IDX_1, IDX_2 
            	idx = "IDX_"+requestName.substring(requestName.indexOf("_")+1);
            	 //화면에서 넘어온 값 중에서 IDX_숫자 키가 있는지 확인하는 것
            	 //IDX_숫자 키가 있다면 그것은 기존에 저장이 되어있던 파일 정보임을 의미하는 "N"이라는 값을 "IS_NEW"키로 저장 
            	if(map.containsKey(idx) == true && map.get(idx) != null){
            		listMap = new HashMap<String,Object>();
            		listMap.put("IS_NEW", "N");
            		listMap.put("FILE_IDX", map.get(idx));
            		list.add(listMap);
            	}
        	}
        }
		return list;
	}
}*/
