package com.github.jbarus.gradmasterbackend.models.problem;

import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
public class ProblemContext {
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    public static HashMap<UUID, ProblemContext> availableContexts = new HashMap<>();

    private UUID uuid;
    private String name;
    private LocalDate date;
    private List<UniversityEmployee> universityEmployees;
    private List<Student> students;
    private HashMap<UUID, List<UUID>> studentReviewerMapping;
    private List<UUID> positiveCorrelationMapping;
    private List<UUID> negativeCorrelationMapping;
    private List<UUID> splittedUniversityEmployees;
    private Solution solution;
    private ProblemParameters problemParameters;


    private boolean calculationInProgress;

    private ProblemContext(UUID uuid) {
        this.uuid = uuid;
        this.problemParameters = new ProblemParameters();
    }

    public static ProblemContext getOrCreateInstance(UUID id){
        return availableContexts.computeIfAbsent(id, k -> new ProblemContext(id));
    }

    public static ProblemContext getInstance(UUID id){
        return availableContexts.get(id);
    }

    public static List<ProblemContext> getAvailableContexts(){
        return new ArrayList<>(availableContexts.values());
    }

    public static ProblemContext deleteContext(UUID id){
        return availableContexts.remove(id);
    }
}
