package com.vividseats.teamanagment.service.members;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vividseats.teamanagment.converters.MemberConverter;
import com.vividseats.teamanagment.domain.Member;
import com.vividseats.teamanagment.dto.MemberDTO;
import com.vividseats.teamanagment.exceptions.MemberCreatedException;
import com.vividseats.teamanagment.exceptions.MemberNotFoundException;
import com.vividseats.teamanagment.repository.MemberRepository;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

  private MemberRepository memberRepository;


  private MemberConverter modelConverter;

  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
    this.modelConverter = new MemberConverter();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<MemberDTO> findById(Long id) {
    Optional<Member> member = memberRepository.findById(id);

    return modelConverter.convertToDTO(member);
  }

  @Override
  public Optional<MemberDTO> create(MemberDTO memberDTO) {

    validateIfKnowsSameMember(memberDTO);

    Member member = modelConverter.convertToEntity(Optional.ofNullable(memberDTO)).get();

    Member newMember = memberRepository.findByMemberId(member.getMemberId());
    if (newMember == null) {
      newMember = modelConverter.convertToEntity(Optional.of(memberDTO)).get();
    }

    Set<Member> knows = new HashSet<>();
    member.getKnows().stream().forEach(e -> {

      Member MemberFound = memberRepository.findByMemberId(e.getMemberId());
      if (MemberFound == null) {
        MemberFound = memberRepository.save(e);
      }
      knows.add(MemberFound);
    });

    newMember.setKnows(knows);
    newMember = memberRepository.save(newMember);

    return modelConverter.convertToDTO(Optional.ofNullable(newMember));

  }

  private void validateIfKnowsSameMember(MemberDTO memberDTO) {
    if (memberDTO == null) {
      throw new MemberNotFoundException("Member not found");
    }

    Long memberId = memberDTO.getId();
    if (memberDTO.getPeopleKnown() != null) {
      Optional<Long> knownSelf = memberDTO.getPeopleKnown().stream()
          .filter(id -> id.longValue() == memberId.longValue()).findFirst();
      if (knownSelf.isPresent()) {
        throw new MemberCreatedException(
            String.format("The member %d can not known self", memberId));
      }
    }


  }

  @Override
  public Set<MemberDTO> findByTeamId(Long teamId) {
    Set<Member> membersTeam = memberRepository.findByTeamId(teamId);

    return modelConverter.convertTo(membersTeam);
  }



}
