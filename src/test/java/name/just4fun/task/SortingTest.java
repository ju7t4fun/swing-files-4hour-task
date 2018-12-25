package name.just4fun.task;

import name.just4fun.task.domain.Student;
import name.just4fun.task.persistent.StudentProvider;
import name.just4fun.task.sorting.SortingFactory;
import name.just4fun.task.sorting.StudentSorting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @since 2st iteration
 */
public class SortingTest {

    private StudentProvider provider;
    private SortingFactory sortingFactory;

    private final static Logger LOG = LogManager.getLogger(SortingTest.class);

    @Before
    public void init() {

        sortingFactory = new SortingFactory();

        provider = mock(StudentProvider.class);
        when(provider.findAll()).thenReturn(Arrays.asList(
                new Student("Student1", 8.5f),
                new Student("Student2", 5.5f),
                new Student("Student3", 9.0f),
                new Student("Student4", 3.9f),
                new Student("Student5", 7.3f)
        ));
    }

    @Test
    public void sortingTest(){

        List<Student> all = provider.findAll();
        StudentSorting soring = sortingFactory.createSortingMethod("HEAP");
        List<Student> sortedStudents = soring.sort(all);

        for (Student student : sortedStudents) {
            LOG.debug(student);
        }


    }
}
