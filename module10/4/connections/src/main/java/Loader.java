import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Loader {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        List<Course> courses = session.createQuery("From Course", Course.class).getResultList();
        List<Student> students = session.createQuery("From Student", Student.class).getResultList();
        List<PurchaseList> purchaseList = session.createQuery("From PurchaseList", PurchaseList.class).getResultList();

        session.beginTransaction();

        for (PurchaseList pl : purchaseList) {
            int courseId = 0;
            for (Course c : courses) {
                if (pl.getId().getCourseName().equals(c.getName())) {
                    courseId = c.getId();
                }
            }
            int studentId = 0;
            for (Student s : students) {
                if (pl.getId().getStudentName().equals(s.getName())) {
                    studentId = s.getId();
                }
            }

            pl.setCourseId(courseId);
            pl.setStudentId(studentId);
            //System.out.println(pl.getId().getCourseName() + " +++" + pl.getCourseId() + " | " + pl.getId().getStudentName() + " +++" + pl.getStudentId());
            session.save(pl);
        }
        session.getTransaction().commit();
        session.close();

        sessionFactory.close();
    }
}
