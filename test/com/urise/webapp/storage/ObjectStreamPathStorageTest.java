package com.urise.webapp.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(new ObjectStreamSerialize(), STORAGE_DIR.getAbsolutePath()));
    }
}