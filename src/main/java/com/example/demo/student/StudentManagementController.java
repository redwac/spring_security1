package com.example.demo.student;

import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"reda"),
            new Student(2,"faycal"),
            new Student(3,"hicham")
    );

    // hasRole'ROLE_' hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getallstudent(){
        System.out.println("getallstudent");
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNenStudent(@RequestBody Student student){
        System.out.println("registerNenStudent");
        System.out.println(student);
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deletteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println("deletteStudent");
        System.out.println(studentId);
    }
    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId,@RequestBody Student student){
        System.out.println("updateStudent");
        System.out.println(String.format("%s %s",studentId, student));
    }
}
