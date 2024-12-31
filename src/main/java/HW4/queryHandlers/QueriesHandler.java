package HW4.queryHandlers;

import org.hibernate.Session;

import java.io.Serializable;

public abstract class QueriesHandler<T, PK extends Serializable> {
    private final Class<T> type;

    public QueriesHandler(Class<T> type) {
        this.type = type;
    }

    public void insert(Session session, T object) {
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
    }

    public void update(Session session, T object) {
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
    }

    public void delete(Session session, T object) {
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
    }

    public T get(Session session, PK id) {
        session.beginTransaction();
        T object = session.get(type, id);
        session.getTransaction().commit();

        return object;
    }
}
