package com.vividseats.teamanagment.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.vividseats.teamanagment.TeamManagmentApplication;
import com.vividseats.teamanagment.dto.MemberDTO;
import com.vividseats.teamanagment.dto.TeamDTO;
import com.vividseats.teamanagment.service.CelebrityFinderServiceImpl;
import com.vividseats.teamanagment.service.TeamMemberFinderService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamManagmentApplication.class)
public class CelebrityFinderServiceTests {

  private TeamMemberFinderService celebrityFinder;

  private static final long CELEBRITY_ID = 1;

  @Before
  public void setup() {
    celebrityFinder = new CelebrityFinderServiceImpl();
  }

  private Set<Long> createSetOfIdsMembers(Integer... members) {
    Set<Long> set = new HashSet<>();
    for (Integer m : members) {
      set.add((long) m);
    }
    return set;
  }


  private TeamDTO buildATeamWithOneCelebrity() {
    TeamDTO team = new TeamDTO();
    MemberDTO celebrity = new MemberDTO(CELEBRITY_ID, team, Collections.emptySet());

    MemberDTO secondMember = new MemberDTO(2l, team, Collections.singleton(1l));
    MemberDTO thirdMember = new MemberDTO(3l, team, createSetOfIdsMembers(1, 2));
    MemberDTO fourthMember = new MemberDTO(4l, team, createSetOfIdsMembers(1, 3));
    MemberDTO fifthMember = new MemberDTO(5l, team, createSetOfIdsMembers(1, 2, 3));
    MemberDTO sixthMember = new MemberDTO(6l, team, createSetOfIdsMembers(1, 4, 5));

    team.add(celebrity);
    team.add(secondMember);
    team.add(thirdMember);
    team.add(fourthMember);
    team.add(fifthMember);
    team.add(sixthMember);

    return team;
  }

  private TeamDTO buildATeamWithoutCelebrity() {
    TeamDTO team = new TeamDTO();

    MemberDTO secondMember = new MemberDTO(2l, team, Collections.singleton(3l));
    MemberDTO thirdMember = new MemberDTO(3l, team, createSetOfIdsMembers(4, 2));
    MemberDTO fourthMember = new MemberDTO(4l, team, createSetOfIdsMembers(2, 3));
    MemberDTO fifthMember = new MemberDTO(5l, team, createSetOfIdsMembers(3));
    MemberDTO sixthMember = new MemberDTO(6l, team, createSetOfIdsMembers(4, 5));

    team.add(secondMember);
    team.add(thirdMember);
    team.add(fourthMember);
    team.add(fifthMember);
    team.add(sixthMember);

    return team;
  }

  @Test
  public void given_TeamWithOneCelebrity_Then_ShouldBeFound() {
    TeamDTO team = buildATeamWithOneCelebrity();

    Optional<MemberDTO> celebrity = celebrityFinder.findOf(team);

    assertTrue(celebrity.isPresent());
    assertEquals(celebrity.get().getId().intValue(), CELEBRITY_ID);
  }

  @Test
  public void given_TeamWithoutACelebrity_Then_ShouldReturnEmptyMember() {
    TeamDTO team = buildATeamWithoutCelebrity();
    Optional<MemberDTO> celebrity = celebrityFinder.findOf(team);

    assertFalse(celebrity.isPresent());
  }

  @Test
  public void given_EmptyTeam_Then_ShouldReturnEmptyCelebrity() {

    Optional<MemberDTO> celebrity = celebrityFinder.findOf(new TeamDTO());
    assertFalse(celebrity.isPresent());
  }

  @Test
  public void given_NullTeam_Then_ShouldReturnEmptyCelebrity() {

    Optional<MemberDTO> celebrity = celebrityFinder.findOf(null);
    assertFalse(celebrity.isPresent());
  }



}
