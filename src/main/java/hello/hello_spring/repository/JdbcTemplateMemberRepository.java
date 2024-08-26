package hello.hello_spring.repository;
import hello.hello_spring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        //JdbcTemplate은 DI 대상이 아님. DI 받는 DataSource로부터 Derive
    }
    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        //jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        jdbcInsert.withTableName("member");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", member.getId());
        parameters.put("name", member.getName());
        jdbcInsert.execute(parameters);
        // 앞의 JDBCTemplate 예제의 insert와는 다른 접근방법. insert문을 사용
//        String insertQuery = "INSERT INTO memeber (id, name) VALUES (?, ?)";
//        jdbcTemplate.update(insertQuery, member.getId(), member.getName());
        return member;
    }
    @Override
    public Optional<Member> findById(String id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?",
                memberRowMapper(), id);
        return result.stream().findAny();
    }
    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?",
                memberRowMapper(), name);
        return result.stream().findAny();
    }
    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getString("id"));
            member.setName(rs.getString("name"));
            return member;
            // 결과가 나오늘 것을 RowMapper가 매핑.역 ROM (DB 결과를 객체(Domain Entity)로 변환
            // RS와 행번호로부터 하나의 객체를 생성. 건별 처리
        };
    }
    // 위의 함수는 아래의 함수로부터 람다 축약된 것임
//    private RowMapper<Member> memberRowMapper() {
//        return new RowMapper<Member>() {
//            @Override
//            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Member member = new Member();
//                member.setId(rs.getString("id"));
//                member.setName(rs.getString("name"));
//                return member;
//            }
//        }
//    }
}