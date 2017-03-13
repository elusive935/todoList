import entities.DAO;
import workflow.HibernateSessionFactory;

public final class App {

    public static void main(final String[] args) throws Exception {
        System.out.println("ToDoList App started");

        DAO dao = new DAO(HibernateSessionFactory.getSessionFactory());

//        for (int i = 0; i < 10; i++) {
//            TaskEntity taskEntity = new TaskEntity();
//            taskEntity.setText("testText " + i);
//            dao.add(taskEntity);
//        }

//        dao.delete(1);
//        dao.delete(3);
//        dao.delete(5);
//        dao.delete(7);

//        TaskEntity taskEntity = dao.getTaskById(9);
//        taskEntity.setText("New text");
//        dao.update(taskEntity, true);


//        Session session = getSession();
//        try {
//            System.out.println("querying all the managed entities...");
//            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
//            for (EntityType<?> entityType : metamodel.getEntities()) {
//                final String entityName = entityType.getName();
//                final Query query = session.createQuery("from " + entityName);
//                System.out.println("executing: " + query.getQueryString());
//                for (Object o : query.list()) {
//                    System.out.println("  " + o);
//                }
//            }
//        } finally {
//            session.close();
//        }

        HibernateSessionFactory.shutdown();
    }
}