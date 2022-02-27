package org.yossy.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.yossy.demo.db.entity.Comment;
import org.yossy.demo.db.entity.Entry;
import org.yossy.demo.db.repository.CommentRepository;
import org.yossy.demo.db.repository.EntryRepository;
import org.yossy.demo.util.StringUtility;

@Service
public class CustomForumService implements ForumService {

    private final EntryRepository entryRepository;
    private final CommentRepository commentRepository;

    public CustomForumService(EntryRepository entryRepository, CommentRepository commentRepository) {
        this.entryRepository = entryRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Entry> getEntryList() {
        return entryRepository.findAll();
    }

    @Override
    public Entry getEntry(long id) {
        return entryRepository.getById(id);
    }

    @Override
    @Transactional
    public void registerEntry(String entryName, String comment, String userName, String[] roles) {
        Entry entry = entryRepository.save(new Entry(entryName, userName, roles, true)); 
        commentRepository.save(new Comment(entry.getId(), StringUtility.decodeNewLine(comment), userName, roles, true));
    }

    @Override
    public List<Comment> getEntryList(long entryId) {
        return commentRepository.findByEntryId(entryId).stream()
                .map(comment -> 
                        new Comment(comment.getCommentId(), comment.getEntryId(), StringUtility.encodeNewLine(comment.getComment()), comment.getUserName(), comment.getRoles(), true, comment.getCreateDt(), comment.getUpdateDt()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void registerComment(Long entryId, String comment, String userName, String[] roles) {
        commentRepository.save(new Comment(entryId, StringUtility.decodeNewLine(comment), userName, roles, true));
    }
    
}
