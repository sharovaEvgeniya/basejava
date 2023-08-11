package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> STORAGE = new LinkedHashMap<>();

    @Override
    protected Object getSearchKey(Object uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return STORAGE.containsKey((String) searchKey);
    }

    @Override
    protected void doClear() {
        STORAGE.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        STORAGE.put((String) searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        STORAGE.putIfAbsent(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return STORAGE.get((String) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        STORAGE.remove((String) searchKey);
    }

    @Override
    protected List<Resume> doGetAll() {
        return List.of(STORAGE.values().toArray(new Resume[0]));
    }

    @Override
    protected int doSize() {
        return STORAGE.size();
    }
}
