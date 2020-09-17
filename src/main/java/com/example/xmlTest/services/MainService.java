package com.example.xmlTest.services;

import com.example.xmlTest.generated.entities.Person;
import com.example.xmlTest.providers.FileProvider;
import com.example.xmlTest.providers.XmlProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

/** Главный сервис приложения. */
@Component
@RequiredArgsConstructor
@Slf4j
public class MainService {

  public static final String NEW_FIO = "Vasil'ev Vasiliy Vasilievych";
  public static final String NEW_CITY = "Moscow";
  public static final BigInteger NEW_HOUSE = BigInteger.valueOf(55);

  /** провайдер для работы с файлами. */
  private final FileProvider fileProvider;

  /** провайдер для работы с xml. */
  private final XmlProvider xmlProvider;

  /**
   * начать выполнение.
   *
   * @param xmlFilePath путь до xml файла.
   */
  public void start(String xmlFilePath) {
    try {
      log.info("started");
      final String content = fileProvider.getContent(xmlFilePath);
      xmlProvider.validate(content);
      final Person person = xmlProvider.parseToObject(content);
      person.setFio(NEW_FIO);
      person.getAddress().setCity(NEW_CITY);
      person.getAddress().setHouse(NEW_HOUSE);
      final String newXml = xmlProvider.parseToString(person);
      fileProvider.writeContent(
          String.format("%s/%s", fileProvider.getFileFolder(xmlFilePath), "changed.xml"), newXml);
      log.info("complete, new xml file - changed.xml");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
