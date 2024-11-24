package pl.edu.agh.to.school.course;

import jakarta.persistence.*;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.student.Student;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private int Id;
    private String name;

    @OneToMany
    private List<Student> students;

    @OneToMany(mappedBy = "course", orphanRemoval = true)
    private List<Grade> gradesList = new ArrayList<>();

    public Course(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }

    public Course() {}

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void assignStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }
}
