package com.its.member.service;

import com.its.member.dto.MemberDTO;
import com.its.member.entity.MemberEntity;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);
        Long saveId = memberRepository.save(memberEntity).getId();
        return saveId;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> om = memberRepository.findById(id);
        if (om.isPresent()) {
            MemberEntity memberEntity = om.get();
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            return memberDTO;
            // return MemberDTO.toDTO(om.get());
        } else {
            return null;
        }
    }

    public MemberDTO login(MemberDTO memberDTO) {
        MemberEntity member = emailCheck(memberDTO.getMemberEmail());
        if (member.getMemberPassword().equals(memberDTO.getMemberPassword())) {
            return MemberDTO.toDTO(member);
        } else {
            return null;
        }
    }

    public MemberEntity emailCheck(String memberEmail) {
        Optional<MemberEntity> member = memberRepository.findByMemberEmail(memberEmail);
        if (member.isPresent()) {
            return member.get();
        } else {
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity list : memberEntityList) {
            memberDTOList.add(MemberDTO.toDTO(list));
        }
        return memberDTOList;
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(memberEntity);
    }
}
