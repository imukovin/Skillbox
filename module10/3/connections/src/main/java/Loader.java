import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Loader {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        Course course = session.get(Course.class, 1);
        System.out.println(course.getStudents().size());


//        Student newStudent = new Student();
//        newStudent.setName("Hi Hi");
//        newStudent.setAge(25);
//        newStudent.setRegistrationDate(new Date());
//        course.getStudents().add(newStudent);
//
//        session.save(newStudent);
//        //session.flush();
//
//        boolean isStillInCollection = course.getStudents().contains(newStudent);
//        System.out.println(isStillInCollection);

        sessionFactory.close();
    }
}
