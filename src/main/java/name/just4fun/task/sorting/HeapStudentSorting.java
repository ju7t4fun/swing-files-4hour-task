package name.just4fun.task.sorting;

import name.just4fun.task.domain.Student;

import java.util.Arrays;
import java.util.List;

/**
 * @since 2th iteration
 * @see 'https://www.geeksforgeeks.org/heap-sort/'
 */
public class HeapStudentSorting implements StudentSorting {

    @Override
    public List<Student> sort(List<Student> students) {
        Student[] stockArr = new Student[students.size()];
        stockArr = students.toArray(stockArr);

        sort(stockArr);
        return Arrays.asList(stockArr);
    }

    protected float calc(Student s){
        return s.getGrade();
    }
    private boolean check(Student a, Student b){
        return calc(a) < calc(b);
    }

    protected void sort(Student[] arr)
    {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {
            // Move current root to end
            Student temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    private void heapify(Student[] arr, int n, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2*i + 1; // left = 2*i + 1
        int r = 2*i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && check(arr[l],arr[largest]))
            largest = l;

        // If right child is larger than largest so far
        if (r < n && check(arr[r],arr[largest]))
            largest = r;

        // If largest is not root
        if (largest != i)
        {
            Student swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }
}
