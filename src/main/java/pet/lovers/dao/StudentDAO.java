package pet.lovers.dao;


import pet.lovers.entities.Student;

import java.util.List;

public interface StudentDAO {
    public List<Student> getStudents();
    public Student getStudent(Integer student_id);
    public void saveStudent(Student student);
    public void deleteStudent(Integer student_id);
}
