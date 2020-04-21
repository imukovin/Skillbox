package main.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "post_comments")
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "parent_id")
    private int parentId;
    @Column(name = "post_id")
    private int postId;
    @Column(name = "user_id")
    private int userId;
    private LocalDate time;
    private String text;
}
