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
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement ps =
                         connection.prepareStatement("SELECT * FROM resume WHERE uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps =
                         connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, resume);
                }
            }
            try (PreparedStatement ps =
                         connection.prepareStatement("SELECT * FROM section WHERE resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, resume);
                }
            }
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
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            try (PreparedStatement ps =
                         connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumeMap.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps =
                         connection.prepareStatement("SELECT * FROM contact ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, resumeMap.get(rs.getString("resume_uuid")));
                }
            }
            try (PreparedStatement ps =
                         connection.prepareStatement("SELECT * FROM section ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, resumeMap.get(rs.getString("resume_uuid")));
                }
            }
            return new ArrayList<>(resumeMap.values());
        });
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
                SectionType type = SectionType.valueOf(e.getKey().name());
                switch (type) {
                    case OBJECTIVE, PERSONAL -> {
                        TextSection textSection = (TextSection) e.getValue();
                        ps.setString(3, textSection.getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> strings = ((ListSection) e.getValue()).getStrings();
                        String str = String.join("\n", strings);
                        ps.setString(3, str);
                    }
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

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            switch (type) {
                case OBJECTIVE, PERSONAL -> resume.addSection(type, new TextSection(value));
                case ACHIEVEMENT, QUALIFICATION -> resume.addSection(type, new ListSection(value.split("\n")));
            }
        }
    }
}
