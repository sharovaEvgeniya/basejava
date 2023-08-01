package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract int getIndex(String uuid);

    protected abstract void clearStorage();

    protected abstract void updateStorage(Resume resume);

    protected abstract void saveStorage(Resume resume);

    protected abstract Resume getStorage(String uuid);

    protected abstract void deleteStorage(String uuid);

    protected abstract Resume[] getAllStorage();

    protected abstract int sizeStorage();

    @Override
    public void clear() {
        clearStorage();
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1) {
            throw new NotExistStorageException(resume.getUuid());
        }
        updateStorage(resume);
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveStorage(resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            throw new NotExistStorageException(uuid);
        }
        return getStorage(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            throw new NotExistStorageException(uuid);
        }
        deleteStorage(uuid);
    }

    @Override
    public Resume[] getAll() {
        return getAllStorage();
    }

    @Override
    public int size() {
        return sizeStorage();
    }
}
