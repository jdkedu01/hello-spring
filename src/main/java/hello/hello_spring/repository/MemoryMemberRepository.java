package hello.hello_spring.repository;
import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
//동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
@Repository
public class MemoryMemberRepository implements MemberRepository
{
    private static Map<String, Member> store = new HashMap<>();
    //private static List<Member> store = new ArrayList<>();
    //private static long sequence = 0L;
    @Override
    public Member save(Member member) {
        //member.setId(++sequence);
        store.put(member.getId(), member);
        //store.add(member);
        return member;
    }
    @Override
    public Optional<Member> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }
    public void clearStore() {
        store.clear();
    }
}