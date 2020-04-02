package com.tumblbug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        /*
        이 메소드로 인해 내장 톰캣을 사용할 수 있다.
            [내장 톰캣 사용이유]
            - 내장 톰캣 사용 시 언제 어디서나 같은 환경에서 스프링 부트를 배포할 수 있다.
            - 서버가 여러대일 경우 같은 환경에서 배포가 이루어지는 것을 보장한다.
         */
        SpringApplication.run(Application.class, args);

    }
}
