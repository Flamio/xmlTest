package com.example.xmlTest;

import com.example.xmlTest.services.MainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class XmlTestApplication {

  public static void main(String[] args) {
    if (args.length == 0) throw new RuntimeException("Должен быть указан входной файл!");

    final ConfigurableApplicationContext run =
        SpringApplication.run(XmlTestApplication.class, args);
    final MainService mainService = run.getBean("mainService", MainService.class);

    mainService.start(args[0]);
  }
}
