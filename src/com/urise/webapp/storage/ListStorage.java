package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new ArrayList<>();

    public int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void clearStorage() {
        storage.clear();
    }

    @Override
    public void updateStorage(Resume resume) {
        storage.set(getIndex(resume.getUuid()), resume);
    }

    @Override
    public void saveStorage(Resume resume) {
        storage.add(resume);
    }

    @Override
    public Resume getStorage(String uuid) {
        return storage.get(getIndex(uuid));
    }

    @Override
    public void deleteStorage(String uuid) {
        storage.remove(getIndex(uuid));
    }

    @Override
    public Resume[] getAllStorage() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int sizeStorage() {
        return storage.size();
    }
}
