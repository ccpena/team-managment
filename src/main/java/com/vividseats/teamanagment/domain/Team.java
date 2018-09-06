package com.vividseats.teamanagment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Team {

  public Team() {}

  @Id
  @GeneratedValue
  @Column(name = "TEAM_ID")
  private Long id;

  @Column(name = "NAME")
  private String name;


}
