package com.its.member;

import com.its.member.dto.MemberDTO;
import com.its.member.entity.MemberEntity;
import com.its.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TestClass {
    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(int i) {
        MemberDTO member = new MemberDTO("charm"+i, "1"+i, "이름"+i, 1+i, "010-"+i);
        return member;
    }

    @Test
    @Transactional
    @Rollback
    public void saveTest() {
        Long saveId = memberService.save(newMember(1));
        MemberDTO saveDTO = memberService.findById(saveId);
        assertThat(newMember(1).getMemberEmail()).isEqualTo(saveDTO.getMemberEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void loginTest() {
        memberService.save(newMember(1));
        MemberDTO loginDTO = new MemberDTO("charm@nav.com", "1234");
        loginDTO = memberService.login(loginDTO);
        assertThat(loginDTO).isNotNull();

//        final String memberEmail = "로그인용이메일";
//        final String memberPassword = "로그인용비번";
//        String memberName = "로그인용이름";
//        int memberAge = 99;
//        String memberMobile = "로그인용전화번호";
//        MemberDTO memberDTO = new MemberDTO(memberEmail, memberPassword);
//        memberService.save(memberDTO);
//        MemberDTO loginMemberDTO = new MemberDTO();
//        loginMemberDTO.setMemberEmail(memberEmail);
//        loginMemberDTO.setMemberPassword(memberPassword);
//        MemberDTO loginResult = memberService.login(loginMemberDTO);
//        assertThat(loginResult).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void listTest() {
        memberService.save(newMember(1));
        MemberDTO memberDTO2 = new MemberDTO("123@nav.com", "1234", "이름2", 10, "010-222-222");
        memberService.save(memberDTO2);
        MemberDTO memberDTO3 = new MemberDTO("999@nav.com", "1234", "이름3", 30, "010-333-333");
        memberService.save(memberDTO3);
        List<MemberDTO> memberDTOList = memberService.findAll();
        assertThat(memberDTOList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    public void findByIdTest() {
        Long saveId = memberService.save(newMember(1));
        assertThat(memberService.findById(saveId)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        Long deleteId = memberService.save(newMember(1));
        memberService.delete(deleteId);
        assertThat(memberService.findById(deleteId)).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        Long saveId = memberService.save(newMember(1));
        MemberDTO saveDTO = memberService.findById(saveId);
        saveDTO.setMemberEmail("update@nav.com");
        memberService.save(saveDTO);
        MemberDTO updateDTO = memberService.findById(saveId);
        assertThat(newMember(1).getMemberEmail()).isEqualTo(updateDTO.getMemberEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void duplicateTest() {
        memberService.save(newMember(1));
        MemberDTO newDTO = new MemberDTO();
        newDTO.setMemberEmail("charm@nav.com");
        MemberEntity duplicateEntity = memberService.emailCheck(newDTO.getMemberEmail());
        assertThat(duplicateEntity).isNotNull();
    }

    @Test
    @DisplayName("회원 데이터 저장")
    public void memberSave() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            memberService.save(newMember(i));
        });
    }
}
