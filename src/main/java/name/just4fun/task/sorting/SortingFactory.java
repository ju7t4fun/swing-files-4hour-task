package name.just4fun.task.sorting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @since 2th iteration
 * DI will be preferable and will be implemented if I have time.
 */
public class SortingFactory {
    private final Map<String, Class<? extends StudentSorting>> methodMap = new HashMap<>();

    private boolean errorTolerance = false;

    private void add(String name, Class<? extends StudentSorting> ref){
        methodMap.put(name,ref);
    }

    public SortingFactory() {
        initMethods();
    }

    public void initMethods(){
        add("HEAP", HeapStudentSorting.class);
    }

    public StudentSorting createSortingMethod(String method) {
        Class<? extends StudentSorting> aClass = methodMap.get(method);
        if (aClass == null) {
            if (errorTolerance) {
                Iterator<Map.Entry<String, Class<? extends StudentSorting>>> iterator = methodMap.entrySet().iterator();
                if (iterator.hasNext()) {
                    aClass = iterator.next().getValue();
                } else {
                    throw new RuntimeException("At least one sorting method must be founded.");
                }
            }else {
                throw new RuntimeException("Sorting method has not been founded.");
            }
        }

        try {
            return aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Can not create sorting object.", e);
        }
    }

}
