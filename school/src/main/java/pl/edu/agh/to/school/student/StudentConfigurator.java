package pl.edu.agh.to.school.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.course.CourseRepository;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.grade.GradeRepository;

import java.time.LocalDate;
import java.util.List;


@Configuration
public class StudentConfigurator {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, CourseRepository courseRepository, GradeRepository gradeRepository) {
        return args -> {
//            Student kowalski = new Student("Jan", "Kowalski", LocalDate.now(), "123456");
//            Student budynek = new Student("Piotr", "Budynek", LocalDate.now(), "6547891");
//            Student menczystaty = new Student("Henryk", "Menczystaty", LocalDate.now(), "848565");
//
//            var to = new Course("Technologie obiektowe");
//            var po = new Course("Programowanie obiektowe");
//
//            courseRepository.saveAll(List.of(to, po));
//
//            to.assignStudent(kowalski);
//            to.assignStudent(budynek);
//
//            po.assignStudent(menczystaty);
//
//            Grade grade1 = new Grade(5, to);
//            kowalski.giveGrade(grade1);
//
//            Grade grade2 = new Grade(4, po);
//            kowalski.giveGrade(grade2);
//
//            Grade grade3 = new Grade(3, to);
//            budynek.giveGrade(grade3);
//
//            Grade grade4 = new Grade(2, po);
//            menczystaty.giveGrade(grade4);
//
//            gradeRepository.saveAll(List.of(grade1, grade2, grade3, grade4));
//            studentRepository.saveAll(List.of(kowalski, budynek, menczystaty));
//            courseRepository.saveAll(List.of(to, po));
        };
    }
}