package com.akvone.validation;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("java-main")
public class JavaMainController {

  @GetMapping("simple")
  public boolean getSimpleResult(@RequestParam(required = false) boolean flag) {
    return flag;
  }

  @PostMapping("simple")
  public void getSimpleResult(@RequestBody @Valid SimpleRequest request) {
    log.info(request.toString());
  }
}
