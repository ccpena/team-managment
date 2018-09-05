package com.vividseats.teamanagment.service;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import com.vividseats.teamanagment.dto.MemberDTO;
import com.vividseats.teamanagment.dto.TeamDTO;

@Component("celebrityFinder")
public class CelebrityFinderServiceImpl implements TeamMemberFinderService {


  @Override
  public Optional<MemberDTO> findOf(TeamDTO team) {

    if (team == null || !team.hasMembers()) {
      return Optional.empty();
    }

    Queue<MemberDTO> queueCelebrities = new LinkedList<>(team.getMembers());

    while (queueCelebrities.size() > 1) {
      MemberDTO first = queueCelebrities.poll();
      MemberDTO second = queueCelebrities.poll();
      MemberDTO possibleCelebrity = null;


      possibleCelebrity = firstMemberKnowsSecondMember(first, second) ? second : first;
      if (doesntKnowAnyBody(possibleCelebrity)) {
        queueCelebrities.add(possibleCelebrity);
      }
    }

    MemberDTO celebrity = queueCelebrities.peek();

    return doesntKnowAnyBody(celebrity) ? Optional.of(celebrity) : Optional.empty();

  }

  private boolean doesntKnowAnyBody(MemberDTO member) {
    if (member == null) {
      return false;
    }
    return member.getPeopleKnown().isEmpty();
  }

  private boolean firstMemberKnowsSecondMember(MemberDTO first, MemberDTO second) {
    Optional<Long> idMemberFound =
        first.getPeopleKnown().stream().filter(memberKnowsAnotherMember(second)).findFirst();
    return idMemberFound.isPresent();
  }

  private static Predicate<Long> memberKnowsAnotherMember(MemberDTO anotherMember) {
    return idKnown -> idKnown == anotherMember.getId();
  }

}
