package com.vividseats.teamanagment.dto;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
@Scope("prototype")
public class TestCasesDTO {

  private List<TeamTestCaseDTO> testCase = new ArrayList<TeamTestCaseDTO>();

}
