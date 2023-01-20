package com.portfolio.sns.repository;
import com.portfolio.sns.model.Post;
import com.portfolio.sns.model.entity.LikeEntity;

import com.portfolio.sns.model.entity.PostEntity;
import com.portfolio.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity,Integer> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);
    //select count(*) from "like" where post_id = 2
    //
    @Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post =:post")
    Integer countByPost(@Param("post") PostEntity post);

    List<LikeEntity> findAllByPost(PostEntity post);
}
