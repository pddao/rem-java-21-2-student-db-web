package de.neuefische.studentdbweb.db;

import de.neuefische.studentdbweb.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDb {
    private final List<Student> students = new ArrayList<>(List.of(
            new Student("1", "Peter"),
            new Student("9", "Linda")
    ));

    public List<Student> list() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(String id) {
        if (findById(id).isPresent()) {
            students.remove(findById(id).get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No student with this id exists.");
        }
    }

    public Optional<Student> findById(String id) {
        for (de.neuefische.studentdbweb.model.Student student : students) {
            if (student.getId().equals(id)) {
                return java.util.Optional.of(student);
            }
        }
        return java.util.Optional.empty();
    }
}
