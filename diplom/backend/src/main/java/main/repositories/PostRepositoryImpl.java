package main.repositories;

import main.model.Posts;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class PostRepositoryImpl implements PostRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Posts> getActiveAndModeratedPosts() {
        return entityManager.createQuery("from Posts where is_active=1 and moderation_status='ACCEPTED'", Posts.class).getResultList();
    }
}
