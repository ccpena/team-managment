package com.vividseats.teamanagment.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vividseats.teamanagment.dto.MemberDTO;
import com.vividseats.teamanagment.dto.TeamDTO;
import com.vividseats.teamanagment.dto.TeamTestCaseDTO;
import com.vividseats.teamanagment.dto.TestCasesDTO;
import com.vividseats.teamanagment.exceptions.FilterCelebrityUploadException;
import com.vividseats.teamanagment.exceptions.MemberCreatedException;
import com.vividseats.teamanagment.exceptions.TeamNotFoundException;
import com.vividseats.teamanagment.exceptions.TestCasesFileNotFoundException;
import com.vividseats.teamanagment.service.TeamMemberFinderService;
import com.vividseats.teamanagment.service.TeamService;
import com.vividseats.teamanagment.service.members.MemberService;

@RestController()
@RequestMapping("/v1/teams")
public class TeamResource {

  @Autowired
  private ApplicationContext appCtx;

  private TeamService teamService;
  private TeamMemberFinderService finderService;
  private MemberService memberService;

  public TeamResource(TeamService teamService) {
    this.teamService = teamService;
  }


  @PostMapping("/upload/filterByCelebrity")
  public ResponseEntity<TestCasesDTO> uploadFile(@RequestParam("file") MultipartFile uploadfile)
      throws Exception {
    TestCasesDTO testCases = getTestCases();

    if (uploadfile.isEmpty()) {
      throw new TestCasesFileNotFoundException("File wasn't uploaded");
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(uploadfile.getInputStream()));
    String line = null;
    String lineNxM = null;
    int n = 0, m = 0;
    TeamTestCaseDTO testCase = getTeamTestCaseBean();
    reader.readLine();
    while ((line = reader.readLine()) != null && !line.isEmpty()) {
      if (lineNxM == null) {
        lineNxM = line;
        StringTokenizer token = new StringTokenizer(lineNxM);
        String nLine = token.nextElement().toString();
        try {
          n = Integer.parseInt(nLine);
        } catch (Exception e) {
          throw new FilterCelebrityUploadException("Number of test cases not found");
        }
        String mLine = token.nextElement().toString();
        try {
          m = Integer.parseInt(mLine);
        } catch (Exception e) {
          throw new FilterCelebrityUploadException("Number of members not found");
        }
        testCase = getTeamTestCaseBean();
        testCase.setIdTeam(n);
        continue;
      }
      MemberDTO memberDTO = getMemberDTO();
      StringTokenizer memberKnows = new StringTokenizer(line);
      String memberId = memberKnows.nextElement().toString();

      if (isNumeric(memberId))
        memberDTO.setId(Long.parseLong(memberId));

      while (memberKnows.hasMoreElements()) {
        String memberKnownId = memberKnows.nextElement().toString();
        if (isNumeric(memberKnownId))
          memberDTO.getPeopleKnown().add(Long.valueOf(memberKnownId));
      }
      testCase.getMemberDTO().add(memberDTO);

      if (m == testCase.getMemberDTO().size()) {
        testCases.getTestCase().add(testCase);
        lineNxM = null;
      }

    }
    reader.close();


    // Process Test Cases
    testCases.getTestCase().forEach(teamTest -> {
      TeamDTO teamDTO = new TeamDTO();
      teamDTO.setId(teamTest.getIdTeam());
      teamDTO.setMembers(teamTest.getMemberDTO());
      Optional<MemberDTO> celebrity = finderService.findOf(teamDTO);
      buildResponse(celebrity, teamTest);
    });


    return new ResponseEntity<TestCasesDTO>(testCases, new HttpHeaders(), HttpStatus.OK);

  }

  private TeamTestCaseDTO buildResponse(Optional<MemberDTO> celebrity, TeamTestCaseDTO teamCase) {
    if (teamCase == null) {
      teamCase = getTeamTestCaseBean();
    }

    if (celebrity.isPresent()) {
      teamCase.setCelebrity(celebrity.get().getId());
      teamCase.setMessage("Celebrity Found with ID: " + celebrity.get().getId());
    } else {
      teamCase.setMessage("Celebrity not found");
    }
    return teamCase;
  }


  //@GetMapping("/{idTeam}/celebrity")
  public ResponseEntity<TeamTestCaseDTO> findCelebrity(@PathVariable long idTeam) {

    Optional<TeamDTO> team = teamService.findById(idTeam);

    if (!team.isPresent()) {
      throw new TeamNotFoundException(String.format("The team %d doesn't exists", idTeam));
    }

    team.get().setMembers(memberService.findByTeamId(idTeam));

    Optional<MemberDTO> celebrity = finderService.findOf(team.get());
    TeamTestCaseDTO teamTest = getTeamTestCaseBean();
    buildResponse(celebrity, teamTest);

    return ResponseEntity.ok().body(teamTest);

  }


  //@PostMapping("/")
  public ResponseEntity<TeamDTO> createATeam(@RequestBody TeamDTO teamDTO) {

    Optional<TeamDTO> teamCreated = teamService.create(teamDTO);

    if (teamCreated.isPresent()) {
      long teamId = teamCreated.get().getId();
      URI location = null;

      location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(teamId).toUri();

      return ResponseEntity.created(location).body(teamCreated.get());
    }

    return ResponseEntity.badRequest().body(teamDTO);

  }

  //@PostMapping("/{idTeam}/members")
  public ResponseEntity<MemberDTO> createAMemberOfATeam(@RequestBody MemberDTO memberDTO,
      @PathVariable long idTeam) {
    TeamDTO teamDTO = new TeamDTO();
    teamDTO.setId(idTeam);
    Optional<MemberDTO> memberCreated = null;
    URI location = null;

    Optional<TeamDTO> team = teamService.findById(idTeam);
    if (!team.isPresent()) {
      throw new TeamNotFoundException(String.format("The team %d doesn't exists", idTeam));
    }


    if (memberDTO == null || memberDTO.getId() == null) {
      throw new MemberCreatedException("The id of the member should be defined");
    }

    memberDTO.setTeam(teamDTO);
    memberCreated = memberService.create(memberDTO);
    if (memberCreated.isPresent()) {
      Long memberId = memberCreated.get().getId();
      location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(memberId).toUri();

      return ResponseEntity.created(location).body(memberCreated.get());
    }

    throw new MemberCreatedException("The member " + idTeam + " couldn't be created");

  }

  public static boolean isNumeric(String str) throws FilterCelebrityUploadException {
    try {
      Double.parseDouble(str);
      return true;
    } catch (NumberFormatException nfe) {
      throw new FilterCelebrityUploadException("The " + str + " is not numeric");
    }
  }

  private MemberDTO getMemberDTO() {
    return appCtx.getBean(MemberDTO.class);
  }

  private TeamTestCaseDTO getTeamTestCaseBean() {
    return appCtx.getBean(TeamTestCaseDTO.class);
  }


  private TestCasesDTO getTestCases() {
    return appCtx.getBean(TestCasesDTO.class);
  }

  @Autowired
  public void setFinderService(TeamMemberFinderService finderService) {
    this.finderService = finderService;
  }

  @Autowired
  public void setMemberService(MemberService memberService) {
    this.memberService = memberService;
  }



}
