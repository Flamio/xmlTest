package com.example.xmlTest.providers.impl;

import com.example.xmlTest.providers.FileProvider;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DefaultFileProvider implements FileProvider {
  @Override
  @SneakyThrows
  public String getContent(final String path) {
    final byte[] bytes = Files.readAllBytes(Path.of(path));
    return new String(bytes);
  }

  @Override
  @SneakyThrows
  public void writeContent(final String path, String data) {
    Files.write(Path.of(path), data.getBytes());
  }

  @Override
  public String getFileFolder(final String file) {
    return new File(file).getParent();
  }
}
