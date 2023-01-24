package com.ideas2it.readXlsxFile.model;

import com.ideas2it.readXlsxFile.helper.HashMapConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.util.Map;

@Entity
@Getter
@Setter
public class ExcelRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;
    private String FileName;
    private String SheetName;
    private int rowNumber;
    @Column(columnDefinition = "json")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> patientAttributes;
}
