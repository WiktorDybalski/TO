package pl.edu.agh.to.school.course;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.to.school.student.Student;

import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping("getStudents/{id}")
    public List<Student> getCourses(@PathVariable int id) {
        return courseService.getStudentsWithID(id);
    }
}
