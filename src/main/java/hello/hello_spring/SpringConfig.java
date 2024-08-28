package hello.hello_spring;

import hello.hello_spring.repository.*;
import hello.hello_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration  // Configuraion도 spring bean으로 관리됨. DI
public class SpringConfig {
//    private final DataSource dataSource;  // for JdbcTemplate
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;}
    private final EntityManager em;    // for JPA
    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;}
    @Bean  // 아래 메서드의 반환 객체가 auto DI
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}