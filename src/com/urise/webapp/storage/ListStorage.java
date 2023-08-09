package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    public Object getSearchKey(Object uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isExist(Object searchKey) {
        return (int) searchKey != -1;
    }

    @Override
    public void doClear() {
        storage.clear();
    }

    @Override
    public void doUpdate(Resume resume, Object searchKey) {
        storage.set((Integer) searchKey, resume);
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    public Resume doGet(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    public void doDelete(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    public Resume[] doGetAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int doSize() {
        return storage.size();
    }
}
