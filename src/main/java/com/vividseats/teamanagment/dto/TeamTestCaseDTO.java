package com.vividseats.teamanagment.dto;

import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Component
@Scope("prototype")
@Data
public class TeamTestCaseDTO {

  private long idTeam;

  @JsonIgnore
  private Set<MemberDTO> memberDTO = new HashSet<>();

  private Long celebrity = -1l;

  private String message;

}
