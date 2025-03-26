package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

// 스프링이 MemberRepository를 빈컨테이너에 관리해줌
@Repository
@RequiredArgsConstructor
public class MemberRepository {
    // 엔티티 매니저를 의존성 주입해줌
    //@PersistenceContext
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("SELECT m FROM Member m WHERE m.name = :name")
                .setParameter("name", name)
                .getResultList();
    }
}
