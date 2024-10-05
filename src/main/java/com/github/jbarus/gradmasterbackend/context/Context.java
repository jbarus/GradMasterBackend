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

    private List<UniversityEmployee> universityEmployeeList;
    private List<Student> studentList;
    private HashMap<UniversityEmployee, List<Student>> universityEmployeeMap;


    private Context(){
        universityEmployeeList = new ArrayList<>();
        studentList = new ArrayList<>();
        universityEmployeeMap = new HashMap<>();
    };

    public static Context getInstance(LocalDate date){
        return availableContexts.computeIfAbsent(date, k -> new Context());
    }

    public static int getNumberOfAvailableContexts(){
        return availableContexts.size();
    }

    public static boolean isInitialized(LocalDate date){
        if(availableContexts.containsKey(date)){
            return availableContexts.get(date).universityEmployeeList != null;
        }else {
            return false;
        }
    }
}
