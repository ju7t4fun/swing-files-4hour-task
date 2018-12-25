package name.just4fun.task.persistent;

import name.just4fun.task.domain.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @since 1st iteration
 */
public class FileStudentProvider implements StudentProvider {

    private final static Logger LOG = LogManager.getLogger(FileStudentProvider.class);
    public static final String CHARSET = "ISO-8859-1";
    private final Path path;

    private static final String SEPERATOR = ",";

    public FileStudentProvider(Path path) {
        this.path =  path;
    }

    public FileStudentProvider(URI uri) {
        this.path =  Paths.get(uri);
    }

    @Override
    public List<Student> findAll() {
        Charset charset = Charset.forName(CHARSET);

        try {
            List<Student> students = new ArrayList<>();
            List<String> lines = Files.readAllLines(path, charset);

            for (String line : lines) {
                Student student = readRecord(line);
                if (student != null) {
                    students.add(student);
                }
            }
            return students;
        } catch (IOException e) {
            LOG.error("Error during file reading.", e);
        }

        return Collections.emptyList();
    }

    private Student readRecord(String line){
        try {
            String[] split = line.split(SEPERATOR);
            if(split.length == 2){
                return new Student(split[0], Float.parseFloat(split[1]));
            } else {
                LOG.warn("Unexpected amount of tokens. Skipping record.");
            }
        }catch (NumberFormatException | NullPointerException ex){
            LOG.warn("Error during record parsing. Skipping record.", ex);
        }
        return null;
    }


    @Override
    public void saveAll(List<Student> students) {
        Stream<String> stream = students.stream().map(s -> "" + s.getName() + SEPERATOR + s.getGrade());

        try {
            // also it will be nice to convert stream into iterator
            Files.write(path, stream.collect(Collectors.toList()), StandardOpenOption.WRITE);
        } catch (IOException e) {
            LOG.error("Error during file writing.", e);
        }
    }
}
