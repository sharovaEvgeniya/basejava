package com.urise.webapp.storage;

public  class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new FileStorage(new ObjectStreamSerialize(), STORAGE_DIR));
    }
}