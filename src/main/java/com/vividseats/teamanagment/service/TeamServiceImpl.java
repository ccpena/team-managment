package com.vividseats.teamanagment.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vividseats.teamanagment.converters.ModelConverter;
import com.vividseats.teamanagment.converters.TeamConverter;
import com.vividseats.teamanagment.domain.Team;
import com.vividseats.teamanagment.dto.TeamDTO;
import com.vividseats.teamanagment.repository.TeamRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class TeamServiceImpl implements TeamService {

  private TeamRepository teamRepository;

  private ModelConverter<TeamDTO, Team> modelConverter;


  public TeamServiceImpl(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
    this.modelConverter = new TeamConverter();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TeamDTO> findById(Long id) {
    Optional<Team> team = teamRepository.findById(id);
    return modelConverter.convertToDTO(team);
  }

  @Override
  public Optional<TeamDTO> create(TeamDTO teamDTO) {
    Optional<Team> team = modelConverter.convertToEntity(Optional.ofNullable(teamDTO));
    Optional<TeamDTO> teamDTOCreated = Optional.empty();

    if (team.isPresent()) {
      Team teamCreated = teamRepository.save(team.get());
      teamDTOCreated = modelConverter.convertToDTO(Optional.ofNullable(teamCreated));
    }

    return teamDTOCreated;

  }

}
