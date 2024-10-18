package pl.edu.agh.iisg.to.service;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.TransactionService;

import java.util.*;

public class SchoolService {

    private final TransactionService transactionService;

    private final StudentDao studentDao;

    private final CourseDao courseDao;

    private final GradeDao gradeDao;

    public SchoolService(TransactionService transactionService, StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao) {
        this.transactionService = transactionService;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
    }

    public boolean enrollStudent(final Course course, final Student student) {
        // TODO - implement
        return transactionService.doAsTransaction(() -> {
            if (course.studentSet().contains(student) || student.courseSet().contains(course)) {
                return false;
            }
            course.studentSet().add(student);
            student.courseSet().add(course);
            return true;
        }).orElse(false);
    }

    public boolean removeStudent(int indexNumber) {
        // TODO - implement
        return transactionService.doAsTransaction(() -> {
            Optional<Student> studentOptional = studentDao.findByIndexNumber(indexNumber);

            if (studentOptional.isPresent()) {
                for(Course course: studentOptional.get().courseSet()) {
                    course.studentSet().remove(studentOptional.get());
                }
                studentDao.remove(studentOptional.get());
                return true;
            } else {
                return false;
            }
        }).orElse(false);
    }

    public boolean gradeStudent(final Student student, final Course course, final float gradeValue) {
        // TODO - implement
        return transactionService.doAsTransaction(() -> {
            Grade grade = new Grade(student, course, gradeValue);
            student.gradeSet().add(grade);
            course.gradeSet().add(grade);
            gradeDao.save(grade);
            return true;
        }).orElse(false);
    }

    public Map<String, List<Float>> getStudentGrades(String courseName) {
        // TODO - implement
        Optional<Course> course = courseDao.findByName(courseName);

        if (course.isPresent()) {
            Map<String, List<Float>> grades = new HashMap<>();

            for (Student s : course.get().studentSet()) {
                List<Float> gradesList = course.get().gradeSet().stream()
                        .filter(grade -> grade.student().equals(s))
                        .map(Grade::grade)
                        .sorted()
                        .toList();
                grades.put(s.fullName(), gradesList);
            }
            return grades;
        }
        return Collections.emptyMap();
    }
}
