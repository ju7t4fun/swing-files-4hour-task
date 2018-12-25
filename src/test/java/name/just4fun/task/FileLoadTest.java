package name.just4fun.task;

import name.just4fun.task.domain.Student;
import name.just4fun.task.persistent.FileStudentProvider;
import name.just4fun.task.persistent.StudentProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @since 1st iteration
 */
public class FileLoadTest {

    private final static Logger LOG = LogManager.getLogger(FileLoadTest.class);

    private StudentProvider provider;

    @Before
    public void init() throws URISyntaxException {

        URI path = FileLoadTest.class.getClassLoader().getResource("data.csv").toURI();
        provider = new FileStudentProvider(Paths.get(path));
    }

    @Test
    public void testFileLoading(){
        List<Student> students = provider.findAll();

        Assert.assertFalse("Data has not been found.",students.isEmpty());
        for (Student student : students) {
            Assert.assertNotNull(student.getName());
            Assert.assertNotEquals(student.getGrade(), .0f, .0001f);
            LOG.debug(student);
        }
    }



}
