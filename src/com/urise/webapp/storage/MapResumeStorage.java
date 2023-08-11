package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> STORAGE = new LinkedHashMap<>();
    private static final Comparator<Resume> RESUME_FULLNAME_COMPARATOR = Comparator.comparing(Resume::getFullName);

    @Override
    protected Object getSearchKey(Object uuid) {
        return STORAGE.get((String) uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doClear() {
        STORAGE.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        STORAGE.put(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        STORAGE.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doDelete(Object searchKey) {
        STORAGE.remove(((Resume) searchKey).getUuid());
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
