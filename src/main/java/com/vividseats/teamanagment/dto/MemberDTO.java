package com.vividseats.teamanagment.dto;

import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Component
@Scope("prototype")
public class MemberDTO {

  private Long id;
  @JsonIgnore
  private TeamDTO team;
  @JsonIgnore
  private Set<MemberDTO> knows = new HashSet<>();
  private Set<Long> peopleKnown = new HashSet<>();

  public MemberDTO() {

  }

  public MemberDTO(Long memberId, TeamDTO team, Set<Long> membersKnown) {
    this.id = memberId;
    this.team = team;
    this.peopleKnown = membersKnown;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id.intValue();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MemberDTO other = (MemberDTO) obj;
    if (id != other.id)
      return false;


    return true;
  }



}
