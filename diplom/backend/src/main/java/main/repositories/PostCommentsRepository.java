package main.repositories;

public interface PostCommentsRepository {
    int getCountOfCommentsByPostId(int postId);
}
