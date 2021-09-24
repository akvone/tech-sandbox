package com.akvone.sandbox.validation;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SimpleRequest {

  @NotEmpty
  private List<SubRequest> names;

}
