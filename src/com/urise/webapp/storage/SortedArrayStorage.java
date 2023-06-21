package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int findIndex(String uuid) {
        Resume searchRey = new Resume();
        searchRey.setUuid(uuid);
        return Arrays.binarySearch(storage,0,size,searchRey);
    }
}
