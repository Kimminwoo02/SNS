package com.portfolio.sns.repository;

import com.portfolio.sns.model.entity.CommentEntity;
import com.portfolio.sns.model.entity.LikeEntity;
import com.portfolio.sns.model.entity.PostEntity;
import com.portfolio.sns.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentEntityRepository extends JpaRepository<CommentEntity,Integer> {
    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);
}
