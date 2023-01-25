package com.portfolio.sns.controller.response;

import com.portfolio.sns.model.Comment;
import com.portfolio.sns.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class CommentResponse {

    private Integer id;
    private String comment;
    private String userName;
    private Integer postId;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;



    public  static CommentResponse fromComment(Comment comment){
        return new CommentResponse(
               comment.getId(),
               comment.getComment(),
               comment.getUserName(),
               comment.getPostId(),
               comment.getRegisteredAt(),
               comment.getUpdatedAt(),
               comment.getDeletedAt()
        );
    }
}
