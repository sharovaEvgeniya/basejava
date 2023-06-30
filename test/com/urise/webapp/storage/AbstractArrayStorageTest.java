package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume resume4 = new Resume(UUID_4);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void update() throws Exception {
        Resume resumeTest = new Resume(UUID_1);
        storage.update(resume1);
        Assert.assertEquals(resume1, resumeTest);
    }

    @Test
    public void save() throws Exception {
        storage.save(resume4);
        Assert.assertEquals("uuid4", resume4.getUuid());
    }

    @Test
    public void delete() throws Exception {
        storage.delete(resume1.getUuid());
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() throws Exception {
        storage.get(resume1.getUuid());
        Assert.assertEquals("uuid1", resume1.getUuid());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumeArr = storage.getAll();
        Assert.assertEquals(resumeArr[0].getUuid(),resume1.getUuid());
        Assert.assertEquals(resumeArr[1].getUuid(),resume2.getUuid());
        Assert.assertEquals(resumeArr[2].getUuid(),resume3.getUuid());
        Assert.assertEquals(3,storage.size());
    }
    @Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        Resume[] storage = new Resume[3];
        for(int i =0; i < storage.length + 1; i++) {
            storage[i] = new Resume();
        }
    }
    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception{
        storage.save(resume1);
    }
    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}