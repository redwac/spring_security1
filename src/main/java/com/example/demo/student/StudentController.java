package com.example.demo.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"reda"),
            new Student(2,"faycal"),
            new Student(3,"hicham")
    );

    @GetMapping(path = "{studentID}")
    public Student getstudent(@PathVariable("studentID") Integer studentID){
        return STUDENTS.stream()
                .filter(student -> studentID.equals(student.getStudentID()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("student" + studentID + "does not existe"));
    }
}
