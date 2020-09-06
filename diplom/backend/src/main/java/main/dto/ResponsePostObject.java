package main.dto;

import java.util.List;

public class ResponsePostObject {
    private int count;
    private List<PostInform> posts;

    public ResponsePostObject() {
    }

    public ResponsePostObject(int count, List<PostInform> posts) {
        this.count = count;
        this.posts = posts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PostInform> getPosts() {
        return posts;
    }

    public void setPosts(List<PostInform> posts) {
        this.posts = posts;
    }
}
