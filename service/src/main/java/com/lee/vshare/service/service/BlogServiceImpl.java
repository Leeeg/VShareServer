package com.lee.vshare.service.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lee.vshare.service.dao.NotesMapper;
import com.lee.vshare.common.entity.Notes;
import com.lee.vshare.common.service.BlogService;
import com.lee.vshare.service.service.base.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-12-19
 * @Time 下午8:32
 */
@Service(version = "1.0.0")
public class BlogServiceImpl extends BaseService<Notes> implements BlogService {

    Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    NotesMapper notesMapper;

    @Override
    public Integer addNote(String title, String content, String describe, Boolean isPrivate, Byte type, Long noteId) {

        Example example = new Example(Notes.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("noteId", noteId);
        Notes notesE = notesMapper.selectOneByExample(example);
        Notes notes = new Notes();
        if (null != notesE) {
            logger.info("{} lastNote : {}", "修改 >>> ", notesE.toString());
            notesE.setNoteModifiedTime(new Date());
            notesE.setNotePermission(isPrivate);
            notesE.setNoteTitle(title);
            notesE.setNoteContent(content);
            notesE.setNoteType(type);
            notesE.setNoteDescribe(describe);
            notesE.setNoteWords(content.length());
            return notesMapper.updateByExampleSelective(notesE, example);
        } else {
            logger.info("{} lastNote : {}", "添加 >>> ", notes.toString());
            notes.setNoteCreateTime(new Date());
            notes.setNoteModifiedTime(new Date());
            notes.setNoteAuthorId(123L);
            notes.setNoteLikes(0);
            notes.setNotePermission(isPrivate);
            notes.setNoteReads(0);
            notes.setNoteId(noteId);
            notes.setNoteTitle(title);
            notes.setNoteContent(content);
            notes.setNoteType(type);
            notes.setNoteShare(1);
            notes.setNoteWords(content.length());
            notes.setNoteAuthorName("lee");
            return notesMapper.insert(notes);
        }
    }

    @Override
    public Integer deleteNotesById(Long... ids) {
        Example example = new Example(Notes.class);
        Example.Criteria criteria = example.createCriteria();
        for (Long id : ids) {
            criteria.orEqualTo("noteId", id);
        }
        return notesMapper.deleteByExample(example);
    }

    @Override
    public Integer updateNote(Long noteId, String title, String content, Boolean isPrivate, Byte type) {
        Example example = new Example(Notes.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("noteId", noteId);

        Notes notes = new Notes();
        notes.setNoteModifiedTime(new Date());
        notes.setNotePermission(isPrivate);
        notes.setNoteTitle(title);
        notes.setNoteContent(content);
        notes.setNoteType(type);
        notes.setNoteWords(content.length());
        return notesMapper.updateByExampleSelective(notes, example);
    }

    @Override
    public List<Notes> getAllNotes() {
        logger.info("getAllNotes");
        return notesMapper.selectAll();
    }

    @Override
    public List<Notes> getNotesById(Long... ids) {
        Example example = new Example(Notes.class);
        Example.Criteria criteria = example.createCriteria();
        for (Long id : ids) {
            criteria.orEqualTo("noteId", id);
        }
        return notesMapper.selectByExample(example);
    }

    @Override
    public List<Map<String, Object>> getNotesList() {
        return notesMapper.selectNotesWithoutContent();
    }

}
