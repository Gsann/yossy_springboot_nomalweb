package org.yossy.demo.service;

import java.util.List;

import org.yossy.demo.db.entity.Comment;
import org.yossy.demo.db.entity.Entry;

public interface ForumService {
    public List<Entry> getEntryList();
    public Entry getEntry(long id);
    public void registerEntry(String entryName, String comment, String userName, String[] roles);
    public List<Comment> getEntryList(long id);
    public void registerComment(Long entryId, String comment, String userName, String[] roles);
}
