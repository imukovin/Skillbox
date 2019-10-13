import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.swing.plaf.IconUIResource;
import java.lang.reflect.Parameter;
import java.util.List;

public class Loader {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

//        List<Course> courses = session.createQuery("From Course").list();
//        System.out.println(courses.size());

        List<PurchaseList> purchaseList = session.createQuery("From " + PurchaseList.class.getSimpleName()).getResultList();

        System.out.println(purchaseList.size());

        sessionFactory.close();
    }
}
