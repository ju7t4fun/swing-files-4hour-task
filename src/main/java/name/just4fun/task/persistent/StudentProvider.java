package name.just4fun.task.persistent;

import name.just4fun.task.domain.Student;

import java.util.List;

/**
 * @since 1st iteration
 */
public interface StudentProvider {
    List<Student> findAll();
    void saveAll(List<Student> students);
}
