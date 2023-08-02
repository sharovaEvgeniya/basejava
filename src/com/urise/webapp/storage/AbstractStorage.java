package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract int getIndex(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doClear();

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract Resume[] doGetAll();

    protected abstract int doSize();

    @Override
    public void clear() {
        doClear();
    }

    @Override
    public final void update(Resume resume) {
        Object searchKey = getIndex(resume.getUuid());
        if (!isExist(searchKey)) {
            getNotExistingSearchKey(resume.getUuid());
        }
        doUpdate(resume, searchKey);
    }

    @Override
    public final void save(Resume resume) {
        Object searchKey = getIndex(resume.getUuid());
        if (isExist(searchKey)) {
            getExistingSearchKey(resume.getUuid());
        }
        doSave(resume, searchKey);
    }

    @Override
    public final Resume get(String uuid) {
        Object searchKey = getIndex(uuid);
        if (!isExist(searchKey)) {
            getNotExistingSearchKey(uuid);
        }
        return doGet(searchKey);
    }

    @Override
    public final void delete(String uuid) {
        Object searchKey = getIndex(uuid);
        if (!isExist(searchKey)) {
            getNotExistingSearchKey(uuid);
        }
        doDelete(searchKey);
    }

    @Override
    public Resume[] getAll() {
        return doGetAll();
    }

    @Override
    public int size() {
        return doSize();
    }

    private void getExistingSearchKey(String uuid) {
        throw new ExistStorageException(uuid);
    }

    private void getNotExistingSearchKey(String uuid) {
        throw new NotExistStorageException(uuid);
    }
}
