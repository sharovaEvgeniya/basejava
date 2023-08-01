package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new ArrayList<>();

    public int getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        if (getKey(resume.getUuid()) == -1) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage.set(getKey(resume.getUuid()), resume);
        }
    }

    @Override
    public void save(Resume resume) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).toString().equals(resume.getUuid())) {
                throw new ExistStorageException(resume.getUuid());
            }
        }
        storage.add(resume);
    }

    @Override
    public Resume get(String uuid) {
        if (getKey(uuid) == -1) {
            throw new NotExistStorageException(uuid);
        } else {
            return storage.get(getKey(uuid));
        }
    }

    @Override
    public void delete(String uuid) {
        if (getKey(uuid) == -1) {
            throw new NotExistStorageException(uuid);
        } else {
            storage.remove(getKey(uuid));
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
