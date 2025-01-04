package pet.lovers.service;

import pet.lovers.entities.Teacher;
import pet.lovers.repositories.CourseRepository;
import pet.lovers.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private TeacherRepository teacherRepository;

    private CourseRepository courseRepository;

    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public List<Teacher> getTeachers(){
        return teacherRepository.findAll();
    }

    @Transactional
    public Teacher getTeacher(Integer teacherId) {
        return teacherRepository.findById(teacherId).get();
    }

    @Transactional
    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

}
