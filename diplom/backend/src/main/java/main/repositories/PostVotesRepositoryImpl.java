package main.repositories;

import main.model.PostVotes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PostVotesRepositoryImpl implements PostVotesRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int getCountOfLikesByPostId(int postId) {
        return entityManager.createQuery("from PostVotes where value=1 and post_id='" + postId + "'", PostVotes.class).getResultList().size();
    }

    @Override
    public int getCountOfDislikesByPostId(int postId) {
        return entityManager.createQuery("from PostVotes where value=-1 and post_id='" + postId + "'", PostVotes.class).getResultList().size();
    }
}
