package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> STORAGE = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return STORAGE.containsKey(searchKey);
    }

    @Override
    protected void doClear() {
        STORAGE.clear();
    }

    @Override
    protected void doUpdate(Resume resume, String searchKey) {
        STORAGE.put(searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, String searchKey) {
        STORAGE.putIfAbsent(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return STORAGE.get(searchKey);
    }

    @Override
    protected void doDelete(String searchKey) {
        STORAGE.remove(searchKey);
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(STORAGE.values());
    }

    @Override
    protected int doSize() {
        return STORAGE.size();
    }
}
