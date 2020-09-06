package main.services;

import main.dto.PostInform;
import main.dto.ResponsePostObject;
import main.model.Posts;
import main.model.Users;
import main.repositories.PostCommentsRepositoryImpl;
import main.repositories.PostRepositoryImpl;
import main.repositories.PostVotesRepositoryImpl;
import main.repositories.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostsService {
    @Autowired
    private PostRepositoryImpl postRepository;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private PostVotesRepositoryImpl postVotesRepository;

    @Autowired
    private PostCommentsRepositoryImpl postCommentsRepository;

    public ResponsePostObject getPosts(int offset, int limit, String mode) {
        List<Posts> posts = postRepository.getActiveAndModeratedPosts();
        List<PostInform> resultList = new ArrayList<>();

        for (int i = 0; i < posts.size(); i++) {
            if (i >= offset) {
                Posts post = posts.get(i);
                Users user = userRepository.getUserById(post.getUserId());
                int countOfLikes = postVotesRepository.getCountOfLikesByPostId(post.getId());
                int countOfDislikes = postVotesRepository.getCountOfDislikesByPostId(post.getId());
                int countOfComments = postCommentsRepository.getCountOfCommentsByPostId(post.getId());
                resultList.add(new PostInform(post.getId(), post.getTime().toEpochDay(), PostInform.createUserInform(user.getId(), user.getName()),
                        post.getTitle(), post.getText(), countOfLikes, countOfDislikes, countOfComments, post.getViewCount()));
            }
            if (i > offset + limit) {
                break;
            }
        }
        if (resultList.size() == 0) {
            return new ResponsePostObject(0, resultList);
        }
        return new ResponsePostObject(posts.size(), resultList);
    }

}
