package de.neuefische.studentdbweb.service;

import de.neuefische.studentdbweb.db.StudentDb;
import de.neuefische.studentdbweb.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentDb students;

    public List<Student> list() {
        return students.list();
    }

    public Optional<Student> findById(String id) {
        return students.findById(id);
    }

    public List<Student> filterStudentByName(String searchString) {
        List<Student> filteredStudents = new ArrayList<>();

        for (Student student : students.list()) {
            if(student.getName().toLowerCase().contains(searchString.toLowerCase())){
                filteredStudents.add(student);
            }
        }
        return filteredStudents;
    }

    public void addStudent(Student student) {
        //check if exists and update
        students.addStudent(student);
    }

    public void removeStudent(String id) {
        students.removeStudent(id);
    }

    public void updateStudent(Student student) {
        removeStudent(student.getId());
        addStudent(student);
    }

}
