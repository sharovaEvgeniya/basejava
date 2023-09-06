package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");

        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doClear() {
        File[] files = fileIsNotNull(directory);
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("File not updated", resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException("File is not created", file.getName());
            }
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("Io error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("File not get", file.getName(), e);
        }
    }


    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File not deleted", file.getName());
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> resumes = new ArrayList<>();
        File[] files = fileIsNotNull(directory);
        for (File file : files) {
            try {
                resumes.add(doRead(file));
            } catch (IOException e) {
                throw new StorageException("resume not added", file.getName(), e);
            }
        }
        return resumes;
    }

    @Override
    protected int doSize() {
        return fileIsNotNull(directory).length;
    }

    private void doWrite(Resume resume, File file) throws IOException {
    }

    private Resume doRead(File file) throws IOException {
        return null;
    }

    private File[] fileIsNotNull(File file) {
        File[] files = file.listFiles();
        if (files == null) {
            throw new StorageException("IO exception in", directory.getName());
        }
        return files;
    }
}
