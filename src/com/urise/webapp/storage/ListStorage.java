package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> STORAGE = new ArrayList<>();

    @Override
    public Object getSearchKey(Object uuid) {
        for (int i = 0; i < STORAGE.size(); i++) {
            if (STORAGE.get(i).getUuid().equals(uuid)) {
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
        STORAGE.clear();
    }

    @Override
    public void doUpdate(Resume resume, Object searchKey) {
        STORAGE.set((Integer) searchKey, resume);
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        STORAGE.add(resume);
    }

    @Override
    public Resume doGet(Object searchKey) {
        return STORAGE.get((Integer) searchKey);
    }

    @Override
    public void doDelete(Object searchKey) {
        STORAGE.remove((int) searchKey);
    }

    @Override
    public List<Resume> doGetAll() {
        return List.of(STORAGE.toArray(new Resume[0]));
    }

    @Override
    public int doSize() {
        return STORAGE.size();
    }
}
