package com.example.abiturientpro.data.models;

import java.util.Arrays;
import java.util.List;

public class Specialty {
    private String code;
    private String name;

    public Specialty(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return code + " " + name;
    }

    public static final List<Specialty> ALL = Arrays.asList(
            new Specialty("09.02.07", "Информационные системы и программирование"),
            new Specialty("38.02.01", "Экономика и бухгалтерский учет (по отраслям)"),
            new Specialty("15.02.14", "Оснащение средствами автоматизации технологических процессов и производств"),
            new Specialty("23.02.07", "Техническое обслуживание и ремонт двигателей, систем и агрегатов автомобилей"),
            new Specialty("43.02.15", "Поварское и кондитерское дело"),
            new Specialty("40.02.01", "Право и организация социального обеспечения"),
            new Specialty("44.02.02", "Преподавание в начальных классах"),
            new Specialty("54.02.01", "Дизайн (по отраслям)")
    );
}