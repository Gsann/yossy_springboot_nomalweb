package org.yossy.demo.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yossy.demo.db.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByEntryId(long entryId);
}
