//package jpabook.jpashop.service;
//
//import jpabook.jpashop.domain.Member;
//import jpabook.jpashop.repository.MemberRepository;
//import org.hibernate.jdbc.Expectation;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class MemberServiceTest {
//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    @Rollback(value = false)
//    public void 회원가입() throws Exception{
//        //given
//        Member member = new Member();
//        member.setName("kim");
//        //when
//        Long id = memberService.join(member);
//        //then
//        Assertions.assertEquals(member, memberRepository.findOne(id));
//    }
//
//    @Test
//    public void 중복가입_유효성검사() throws Exception{
//        //given
//        Member m1 = new Member();
//        m1.setName("kim");
//        Member m2 = new Member();
//        m2.setName("kim");
//        // when
//        memberService.join(m1);
//        // then
//        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
//            memberService.join(m2);
//        });
//
//        assertEquals("이미 존재하는 회원입니다.", e.getMessage());
//    }
//
//
//}