package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> STORAGE = new ArrayList<>();
    private static final Comparator<Resume> RESUME_FULLNAME_COMPARATOR = Comparator.comparing(Resume::getFullName);
    @Override
    public Object getSearchKey(Object uuid) {
        for (int i = 0; i < STORAGE.size(); i++) {
            if (STORAGE.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isExist(Object searchKey) {
        return (int) searchKey != -1;
    }

    @Override
    public void doClear() {
        STORAGE.clear();
    }

    @Override
    public void doUpdate(Resume resume, Object searchKey) {
        STORAGE.set((Integer) searchKey, resume);
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        STORAGE.add(resume);
    }

    @Override
    public Resume doGet(Object searchKey) {
        return STORAGE.get((Integer) searchKey);
    }

    @Override
    public void doDelete(Object searchKey) {
        STORAGE.remove((int) searchKey);
    }

    @Override
    public List<Resume> doGetAllSorted() {
        Comparator<Resume> resumeComparator = RESUME_FULLNAME_COMPARATOR.thenComparing(new ResumeUuidComparator());
        TreeSet<Resume> listSortedComparator = new TreeSet<>(resumeComparator);
        listSortedComparator.addAll(STORAGE.stream().toList());
        return listSortedComparator.stream().toList();
    }

    @Override
    public int doSize() {
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
