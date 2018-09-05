package com.vividseats.teamanagment.service;

import java.util.Optional;
import com.vividseats.teamanagment.dto.MemberDTO;
import com.vividseats.teamanagment.dto.TeamDTO;

public interface TeamMemberFinderService {
	
	Optional<MemberDTO> findOf(TeamDTO team);

}
