package com.spring.board.author.Controller;

import com.spring.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

    @RestController
//slf4j어노테이션을(롬복)을 통해 쉽게 logback 로그라이브러리 사용가능
    @Slf4j
    public class TestController {
        //        slf4j어노테이션 사용하지 않고, 직접 라이브러리 import하여 로거 생성가능
//        private static final Logger logger = LoggerFactory.getLogger(LogTestController.class);
        @Autowired
        private AuthorService authorService;
        @GetMapping("log/test1")
        public String testMethod1(){
            log.debug("debug 로그입니다.");
            log.info("info 로그입니다.");
            log.error("error 로그입니다.");
            return "ok";
        }

        @GetMapping("exception/test1/{id}")
        public String exceptionTestMethod(@PathVariable Long id){
            authorService.findById(id);
            return "ok";
        }
    }