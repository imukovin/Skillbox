package main.repositories;

public interface PostVotesRepository {
    int getCountOfLikesByPostId(int postId);
    int getCountOfDislikesByPostId(int postId);
}
