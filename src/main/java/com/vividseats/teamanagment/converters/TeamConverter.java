package com.vividseats.teamanagment.converters;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.vividseats.teamanagment.domain.Team;
import com.vividseats.teamanagment.dto.TeamDTO;

@Component("teamConverter")
public class TeamConverter implements ModelConverter<TeamDTO, Team> {

  private ModelMapper modelMapper;

  public TeamConverter() {
    this.modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
  }


  @Override
  public Optional<TeamDTO> convertToDTO(Optional<Team> entity) {
    if (!entity.isPresent()) {
      return Optional.empty();
    }

    TeamDTO dto = modelMapper.map(entity.get(), TeamDTO.class);

    return dto == null ? Optional.empty() : Optional.of(dto);
  }

  @Override
  public Optional<Team> convertToEntity(Optional<TeamDTO> dto) {
    if (!dto.isPresent()) {
      return Optional.empty();
    }

    Team team = modelMapper.map(dto.get(), Team.class);

    return team == null ? Optional.empty() : Optional.of(team);
  }

}
