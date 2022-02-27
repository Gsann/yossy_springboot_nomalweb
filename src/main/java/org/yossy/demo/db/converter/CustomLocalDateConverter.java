package org.yossy.demo.db.converter;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;

public class CustomLocalDateConverter implements AttributeConverter<LocalDate,  java.sql.Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDateTime) {
        return (localDateTime == null ? null : java.sql.Date.valueOf(localDateTime));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return (date == null ? null : date.toLocalDate());
    }
    
}
