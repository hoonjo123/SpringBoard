package com.spring.board.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
//관점지향 AOP의 핵심 구성요소로 관심사를 모듈화한 것
@Component
//Spring Bean
@Slf4j

public class AopLogService {

//Aop의 대상이 되는 controller, service등을 정의하는 어노테이션
//    @Pointcut("excution(* com.spring.board..controller..*.*(..))") -> package
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerPointcut(){
    }
//    @Before("controllerPointcut()")
//    public void beforeController(JoinPoint joinPoint){
//        log.info("Before Controller");
//        ServletRequestAttributes servletRequestAttributes =
//                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest req = servletRequestAttributes.getRequest();
//
//        //json 형태로 사용자의 요청을 조립하기 위한 로직
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode objectNode = objectMapper.createObjectNode();
//        objectNode.put("Method Name", joinPoint.getSignature().getName());
//        objectNode.put("CRUD Name", req.getMethod());
//
//        Map<String, String[]> paramMap = req.getParameterMap();
//
//        /*ObjectNode는 트리구조이기때문에 유연하게 json으로 변환가능
//        또다른 Json으로 출력을 하겠다.*/
//        ObjectNode objectNodeDetail = objectMapper.valueToTree(paramMap);
//        objectNode.set("user inputs", objectNodeDetail);
//        log.info("user request info" + objectNode);
//        //메서드가 실행되기 전에 인증, 입력값 검증등을 수행하는 용도로 사용하는 사전단계
//    }
//
//    @After("controllerPointcut()")
//
//    public void afterController(){
//        log.info("End Controller");
//    }



//방식2. around사용 - 가장 빈번히 사용되는 방식이다.
    @Around("controllerPointcut()")
//    @Order(2)
    //joinpoint : 대상으로 하는 컨트롤러에 특정매서드를 의미 (aspect가 적용될 수 있는 프로그램 실행의 특정지점)
    public Object ControllerLogger(ProceedingJoinPoint proceedingJoinPoint){
//        log.info("request method" + proceedingJoinPoint.getSignature().toString());


//        사용자의 요청값을 출력하기 위해 httpServletRequest객체를 꺼내는 로직
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = servletRequestAttributes.getRequest();

        //json 형태로 사용자의 요청을 조립하기 위한 로직
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("Method Name", proceedingJoinPoint.getSignature().getName());
        objectNode.put("CRUD Name", req.getMethod());

        Map<String, String[]> paramMap = req.getParameterMap();

        /*ObjectNode는 트리구조이기때문에 유연하게 json으로 변환가능
        또다른 Json으로 출력을 하겠다.*/
        ObjectNode objectNodeDetail = objectMapper.valueToTree(paramMap);
        objectNode.set("user inputs", objectNodeDetail);
        log.info("user request info" + objectNode);


        try {
            //본래의 컨트롤러 메서드 호출하는 부분
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            log.info("End Controller");
        }
    }
}
