package name.just4fun.task.view;

import name.just4fun.task.domain.Student;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {

    private String[] columnNames = {"Name", "Grade",};

    private static final int NAME_COLUMN = 0;
    private static final int GRADE_COLUMN = 1 ;

    private List<Student> data;

    public StudentTableModel(List<Student> students) {
        this.data = students;
    }
    public void setData(List<Student> students){
        this.data = students;
    }

    public List<Student> getData() {
        return data;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }


    public Object getValueAt(int row, int col) {
        Student student = data.get(row);
        switch (col) {
            case NAME_COLUMN:
                return student.getName();
            case GRADE_COLUMN:
                return student.getGrade();
            default:
                return null;
        }
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public void setValueAt(Object value, int row, int col) {

        Student student = data.get(row);
        switch (col) {
            case NAME_COLUMN:
                student.setName((String) value);break;
            case GRADE_COLUMN:
                student.setGrade((Float) value);break;
        }
        fireTableCellUpdated(row, col);
    }
}
