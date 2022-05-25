 package first.common.resolver;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import first.common.common.CommandMap;

/* MapArguemtResolver를 등록하지 않았다면, 컨트롤러에서 request.getParameter메서드 등을 이용하여 
 * 하나하나씩 값을 가져와야하기 때문에 번거롭고 코드가 길어진다. */

//HandlerMethodArgumentResolver인터페이스를 인플먼츠하면 두가지 메서드를 반드시 구현해야한다.
//supportsParameter()메서드, resolveArgument()메서드
public class CustomMapArgumentResolver implements HandlerMethodArgumentResolver {

	@Override   //supportsParameter()메서드는 Resolver가 적용하는지 검사하는 역할
	//supportsParameter()메서드는 컨트롤러의 파라미터가 CommandMap클래스인지 검사하도록 함.
	public boolean supportsParameter(MethodParameter parameter) {
		// TODO Auto-generated method stub
		return CommandMap.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override  //resolveArgument()메서드는 파라미터와 기타 정보를 받아서 실제 객체를 반환(중요메서드)
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// TODO Auto-generated method stub
		//CommandMap객체를 생성
		CommandMap commandMap = new CommandMap();
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		//Enumeration과 Iterator인터페이스는 반복문을 통해 데이터를 한번에 출력할 수 있도록 도와준다.
		Enumeration<?> enumeration = request.getParameterNames();
		
		String key = null;
		String[] values = null;
		/* 처음 반복문에 들어갈 때 enumeration.hasMoreElements()가 현재 커서 이후에 요서들이 있는지 여부를 체크
		 * boolean타입을 반환, (현재 커서 이후에 요소들이 있는지 여부 체크) 요소가 있으면 true, 없으면 false를 반환 */
		while(enumeration.hasMoreElements()) {
			//enumeration.nextElement()메소드를 통해 해당 요소를 꺼내고 현재 커서를 다음 요소를 가르키게 한다.
			key = (String) enumeration.nextElement();
			values = request.getParameterValues(key);  //request에 담겨있는 모든 키(key)와 값(value)을
			if(values != null) {
				commandMap.put(key, (values.length > 1) ? values:values[0]);  //commandMap에 저장
			}
		}
		return commandMap;  //모든 파라미터가 담겨있는 commandMap을 반환.
	}

	
}
