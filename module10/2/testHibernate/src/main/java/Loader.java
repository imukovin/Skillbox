import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Loader {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        Course course = session.get(Course.class, 2);
        System.out.println("id: " + course.getId());
        System.out.println("name: " + course.getName());
        System.out.println("duration: " + course.getDuration());
        System.out.println("type: " + course.getType());
        System.out.println("description: " + course.getDiscription());
        System.out.println("teacher: " + course.getTeacherId());
        System.out.println("students: " + course.getStudentCount());
        System.out.println("price: " + course.getPrice());
        System.out.println("per hour: " + course.getPricePerHour());

        sessionFactory.close();
    }
}