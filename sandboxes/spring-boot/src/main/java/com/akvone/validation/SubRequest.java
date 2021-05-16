package com.akvone.validation;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SubRequest {

  @NotEmpty
  private String name;
}
