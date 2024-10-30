package pl.edu.agh.iisg.to.dao;

import org.hibernate.Session;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.SessionService;

import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentDao extends GenericDao<Student> {

    public StudentDao(SessionService sessionService) {
        super(sessionService, Student.class);
    }

    public Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
        Student student = new Student(firstName, lastName, indexNumber);
        return save(student);
    }

    public List<Student> findAll() {
        // TODO - implement
        try {
            return currentSession().createQuery("SELECT s from Student s ORDER BY s.lastName", Student.class)
                    .getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Optional<Student> findByIndexNumber(final int indexNumber) {
        // TODO - implement
        try {
            return currentSession().createQuery("SELECT s FROM Student s WHERE s.indexNumber = :indexNumber", Student.class)
                    .setParameter("indexNumber", indexNumber)
                    .uniqueResultOptional();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
