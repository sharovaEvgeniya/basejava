package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract Object getSearchKey(Object uuid);

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
        Object searchKey = findNotExistingSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    @Override
    public final void save(Resume resume) {
        Object searchKey = findExistingSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    @Override
    public final Resume get(String uuid) {
        Object searchKey = findNotExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public final void delete(String uuid) {
        Object searchKey = findNotExistingSearchKey(uuid);
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

    private Object findExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object findNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
