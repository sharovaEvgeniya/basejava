package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    public Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isExist(Integer searchKey) {
        return searchKey != -1;
    }

    @Override
    public void doClear() {
        storage.clear();
    }

    @Override
    public void doUpdate(Resume resume, Integer searchKey) {
        storage.set(searchKey, resume);
    }

    @Override
    public void doSave(Resume resume, Integer searchKey) {
        storage.add(resume);
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public void doDelete(Integer searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    public List<Resume> doGetAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public int doSize() {
        return storage.size();
    }
}
