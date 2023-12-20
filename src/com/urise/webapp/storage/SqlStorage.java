package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.connectWithException("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps =
                         connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) throw new NotExistStorageException(resume.getUuid());
            }
            try (PreparedStatement ps =
                         connection.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            try (PreparedStatement ps =
                         connection.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps =
                         connection.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.connectWithException("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "    WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, resume);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.connectWithException("DELETE FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {

        List<Resume> resumes = sqlHelper.connectWithException("" +
                        "SELECT * FROM resume  " +
                        "ORDER BY full_name, uuid",
                ps -> {
                    Map<String, Resume> map = new LinkedHashMap<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        Resume resume;
                        if (map.containsKey(uuid)) {
                            map.get(uuid);
                        } else {
                            resume = new Resume(uuid, rs.getString("full_name"));
                            map.put(uuid, resume);
                        }
                    }
                    return new ArrayList<>(map.values());
                });
        sqlHelper.connectWithException("" +
                "SELECT * FROM contact " +
                "ORDER BY resume_uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (Resume resume : resumes) {
                    addContact(rs, resume);
                }
            }
            return null;
        });
        sqlHelper.connectWithException("" +
                "SELECT * FROM section " +
                "ORDER BY resume_uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (Resume resume : resumes) {
                    addSectionText(rs, resume);
                }
            }
            return null;
        });
        return resumes;
    }

    @Override
    public int size() {
        return sqlHelper.connectWithException("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES  (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                String sectionType = e.getValue().getClass().getSimpleName();
                if (sectionType.equals("TextSection")) {
                    TextSection textSection = (TextSection) e.getValue();
                    ps.setString(3, textSection.getContent());
                }
                if (sectionType.equals("ListSection")) {
                    String str = "";
                    List<String> strings = ((ListSection) e.getValue()).getStrings();
                    for (String string : strings) {
                        str += string + " \n";
                    }
                    ps.setString(3, str);
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            resume.addContact(type, value);
        }
    }

    private void addSectionText(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getObject("value").getClass().getSimpleName();
        SectionType type = SectionType.valueOf(rs.getString("type"));
        if (value.equals("TextSection")) {
            resume.addSection(type, new TextSection(rs.getString("value")));
        }
        if (value.equals("ListSection")) {
            List<String> strings = ((ListSection) rs.getObject("value")).getStrings();
            resume.addSection(type, new ListSection(strings));
        }
    }
}
