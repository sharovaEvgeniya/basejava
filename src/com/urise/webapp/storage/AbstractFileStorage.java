package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import java.io.*;
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

    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;

    protected abstract Resume doRead(InputStream inputStream) throws IOException;

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
        File[] files = getCheckedListFiles(directory);
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume,new BufferedOutputStream( new FileOutputStream(file)));
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
            doUpdate(resume, file);
        } catch (IOException e) {
            throw new StorageException("Io error", file.getName(), e);
        }
//        try{
//            file.createNewFile();
//        } catch (IOException e) {
//            throw new StorageException("Io error", file.getName(), e);
//        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream( new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File is not get", file.getName(), e);
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
        File[] files = getCheckedListFiles(directory);
        for (File file : files) {
            resumes.add(doGet(file));
        }
        return resumes;
    }

    @Override
    protected int doSize() {
        return getCheckedListFiles(directory).length;
    }

    private File[] getCheckedListFiles(File file) {
        File[] files = file.listFiles();
        if (files == null) {
            throw new StorageException("IO exception in", directory.getName());
        }
        return files;
    }
}
