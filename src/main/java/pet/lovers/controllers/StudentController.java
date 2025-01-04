package pet.lovers.controllers;

import pet.lovers.entities.Student;
import pet.lovers.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("student")
public class StudentController {


   StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //    @PostConstruct
//    public void setup() {
//        Student Stud1= new Student("Nick", "Jones", "nick@hua.gr");
//        Student Stud2= new Student("Jack", "James", "jack@hua.gr");
//        Student Stud3= new Student("John", "Stone", "john@hua.gr");
//        studentService.saveStudent(Stud1);
//        studentService.saveStudent(Stud2);
//        studentService.saveStudent(Stud3);
//    }


    @GetMapping("")
    public String showStudents(Model model){
        model.addAttribute("students", studentService.getStudents());
        return "student/students";
    }

    @GetMapping("/{id}")
    public String showStudent(@PathVariable Integer id, Model model) {
        try {
            model.addAttribute("student", studentService.getStudent(id));
            return "student/student";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found", e);
        }
    }

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable Integer id, Model model){
        model.addAttribute("student", studentService.getStudent(id));
        return "student/student-profile";
    }

    @GetMapping("/new")
    public String addStudent(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "student/student";
    }

    @PostMapping("/new")
    public String saveStudent(@ModelAttribute("student") Student student, Model model) {
        studentService.saveStudent(student);
        model.addAttribute("students", studentService.getStudents());
        return "student/students";
    }
}