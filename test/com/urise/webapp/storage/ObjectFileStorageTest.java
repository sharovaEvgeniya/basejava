package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerialize;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(new ObjectStreamSerialize(), STORAGE_DIR));
    }
}