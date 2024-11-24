package pl.edu.agh.to.school.student;

import jakarta.persistence.*;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.grade.Grade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String indexNumber;

    @OneToMany
    @JoinColumn(name = "student_id")
    private List<Grade> grades;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Student(String firstName, String lastName, LocalDate birthDate, String indexNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.indexNumber = indexNumber;
        this.grades = new ArrayList<>();
    }

    public Student() {}

    public void giveGrade(Grade grade) {
        grades.add(grade);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public Course getCourse() {
        return course;
    }
}
