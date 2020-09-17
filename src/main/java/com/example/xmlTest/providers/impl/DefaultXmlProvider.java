package com.example.xmlTest.providers.impl;

import com.example.xmlTest.generated.entities.Person;
import com.example.xmlTest.providers.XmlProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.StringReader;
import java.io.StringWriter;

@Component
@Slf4j
public class DefaultXmlProvider implements XmlProvider {

  private final JAXBContext jaxbContext;

  {
    try {
      jaxbContext = JAXBContext.newInstance(Person.class);
    } catch (JAXBException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  @SneakyThrows
  @Override
  public void validate(final String xml) {
    final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    final Schema schema =
        factory.newSchema(getClass().getClassLoader().getResource("xsd/person.xsd"));
    final Validator validator = schema.newValidator();
    validator.validate(new StreamSource(new StringReader(xml)));
    log.info("validate ok");
  }

  @Override
  @SneakyThrows
  public Person parseToObject(final String xml) {
    final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    return (Person) unmarshaller.unmarshal(new StreamSource(new StringReader(xml)));
  }

  @Override
  @SneakyThrows
  public String parseToString(final Person person) {
    final Marshaller marshaller = jaxbContext.createMarshaller();
    final StringWriter sw = new StringWriter();
    marshaller.marshal(person, sw);
    return sw.toString();
  }
}
