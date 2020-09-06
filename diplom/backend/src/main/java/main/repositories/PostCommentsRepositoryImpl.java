package main.repositories;

import main.model.PostComments;
import main.model.PostVotes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PostCommentsRepositoryImpl implements PostCommentsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int getCountOfCommentsByPostId(int postId) {
        return entityManager.createQuery("from PostComments where post_id='" + postId + "'", PostComments.class).getResultList().size();
    }
}
