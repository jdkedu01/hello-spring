package hello.hello_spring.service;
import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    //@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // 회원가입
   public String join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 ID 회원입니다.");
                });
    }
    // 회원 조회
    public List<Member> findMembers() {
        // 여기서도 별도의 조건이 있어면 코드로 표현함
        // 예를 들어 현재의 리소스 상황에따라 상위 N개로 제한할 수 있음
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(String memberId) {
        return memberRepository.findById(memberId);
    }
}