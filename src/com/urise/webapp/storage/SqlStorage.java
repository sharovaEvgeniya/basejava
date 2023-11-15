package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public static String sqlRequest;
    public static SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlRequest = "DELETE FROM resume";
        sqlHelper.connectWithException(sqlRequest);
    }

    @Override
    public void update(Resume resume) {
        sqlRequest = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        sqlHelper.connectWithException(sqlRequest, ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(resume.getUuid());
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlRequest = "INSERT INTO resume(uuid, full_name) VALUES (?,?)";
        sqlHelper.connectWithException(resume.getUuid(), sqlRequest, ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        sqlRequest = "SELECT * FROM resume WHERE uuid =?";
        return sqlHelper.connectWithException(sqlRequest, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlRequest = "DELETE FROM resume WHERE uuid =?";
        sqlHelper.connectWithException(sqlRequest, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        sqlRequest = "SELECT * FROM resume ORDER BY full_name";
        return sqlHelper.connectWithException(sqlRequest, ps -> {
            List<Resume> resumes = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        sqlRequest = "SELECT count(*) FROM resume";
        return sqlHelper.connectWithException(sqlRequest, ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }
}
