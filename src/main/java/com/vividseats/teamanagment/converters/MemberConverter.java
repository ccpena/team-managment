package com.vividseats.teamanagment.converters;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import com.vividseats.teamanagment.domain.Member;
import com.vividseats.teamanagment.domain.Team;
import com.vividseats.teamanagment.dto.MemberDTO;

public class MemberConverter implements ModelConverter<MemberDTO, Member> {

  private ModelMapper modelMapper = new ModelMapper();


  @Override
  public Optional<MemberDTO> convertToDTO(Optional<Member> entity) {
    if (!entity.isPresent()) {
      return Optional.empty();
    }

    MemberDTO dto = modelMapper.map(entity.get(), MemberDTO.class);

    return dto == null ? Optional.empty() : Optional.of(dto);
  }

  @Override
  public Optional<Member> convertToEntity(Optional<MemberDTO> dto) {
    if (!dto.isPresent()) {
      return Optional.empty();
    }

    Converter<MemberDTO, Member> myConverter = new Converter<MemberDTO, Member>() {
      public Member convert(MappingContext<MemberDTO, Member> context) {
        MemberDTO source = context.getSource();
        Member destination = new Member();

        destination.setMemberId(source.getId());

        Team team = new Team();
        team.setId(source.getTeam().getId());
        destination.setTeam(team);

        Set<Member> knows = new HashSet<>();
        if (source.getPeopleKnown() != null) {
          source.getPeopleKnown().forEach(knowId -> {
            Member m = new Member();
            m.setMemberId(knowId);
            m.setTeam(team);
            knows.add(m);
          });
        }
        destination.setKnows(knows);


        return destination;
      }
    };

    modelMapper.addConverter(myConverter);
    Member Member = modelMapper.map(dto.get(), Member.class);

    return Member == null ? Optional.empty() : Optional.of(Member);
  }

}
