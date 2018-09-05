package com.vividseats.teamanagment.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Member {

  @Id
  @Column(name = "MEMBER_ID")
  private Long memberId;

  @ManyToOne
  @JoinColumn(name = "TEAM_ID")
  private Team team;


  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "known_members", joinColumns = @JoinColumn(name = "MEMBER_ID"),
      inverseJoinColumns = @JoinColumn(name = "KNOWN_ID"))
  private Set<Member> knows = new HashSet<Member>();;


  @ManyToMany(mappedBy = "knows")
  private Set<Member> teammates = new HashSet<Member>();
}
