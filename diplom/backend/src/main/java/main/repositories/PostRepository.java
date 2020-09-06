package main.repositories;

import main.model.Posts;
import java.util.List;

public interface PostRepository {

    List<Posts> getActiveAndModeratedPosts();

}
