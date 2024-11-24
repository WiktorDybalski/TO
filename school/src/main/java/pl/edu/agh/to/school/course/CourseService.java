package pl.edu.agh.to.school.course;

import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.student.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public List<Student> getStudentsWithID(int id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get().getStudents();
        }
        return new ArrayList<>();
    }
}
