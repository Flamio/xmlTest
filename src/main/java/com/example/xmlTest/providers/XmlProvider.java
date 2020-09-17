package com.example.xmlTest.providers;

import com.example.xmlTest.generated.entities.Person;

/**
 * Провайдер для работы с xml.
 */
public interface XmlProvider {
    /**
     * Валидация по xsd.
     * @param xml данные в xml.
     */
    void validate(String xml);

    /**
     * Распарсить xml в объект.
     * @param xml данные в xml.
     * @return Объект.
     */
    Person parseToObject(String xml);

    /**
     * Распарсить объект в строку xml
     * @param person объект.
     * @return строка xml.
     */
    String parseToString(Person person);
}
