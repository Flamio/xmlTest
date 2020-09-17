package com.example.xmlTest.providers;

/** провайдер для работы с файлами. */
public interface FileProvider {
  /**
   * Получить содержимое файла.
   *
   * @param path путь до файла.
   * @return Содержимое.
   */
  String getContent(String path);

  /**
   * Записать содержимое в файл.
   *
   * @param path путь до файла.
   * @param data содержимое.
   */
  void writeContent(String path, String data);

  /**
   * Получить директорию файла.
   *
   * @param file путь до файла.
   * @return Директория файла.
   */
  String getFileFolder(String file);
}
