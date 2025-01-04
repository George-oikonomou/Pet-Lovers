package pet.lovers.controllers;

import pet.lovers.entities.Teacher;
import pet.lovers.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("teacher")
public class TeacherController {


    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    //    @PostConstruct
//    public void setup() {
//       Teacher t1 = new Teacher("Prof","Mark","Tailor","mark@company.com");
//       Teacher t2 = new Teacher("Lec","John","Marmor","john@example.com");
//
//       teacherService.saveTeacher(t1);
//       teacherService.saveTeacher(t2);
//    }

    @RequestMapping()
    public String showTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getTeachers());
        return "teacher/teachers";
    }

    @GetMapping("/{id}")
    public String showTeacher(@PathVariable Integer id, Model model){
        Teacher teacher = teacherService.getTeacher(id);
        model.addAttribute("teachers", teacher);
        return "teacher/teachers";
    }

    @GetMapping("/new")
    public String addTeacher(Model model){
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "teacher/teacher";

    }

    @PostMapping("/new")
    public String saveTeacher(@ModelAttribute("course") Teacher teacher, Model model) {
        teacherService.saveTeacher(teacher);
        model.addAttribute("teachers", teacherService.getTeachers());
        return "teacher/teachers";
    }

    @GetMapping("/{id}/courses")
    public String showCourses(@PathVariable("id") Integer id, Model model){
        Teacher teacher = teacherService.getTeacher(id);
        model.addAttribute("courses", teacher.getCourses());
        return "course/courses";
    }

}
