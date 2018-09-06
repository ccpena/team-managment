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

  public Member() {}

  public Member(long id) {
    this.memberId = id;
  }

  @Id
  @Column(name = "MEMBER_ID", nullable = false)
  private Long memberId;

  @ManyToOne(optional = false)
  @JoinColumn(name = "TEAM_ID", insertable = true, updatable = true)
  private Team team;


  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "known_members", joinColumns = @JoinColumn(name = "MEMBER_ID"),
      inverseJoinColumns = @JoinColumn(name = "KNOWN_ID"))
  private Set<Member> knows = new HashSet<Member>();;


}
