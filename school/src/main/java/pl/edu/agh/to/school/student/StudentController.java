package pl.edu.agh.to.school.student;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(params = "id")
    public Student getStudent(@RequestParam int id) {
        return studentService.getStudent(id);
    }

    @PostMapping(value = "/giveGrade", params = "gradeValue")
    public String giveGrade(@RequestParam int studentId, @RequestParam int gradeValue, @RequestParam int courseId) {
        studentService.giveGrade(studentId, gradeValue, courseId);
        return "Added new grade";
    }

    @GetMapping(value = "/gradesMeanForStudent", params = "studentId")
    public double gradesMeanForStudent(@RequestParam int studentId) {
        return studentService.gradesMean(studentId);
    }
}
