import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10];
    int size;

    void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        Resume resume = new Resume();
        for (Resume r : storage) {
            if (r == null) {
                break;
            }
            if (uuid == r.uuid.intern()) {
                resume = r;
            }

        }
        return resume;
    }

    void delete(String uuid) {
        int index = 0;
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid == uuid) {
                index = i;
            }
        }
        storage = remove(storage, index);
        size--;
    }

    Resume[] remove(Resume[] r, int index) {
        if (r == null || index < 0 || index >= r.length) {
            return r;
        }
        Resume[] uniqueStorage = new Resume[r.length - 1];
        System.arraycopy(r, 0, uniqueStorage, 0, index);
        System.arraycopy(r, index + 1, uniqueStorage, index, r.length - index - 1);
        return uniqueStorage;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] uniqueStorage = new Resume[size];
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                break;
            } else {
                uniqueStorage[i] = storage[i];
            }
        }
        return uniqueStorage;
    }

    int size() {
        return size;
    }
}
