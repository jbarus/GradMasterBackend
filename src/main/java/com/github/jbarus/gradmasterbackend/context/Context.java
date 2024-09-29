package com.github.jbarus.gradmasterbackend.context;

import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Context {
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private static HashMap<LocalDate, Context> availableContexts = new HashMap<>();

    private List<UniversityEmployee> universityEmployeeList = new ArrayList<>();
    private List<Student> studentList = new ArrayList<>();


    private Context(){};

    public static Context getInstance(LocalDate date){
        return availableContexts.computeIfAbsent(date, k -> new Context());
    }
}
