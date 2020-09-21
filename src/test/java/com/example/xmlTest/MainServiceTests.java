package com.example.xmlTest;

import com.example.xmlTest.providers.FileProvider;
import com.example.xmlTest.providers.XmlProvider;
import com.example.xmlTest.providers.impl.DefaultXmlProvider;
import com.example.xmlTest.services.MainService;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;

public class MainServiceTests {

  private MainService mainService;
  private FileProvider fileProvider;

  private ArgumentCaptor<String> pathCapture = ArgumentCaptor.forClass(String.class);
  private ArgumentCaptor<String> dataCapture = ArgumentCaptor.forClass(String.class);

  @BeforeEach
  public void setUp() {
    fileProvider = Mockito.mock(FileProvider.class);
    final XmlProvider xmlProvider = new DefaultXmlProvider();
    mainService = new MainService(fileProvider, xmlProvider);
  }

  @Test
  public void withValidXmlShouldChangedRightFields() {

    // arrange
    final String testInput = getClassPathResourceAsString("/test_input.xml");
    final String expectedOutput = getClassPathResourceAsString("/test_output.xml");

    Mockito.when(fileProvider.getContent(Mockito.anyString())).thenReturn(testInput);
    Mockito.when(fileProvider.getFileFolder(Mockito.anyString())).thenReturn("");

    // act
    mainService.start("/test_input.xml");

    // assert
    Mockito.verify(fileProvider).writeContent(pathCapture.capture(), dataCapture.capture());

    assertThat(pathCapture.getValue()).isEqualTo("/changed.xml");
    assertThat(dataCapture.getValue()).isEqualTo(expectedOutput);
  }

  @Test
  public void withErrorXmlShouldThrowException() {

    // arrange
    final String testInput = getClassPathResourceAsString("/test_input_with_errors.xml");

    Mockito.when(fileProvider.getContent(Mockito.anyString())).thenReturn(testInput);

    // act
    final Throwable throwable =
        catchThrowable(() -> mainService.start("/test_input_with_errors.xml"));

    // assert
    assertThat(throwable).isInstanceOf(Exception.class);
  }

  private String getClassPathResourceAsString(String path) {
    final DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
    final Resource resource = defaultResourceLoader.getResource(path);
    final String s;
    try {
      s = IOUtils.toString(resource.getInputStream(), "UTF-8");
    } catch (IOException e) {
      throw new IllegalStateException("Can't process classpath resource: " + e.getMessage(), e);
    }
    return s;
  }
}
