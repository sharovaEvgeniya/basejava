package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> storage = new HashMap<>();

    @Override
    protected int getIndex(String uuid) {
        int i = 0;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getKey().equals(uuid)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey != -1;
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getKey().equals(resume.getUuid())) {
                storage.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        int i = 0;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (i == (int) searchKey) {
                return entry.getValue();
            }
            i++;
        }
        return null;
    }

    @Override
    protected void doDelete(Object searchKey) {
        int i = 0;
        Object key = null;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (i == (int) searchKey) {
                key = entry.getKey();
            }
            i++;
        }
        storage.remove(key);
    }

    @Override
    protected Resume[] doGetAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected int doSize() {
        return storage.size();
    }
}
