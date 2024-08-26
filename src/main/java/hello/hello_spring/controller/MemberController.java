package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
       this.memberService = memberService;
    }

    @GetMapping(value = "/members/new")
    public String createForm22() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(MemberForm form) {
        //HTML form에서 Member말고 Product이면 ProductForm Class 별도로 준비해서 사용
        Member member = new Member();
        //여러개도 받을 수 있음
        //System.out.println(form.getId());
        member.setId(form.getId());
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }
    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}