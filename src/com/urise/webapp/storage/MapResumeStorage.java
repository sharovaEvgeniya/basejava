package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> STORAGE = new HashMap<>();


    @Override
    protected Resume getSearchKey(String uuid) {
        return STORAGE.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doClear() {
        STORAGE.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Resume searchKey) {
        STORAGE.put(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Resume searchKey) {
        STORAGE.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doDelete(Resume searchKey) {
        STORAGE.remove(searchKey.getUuid());
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
