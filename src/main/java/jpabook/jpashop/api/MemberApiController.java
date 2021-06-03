package jpabook.jpashop.api;

import jpabook.jpashop.domains.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/createMember")
    public MemberDTO1 saveMember(@RequestBody @Valid MemberRequest memberIn){

        Member member = new Member();
        member.setName(memberIn.getName());

        Long memberId = memberService.join(member);

        return new MemberDTO1(memberId);
    }

    @PostMapping("/api/v2/updateMember/{memberId}")
    public UpdateMemberDTO updateMember(@PathVariable("memberId") Long memberId,
                                        @RequestBody MemberRequest updateMember) {

        memberService.updateMember(memberId, updateMember.getName());

        Member member = memberService.findOne(memberId);

        return new UpdateMemberDTO(member.getId(), member.getName());
    }

    @GetMapping("/api/v2/memberList")
    public Result getMemberList(){

        List<Member> members = memberService.findMembers();

        List<MemberDTO2> list = members.stream()
                                        .map(m -> new MemberDTO2(m.getName()))
                                        .collect(Collectors.toList());
        return new Result(list, list.size());

    }

    @Data
    @AllArgsConstructor
    class Result<T> {
        private T data;
        private int count;
    }

    @Getter
    @AllArgsConstructor
    static class MemberDTO1{
        private Long id;
    }

    @Data
    static class MemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    private class UpdateMemberDTO {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    private class MemberDTO2 {
        private String name;
    }
}
