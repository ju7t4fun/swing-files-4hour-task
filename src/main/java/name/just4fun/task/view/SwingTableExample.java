package name.just4fun.task.view;

import name.just4fun.task.domain.Student;
import name.just4fun.task.persistent.FileStudentProvider;
import name.just4fun.task.persistent.StudentProvider;
import name.just4fun.task.sorting.SortingFactory;
import name.just4fun.task.sorting.StudentSorting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class SwingTableExample extends JPanel {

    private final static Logger LOG = LogManager.getLogger(FileStudentProvider.class);

    JPanel panel;

    private StudentProvider sp;
    StudentTableModel studentTableModel;
    SortingFactory sortingFactory;


    public SwingTableExample() {
        super(new GridLayout(1, 0));

        panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setSize(300,300);
        BorderLayout layout = new BorderLayout();
        layout.setHgap(10);
        layout.setVgap(10);

        panel.setLayout(layout);
        // sortingFactory
        sortingFactory = new SortingFactory();

        JButton loadButton = new JButton("LOAD");
        panel.add(loadButton, BorderLayout.PAGE_START);
        loadButton.addActionListener(e -> {
            final JFileChooser fc = new JFileChooser();

            int returnVal = fc.showOpenDialog(panel);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                sp = new FileStudentProvider(file.toURI());

                refreshTableWithData( sp.findAll());
                LOG.debug("Opening: " + file.getName() + ".");
            } else {
                LOG.debug("Open command cancelled by user." );
            }
        });

        List<Student> students = Collections.singletonList(
                new Student("Student", 10f)
        );
        initTableWithData(students);


        JButton sortButton = new JButton("SORT");
        panel.add(sortButton, BorderLayout.PAGE_END);
        sortButton.addActionListener(e->{

            StudentSorting soring = sortingFactory.createSortingMethod("HEAP");
            long l = System.nanoTime();
            List<Student> sorted = soring.sort(studentTableModel.getData());
            l = System.nanoTime() - l;
            LOG.info("Sorting time: " + l + "ns, records: " + sorted.size());
            studentTableModel.setData(sorted);
            panel.repaint();
        });


        //Add the scroll pane to this panel.
        add(panel);
    }
    public void refreshTableWithData(List<Student> students){
        studentTableModel.setData(students);

    }

    public void initTableWithData(List<Student> students){
        studentTableModel = new StudentTableModel(students);
        JTable table = new JTable(studentTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        createAndShowGUI();
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TestTask");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        SwingTableExample newContentPane = new SwingTableExample();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
