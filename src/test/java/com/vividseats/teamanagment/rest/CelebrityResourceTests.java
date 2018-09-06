package com.vividseats.teamanagment.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.vividseats.teamanagment.TeamManagmentApplication;
import com.vividseats.teamanagment.dto.MemberDTO;
import com.vividseats.teamanagment.dto.TeamDTO;
import com.vividseats.teamanagment.service.TeamService;

@SpringBootTest(classes = TeamManagmentApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CelebrityResourceTests {

  private final static String URI_FIND_CELEBRITY = "/v1/teams/%d/celebrity";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TeamService teamService;

  private Set<Long> createSetOfIdsMembers(Long... members) {
    Set<Long> set = new HashSet<>();
    for (Long m : members) {
      set.add(m);
    }
    return set;
  }

  private TeamDTO buildATeamWithACelebrity() {
    TeamDTO team = new TeamDTO();
    Random random = new Random();
    team.setId(random.nextLong());

    MemberDTO celebrity = new MemberDTO(1l, team, Collections.emptySet());

    MemberDTO secondMember = new MemberDTO(2l, team, Collections.singleton(1l));
    MemberDTO thirdMember = new MemberDTO(3l, team, createSetOfIdsMembers(1l, 2l));
    MemberDTO fourthMember = new MemberDTO(4l, team, createSetOfIdsMembers(1l, 3l));
    MemberDTO fifthMember = new MemberDTO(5l, team, createSetOfIdsMembers(1l, 2l, 3l));
    MemberDTO sixthMember = new MemberDTO(6l, team, createSetOfIdsMembers(1l, 4l, 5l));

    team.add(celebrity);
    team.add(secondMember);
    team.add(thirdMember);
    team.add(fourthMember);
    team.add(fifthMember);
    team.add(sixthMember);

    return team;
  }

  @Test
  @Ignore
  public void shouldFindACelebrity() throws Exception {
    long teamId = 1l;

    TeamDTO team = buildATeamWithACelebrity();

    when(teamService.findById(any())).thenReturn(Optional.of(team));

    final String RESOURCE_LOCATION = String.format(URI_FIND_CELEBRITY, teamId);

    mockMvc.perform(get(RESOURCE_LOCATION)).andDo(print())
        .andExpect(content().json("{'celebrity': 1}")).andExpect(status().isOk());
  }

  @Test
  @Ignore
  public void given_NotExistentTeam_Then_ShouldReturnNotFound() throws Exception {
    long teamId = 1l;

    when(teamService.findById(any())).thenReturn(Optional.empty());

    final String RESOURCE_LOCATION = String.format(URI_FIND_CELEBRITY, teamId);

    mockMvc.perform(get(RESOURCE_LOCATION)).andDo(print()).andExpect(status().isNotFound());
  }

}
