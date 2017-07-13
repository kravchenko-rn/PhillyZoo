package pivvit.base;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class ObjectsCollection<B> {
    private List<B> collection = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public <T extends B> T getInstance(Class<T> clazz) {
        for (B element : collection) {
            if (element.getClass().equals(clazz)) {
                return (T) element;
            }
        }

        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T data = constructor.newInstance();
            collection.add(data);
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public void clear() {
        collection.clear();
    }
}
