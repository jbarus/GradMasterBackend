package com.github.jbarus.gradmasterbackend.models.problem;

import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Committee {
    List<UniversityEmployee> universityEmployees;
    List<Student> students;


    public Committee(List<UniversityEmployee> universityEmployees, List<Student> students) {
        this.universityEmployees = universityEmployees;
        this.students = students;
    }

    public Committee() {
    }
}
