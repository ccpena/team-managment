package com.vividseats.teamanagment.service.members;

import java.util.Set;
import com.vividseats.teamanagment.dto.MemberDTO;
import com.vividseats.teamanagment.service.CrudOperation;

public interface MemberService extends CrudOperation<MemberDTO> {

  Set<MemberDTO> findByTeamId(Long teamId);

}
