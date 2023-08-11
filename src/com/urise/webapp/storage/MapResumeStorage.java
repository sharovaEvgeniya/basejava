package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> STORAGE = new LinkedHashMap<>();


    @Override
    protected Object getSearchKey(Object uuid) {
        return STORAGE.get((String) uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doClear() {
        STORAGE.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        STORAGE.put(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        STORAGE.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doDelete(Object searchKey) {
        STORAGE.remove(((Resume) searchKey).getUuid());
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
