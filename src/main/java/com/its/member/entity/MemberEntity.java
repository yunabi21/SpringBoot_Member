package com.its.member.entity;

import com.its.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class MemberEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50, unique = true)
  private String memberEmail;

  @Column(length = 20)
  private String memberPassword;

  @Column(length = 20)
  private String memberName;

  @Column
  private int memberAge;

  @Column(length = 30)
  private String memberPhone;

  public static MemberEntity toEntity(MemberDTO memberDTO) {
    MemberEntity memberEntity = new MemberEntity();
    memberEntity.setMemberEmail(memberDTO.getMemberEmail());
    memberEntity.setMemberPassword(memberDTO.getMemberPassword());
    memberEntity.setMemberName(memberDTO.getMemberName());
    memberEntity.setMemberAge(memberDTO.getMemberAge());
    memberEntity.setMemberPhone(memberDTO.getMemberPhone());
    return memberEntity;
  }

  public static MemberEntity toUpdateEntity(MemberDTO memberDTO) {
    MemberEntity memberEntity = new MemberEntity();
    memberEntity.setId(memberDTO.getId());
    memberEntity.setMemberEmail(memberDTO.getMemberEmail());
    memberEntity.setMemberPassword(memberDTO.getMemberPassword());
    memberEntity.setMemberName(memberDTO.getMemberName());
    memberEntity.setMemberAge(memberDTO.getMemberAge());
    memberEntity.setMemberPhone(memberDTO.getMemberPhone());
    return memberEntity;
  }
}
