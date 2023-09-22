package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectStreamSerialize;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(new ObjectStreamSerialize(), STORAGE_DIR.getAbsolutePath()));
    }
}