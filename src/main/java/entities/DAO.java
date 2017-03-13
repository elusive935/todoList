package entities;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;

import java.util.List;

public class DAO {
    private final SessionFactory sessionFactory;
    private static final int pageLength = 10;

    public DAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void delete(int id){
        Session session = getSession();
        session.beginTransaction();

        TaskEntity taskEntity = session.get(TaskEntity.class, id);
        session.delete(taskEntity);

        session.getTransaction().commit();
        session.close();
    }

    public void update(TaskEntity task, boolean status){
        Session session = getSession();
        session.beginTransaction();

        TaskEntity taskEntity = session.get(TaskEntity.class, task.getIdTask());
        taskEntity.setText(task.getText());
        taskEntity.setStatus(status);

        session.getTransaction().commit();
        session.close();
    }

    public void add(TaskEntity task){
        Session session = getSession();
        session.beginTransaction();

        session.save(task);

        session.getTransaction().commit();
        session.close();
    }

    public TaskEntity getTaskById(int id){
        Session session = getSession();
        session.beginTransaction();

        Query query = session.getNamedQuery("TaskEntity.getById").setInteger(0, id);
        TaskEntity taskEntity = (TaskEntity) query.list().get(0);

        session.getTransaction().commit();
        session.close();

        return taskEntity;
    }

    public List<TaskEntity> getTaskList(String filterState, int page){
        Session session = getSession();
        session.beginTransaction();

        Query query;
        if (filterState.equals("Done")) {
            query = session.createQuery("from TaskEntity as task where task.status = ?");
            query.setInteger(0, 1);
        } else if (filterState.equals("Undone")) {
            query = session.createQuery("from TaskEntity as task where task.status = ?");
            query.setInteger(0, 0);
        } else {
            query = session.createQuery("from TaskEntity");
        }

        query.setFirstResult((page-1) * pageLength);
        query.setMaxResults(pageLength);
        List<TaskEntity> result = (List<TaskEntity>)query.list();

        session.getTransaction().commit();
        session.close();
        return result;
    }

    private Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public long getTaskCount(String filterState){
        Session session = getSession();
        session.beginTransaction();

        Query query;
        if (filterState.equals("Done")) {
            query = session.createQuery("select count(*) from TaskEntity as task where task.status = ?");
            query.setInteger(0, 1);
        } else if (filterState.equals("Undone")) {
            query = session.createQuery("select count(*) from TaskEntity as task where task.status = ?");
            query.setInteger(0, 0);
        } else {
            query = session.createQuery("select count(*) from TaskEntity");
        }

        List<Long> result = (List<Long>)query.list();

        session.getTransaction().commit();
        session.close();

        return result.get(0);

    }
}
