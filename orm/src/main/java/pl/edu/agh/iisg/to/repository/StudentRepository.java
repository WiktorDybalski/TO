package pl.edu.agh.iisg.to.repository;

import pl.edu.agh.iisg.to.model.Student;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements Repository<Student> {

    @Override
    public Optional<Student> add(Student student) {
        return Optional.empty();
    }

    @Override
    public Optional getById(int id) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public void remove(Student student) {

    }

    public List<Student> findAllByCourseName(String courseName) {
        return Collections.emptyList();
    }
}
