package pl.edu.agh.iisg.to.repository;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Student;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements Repository<Student> {

    private StudentDao studentDao;
    private CourseDao courseDao;

    public StudentRepository(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    @Override
    public Optional<Student> add(Student student) {
        return studentDao.create(student.firstName(), student.lastName(), student.indexNumber());
    }

    @Override
    public Optional<Student> getById(int id) {
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public void remove(Student student) {
        Optional<Student> studentOptional = Optional.ofNullable(student);

        if (studentOptional.isPresent()) {
            for (Course course : studentOptional.get().courseSet()) {
                course.studentSet().remove(studentOptional.get());
            }
            studentDao.remove(studentOptional.get());
        }
    }

    public List<Student> findAllByCourseName(String courseName) {
        Optional<Course> courseOptional = courseDao.findByName(courseName);
        return courseOptional.map(course -> course.studentSet().stream().toList()).orElse(Collections.emptyList());
    }
}
