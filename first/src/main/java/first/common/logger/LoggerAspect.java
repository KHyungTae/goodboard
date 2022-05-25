package first.common.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

//AOP설정
//LoggerAspect클래스는 Controller, Service, DAO가 실행될 떄, 어떤 계층의 어떤 메서드가 실행되었는지를 보여주는 역할을 한다.

/* @Aspect애노테이션은 (action-servlet.xml과 context-aspect.xml에서 <aop:sapectj-autoproxy/>태그를 사용했는데 
   @Aspect애노테이션을 통해 bean을 등록시켜주는 역할을 한다. */

/* AspectJ를 이용한 장점 한가지: AOP를 설정할 때는 하나 이상의 포인트컷과 어드바이스를 가져야하는데, AspectJ를 사용하면
 * 같이 애노테이션을 이용하여 어드바이스에 포인트컷을 직접 지정해 줄 수 있다. (이런방식이 되지 않으면 모두 따로 지정해야한다.) */ 
@Aspect
public class LoggerAspect {
	protected Log log = LogFactory.getLog(LoggerAspect.class);
	static String name = "";
	static String type = "";

	//Advice를 이용한 5가지 종류 중 Around Advice이다.
	
	/* execution은 포인트컷 표현식 부분이다. (여러가지 포인트컷 지사자를 사용할 수 있는데 그중 하나이다.) execution(): 가장
	 * 대표적이고 강력한 지시자로, 접근제어자, 리턴타입, 메서드, 파라미터 타입, 예외 타입 등을 조합해서 메서드까지 선택가능한 가장 
	 * 정교한 포인트컷을 만들 수 있다. */
	
	// * first..controller.*Controller.*(..)이 부분에서 first.. 부분은 first패키지 밑의 모든 서브패키지를 의미
	// controller. 은 controller 패키지 밑의 클래스와 인터페이스만을 지정.
	// *Controller. 은 Controller라는 이름으로 끝나는 것을 의미
	// *(..) 은 모든 메서드를 의미
	// or, and, not (||, &&, !) 3가지로 표현식을 조합 할 수 있다.
	@Around("execution(* first..controller.*Controller.*(..)) or execution(* first..service.*Impl.*(..)) or execution(* first..dao.*DAO.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		type = joinPoint.getSignature().getDeclaringTypeName();
		if (type.indexOf("Controller") > -1) {
			name = "Controller \t: ";
		} else if (type.indexOf("Service") > -1) {
			name = "ServiceImpl \t: ";
		} else if (type.indexOf("DAO") > -1) {
			name = "DAO \t\t: ";
		}
		log.debug(name + type + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
}
