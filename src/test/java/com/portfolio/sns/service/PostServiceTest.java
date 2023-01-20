package com.portfolio.sns.service;

import com.portfolio.sns.SnsApplication;
import com.portfolio.sns.exception.ErrorCode;
import com.portfolio.sns.exception.SnsApplicationException;
import com.portfolio.sns.fixture.PostEntityFixture;
import com.portfolio.sns.fixture.UserEntityFixture;
import com.portfolio.sns.model.entity.PostEntity;
import com.portfolio.sns.model.entity.UserEntity;
import com.portfolio.sns.repository.PostEntityRepository;
import com.portfolio.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {
    @Autowired
    private  PostService postService;
    @MockBean
    private PostEntityRepository postEntityRepository;
    @MockBean
    private UserEntityRepository userEntityRepository;
    @Test
    void 포스트작성이_성공하는경우(){
        String title = "title";
        String body = "body";
        String userName = "userName";

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));


        Assertions.assertDoesNotThrow(()->postService.create(title,body,userName));
    }

    @Test
    void 포스트작성시_요청한유저가_존재하지않는경우(){
        String title = "title";
        String body = "body";
        String userName = "userName";

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        SnsApplicationException e =  Assertions.assertThrows(SnsApplicationException.class,()->postService.create(title,body,userName));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND,e.getErrorCode());

    }

    @Test
    void 포스트수정성공(){
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId=1;

        PostEntity postEntity = PostEntityFixture.get(userName,postId,1);
        UserEntity userEntity =  postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postEntityRepository.saveAndFlush(any())).thenReturn(postEntity);

        Assertions.assertDoesNotThrow(()-> postService.modify(title,body,userName,postId));

    }

    @Test
    void 포스트수정시_포스트가_존재하지않는_경우(){
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId=1;

        PostEntity postEntity = PostEntityFixture.get(userName,postId,1);
        UserEntity userEntity =  postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, ()-> postService.modify(title,body,userName,postId));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND,e.getErrorCode());

    }

    @Test
    void 포스트수정시_권한이_없는_경우(){
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId=1;

        PostEntity postEntity = PostEntityFixture.get(userName,postId,1);
        UserEntity writer = UserEntityFixture.get("userName1","password",2);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, ()-> postService.modify(title,body,userName,postId));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION,e.getErrorCode());

    }
}