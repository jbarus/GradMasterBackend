package com.github.jbarus.gradmasterbackend.context;

import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.structures.CorrelationMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Context {
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private static HashMap<UUID, Context> availableContexts = new HashMap<>();

    private UUID id;
    private String name;
    private LocalDate date;
    private List<UniversityEmployee> universityEmployeeList;
    private List<Student> studentList;
    private HashMap<UniversityEmployee, List<Student>> universityEmployeeMap;
    private List<Student> unassignedStudentList;
    private List<UUID> positiveCorrelation;
    private List<UUID> negativeCorrelation;
    private ContextOptions contextOptions = new ContextOptions();


    private Context(){
        this.id = UUID.randomUUID();
        this.universityEmployeeList = new ArrayList<>();
        this.studentList = new ArrayList<>();
        this.universityEmployeeMap = new HashMap<>();
        this.unassignedStudentList = new ArrayList<>();
    };

    public static Context getInstance(LocalDate date){
        for (Context context : availableContexts.values()) {
            if (context.date.equals(date)) {
                return context;
            }
        }
        Context context = new Context();
        context.date = date;
        availableContexts.put(context.id, context);
        return context;
    }

    public static Context getInstance(UUID id){
        return availableContexts.get(id);
    }

    public static List<Context> getAvailableContexts(){
        return new ArrayList<>(availableContexts.values());
    }

    public static Context deleteContext(UUID id){
        return availableContexts.remove(id);
    }

    public static int getNumberOfAvailableContexts(){
        return availableContexts.size();
    }

    public static boolean isInitialized(LocalDate date){
        for (Context context : availableContexts.values()) {
            if (context.date.equals(date)) {
                return context.universityEmployeeList.isEmpty();
            }
        }
        return false;
    }
}
