package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // jpa em는 기본적으로 트랜젝션 안에서 움직이고 readOnly = true로 읽기전용 으로 쓰면서 쓰기 기능 메서드만 Transactional을 따로 붙혀준다.
@RequiredArgsConstructor
public class MemberService {
    final private MemberRepository memberRepository;

    /*
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //  맴버의 중복이 있을 경우 예외를 발생, Member 엔티티에 name컬럼 unique 제약조건 줌
    private void validateDuplicateMember(Member member) {
        List<Member> byName = memberRepository.findByName(member.getName());

        if (!byName.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //  목록조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    @Transactional
    public Long update(Long id, String name) {
        Member getMember = memberRepository.findOne(id);
        getMember.setName(name);
        return getMember.getId();
    }
}
