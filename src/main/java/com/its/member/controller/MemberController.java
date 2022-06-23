package com.its.member.controller;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm() {
        return "memberPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "memberPages/login";
    }

    @GetMapping("/login-form")
    public String loginForm(){
        return "memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session) {
        MemberDTO loginDTO = memberService.login(memberDTO);
        if (loginDTO != null) {
            session.setAttribute("loginId", loginDTO.getId());
            session.setAttribute("loginEmail", loginDTO.getMemberEmail());
            return "memberPages/main";
        } else {
            return "memberPages/login";
        }
    }

    @GetMapping("/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        System.out.println("memberDTOList = " + memberDTOList);
        model.addAttribute("memberList", memberDTOList);
        return "memberPages/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/detail";
    }

    @GetMapping("/ajax/{id}")
    public @ResponseBody MemberDTO ajaxFindById(@PathVariable Long id) {
        MemberDTO memberDTO = memberService.findById(id);
        return memberDTO;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        memberService.delete(id);
        if (session.getAttribute("loginEmail") != "관리자") {
            session.invalidate();
        }
        return "index";
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteMapping(@PathVariable Long id, HttpSession session) {
        memberService.delete(id);
        if (session.getAttribute("loginEmail") != "관리자") {
            session.invalidate();
        }
        return "ok";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("member", memberService.findById(id));
        return "memberPages/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO, Model model) {
        memberService.update(memberDTO);
        model.addAttribute("member", memberService.findById(memberDTO.getId()));
        return "redirect:/member/" + memberDTO.getId();
    }

    @PutMapping("/update")
    public @ResponseBody String putUpdate(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "ok";
    }

    @PostMapping("/dup-check")
    public @ResponseBody String duplicateCheck(@RequestParam String memberEmail) {
        if (memberService.emailCheck(memberEmail) == null){
            return "ok";
        } else {
            return "no";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

}
