package pl.edu.agh.to.school.grade;

import jakarta.persistence.*;
import pl.edu.agh.to.school.course.Course;

@Entity
public class Grade {

    @Id
    @GeneratedValue
    private int Id;
    private int gradeValue;

    @ManyToOne()
    @JoinColumn(name = "course_id")
    private Course course;

    public Grade(int gradeValue, Course course) {
        this.gradeValue = gradeValue;
        this.course = course;
    }


    public Grade() {}

    public int getId() {
        return Id;
    }

    public int getGradeValue() {
        return gradeValue;
    }

    public Course getCourse() {
        return course;
    }
}
