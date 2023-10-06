package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerialize;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(new ObjectStreamSerialize(), STORAGE_DIR.getAbsolutePath()));
    }
}