package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerialize;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new FileStorage(new ObjectStreamSerialize(), STORAGE_DIR));
    }
}