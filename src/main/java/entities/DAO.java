package entities;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;

import java.util.ArrayList;
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
        Query query = session.getNamedQuery("StatusEntity.byTaskId").setInteger(0, id);
        StatusEntity statusEntity = (StatusEntity) query.list().get(0);
        session.delete(taskEntity);
        session.delete(statusEntity);

        session.getTransaction().commit();
        session.close();
    }

    public void update(TaskEntity task, boolean status){
        Session session = getSession();
        session.beginTransaction();

        TaskEntity taskEntity = session.get(TaskEntity.class, task.getIdTask());
        taskEntity.setText(task.getText());
        Query query = session.getNamedQuery("StatusEntity.byTaskId").setInteger(0, task.getIdTask());
        StatusEntity statusEntity = (StatusEntity) query.list().get(0);
        statusEntity.setStatus(!status ? 0 : 1);

        session.getTransaction().commit();
        session.close();
    }

    public void add(TaskEntity task){
        Session session = getSession();
        session.beginTransaction();

        session.save(task);
        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setIdTask(task.getIdTask());
        statusEntity.setStatus(0);
        session.save(statusEntity);

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

    public StatusEntity getStatusByTaskId(int id){
        Session session = getSession();
        session.beginTransaction();

        Query query = session.getNamedQuery("StatusEntity.byTaskId").setInteger(0, id);
        StatusEntity statusEntity = (StatusEntity) query.list().get(0);

        session.getTransaction().commit();
        session.close();
        return statusEntity;
    }

    public List<Task> getTaskList(String filterState){
        Session session = getSession();
        session.beginTransaction();

        Query query;
        if (filterState.equals("Done")) {
//            query = session.createQuery("from TaskEntity join StatusEntity on StatusEntity.status=? where TaskEntity.idTask = StatusEntity.idTask");
            query = session.createQuery("from TaskEntity as task join StatusEntity as status on task.idTask = status.idTask where status.status=?");
            query.setInteger(0, 1);
        } else if (filterState.equals("Undone")) {
//            query = session.createQuery("from TaskEntity join StatusEntity on TaskEntity.idTask = StatusEntity.idTask where StatusEntity.status=?");
            query = session.createQuery("from TaskEntity as task join StatusEntity as status on task.idTask = status.idTask where status.status=?");
//            query = session.createQuery("from TaskEntity join StatusEntity on TaskEntity.idTask = StatusEntity.idTask where StatusEntity.status=?");
            query.setInteger(0, 0);
        } else {
            query = session.createQuery("from TaskEntity");
        }

        List<Task> result = new ArrayList<Task>();
        List<TaskEntity> taskList = (List<TaskEntity>)query.list();

        for (TaskEntity task:taskList) {
            StatusEntity status = getStatusByTaskId(task.getIdTask());
            boolean resultStatus = status.getStatus() != 0;
            result.add(new Task(task.getText(), resultStatus, task.getIdTask()));
        }
        
        session.getTransaction().commit();
        session.close();
        return result;
    }

    private Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }
}
