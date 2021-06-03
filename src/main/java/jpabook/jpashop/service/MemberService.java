package jpabook.jpashop.service;

import jpabook.jpashop.domains.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

/*
    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
*/
    //롬복에서 final 멤버변수에 대해서 셋팅함.
    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long join(Member member){
        //중복회원
        validateDuplicationMember(member);
        memberRepository.save(member);
        return member.getId();      // 영속성 context에서 값이 보장됨.
    }

    private void validateDuplicationMember(Member member) {
        //Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원전체조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void updateMember(Long memberId, String memberName){
        Member member = memberRepository.findOne(memberId);
        member.setName(memberName);
    }
}
