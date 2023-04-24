import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid == storage[i].uuid.intern()) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                storage = remove(storage, i);
                size--;
            }
        }
    }

    Resume[] remove(Resume[] r, int index) {
        if (r == null || index < 0 || index >= r.length) {
            return r;
        }
        System.arraycopy(r, 0, storage, 0, index);
        System.arraycopy(r, index + 1, storage, index, r.length - index - 1);
        return r;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
