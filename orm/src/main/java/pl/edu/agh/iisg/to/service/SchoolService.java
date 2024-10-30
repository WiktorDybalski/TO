package pl.edu.agh.iisg.to.service;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.repository.StudentRepository;
import pl.edu.agh.iisg.to.session.TransactionService;

import java.util.*;

public class SchoolService {

    private final TransactionService transactionService;

    private final StudentDao studentDao;

    private final CourseDao courseDao;

    private final GradeDao gradeDao;

    private final StudentRepository studentRepository;

    public SchoolService(TransactionService transactionService, StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao,
                         StudentRepository studentRepository) {
        this.transactionService = transactionService;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
        this.studentRepository = studentRepository;
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

    public void removeStudent(int indexNumber) {
        // TODO - implement
        transactionService.doAsTransaction(() -> {
            Optional<Student> studentOptional = studentDao.findByIndexNumber(indexNumber);
            studentRepository.remove(studentOptional.get());
            return null;
        });
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
        List<Student> students = studentRepository.findAllByCourseName(courseName);
        if (students.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, List<Float>> grades = new HashMap<>();
        for (Student s : students) {
            grades.put(s.fullName(), s.gradeSet().stream().map(Grade::grade).toList());
        }
        return grades;
    }
}
