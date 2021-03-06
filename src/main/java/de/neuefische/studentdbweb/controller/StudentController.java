package de.neuefische.studentdbweb.controller;

import de.neuefische.studentdbweb.model.Student;
import de.neuefische.studentdbweb.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> listStudents(@RequestParam Optional<String> search) {
        if (search.isPresent() && !search.get().isBlank()) {
            return studentService.filterStudentByName(search.get());
        }
        return studentService.list();
    }

    @GetMapping("{id}")
    public Student getStudentById(@PathVariable String id) {
        Optional<Student> optionalStudent = studentService.findById(id);
        return optionalStudent.orElse(null);
    }

//    @PutMapping("{id}")
//    public void addStudent(@Valid @RequestBody Student student, @PathVariable String id) {
//        if (id.equals(student.getId())) {
//            studentService.addStudent(student);
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id not matching");
//        }
//    }

    @PutMapping("{id}")
    public void upsertStudent(@Valid @RequestBody Student student, @PathVariable String id) {
        if (id.equals(student.getId())) {
            if (studentService.findById(id).isEmpty()) {
                studentService.addStudent(student);
            } else {
                studentService.updateStudent(student);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id not matching");
        }
    }

    @DeleteMapping("{id}")
    public void removeStudentById(@PathVariable String id) {
        studentService.removeStudent(id);
    }

}
