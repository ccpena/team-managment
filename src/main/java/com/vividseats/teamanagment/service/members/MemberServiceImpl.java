package com.vividseats.teamanagment.service.members;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.vividseats.teamanagment.converters.MemberConverter;
import com.vividseats.teamanagment.converters.ModelConverter;
import com.vividseats.teamanagment.domain.Member;
import com.vividseats.teamanagment.dto.MemberDTO;
import com.vividseats.teamanagment.repository.MemberRepository;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

  private MemberRepository memberRepository;


  private ModelConverter<MemberDTO, Member> modelConverter;

  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
    this.modelConverter = new MemberConverter();
  }

  @Override
  public Optional<MemberDTO> findById(Long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<MemberDTO> create(MemberDTO memberDTO) {
    Optional<Member> member = modelConverter.convertToEntity(Optional.of(memberDTO));


    if (member.isPresent()) {
      memberRepository.save(member.get());
      if (member.get().getKnows() != null) {
        member.get().getKnows().forEach(m -> {
          memberRepository.save(m);
        });
      }

      return Optional.of(memberDTO);
    }

    return Optional.empty();

  }



}
