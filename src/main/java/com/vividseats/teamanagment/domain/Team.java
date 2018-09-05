package com.vividseats.teamanagment.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Entity
@Data
public class Team {

  @Id
  @GeneratedValue
  @Column(name = "TEAM_ID")
  private Long id;

  private String name;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
  List<Member> members = new ArrayList<Member>();

}
