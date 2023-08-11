package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> STORAGE = new LinkedHashMap<>();
    private static final Comparator<Resume> RESUME_FULLNAME_COMPARATOR = Comparator.comparing(Resume::getFullName);

    @Override
    protected Object getSearchKey(Object uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return STORAGE.containsKey((String) searchKey);
    }

    @Override
    protected void doClear() {
        STORAGE.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        STORAGE.put((String) searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        STORAGE.putIfAbsent(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return STORAGE.get((String) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        STORAGE.remove((String) searchKey);
    }

    @Override
    protected List<Resume> doGetAllSorted() {
        Comparator<Resume> resumeComparator = RESUME_FULLNAME_COMPARATOR.thenComparing(new ResumeUuidComparator());
        TreeSet<Resume> listSortedComparator = new TreeSet<>(resumeComparator);
        listSortedComparator.addAll(STORAGE.values());
        return listSortedComparator.stream().toList();
    }

    @Override
    protected int doSize() {
        return STORAGE.size();
    }

    private static class ResumeUuidComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume o1, Resume o2) {
            if (o1.getFullName().equals(o2.getFullName())) {
                return o1.getFullName().compareTo(o2.getFullName());
            }
            return o1.getUuid().compareTo(o2.getUuid());
        }
    }
}
