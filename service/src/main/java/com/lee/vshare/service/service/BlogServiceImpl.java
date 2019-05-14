package com.lee.vshare.service.service;

import com.lee.vshare.service.dao.NotesMapper;
import com.lee.vshare.common.entity.Notes;
import com.lee.vshare.common.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-12-19
 * @Time 下午8:32
 */
@Service
public class BlogServiceImpl extends BaseService<Notes> implements BlogService {

    Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    NotesMapper notesMapper;

    @Override
    public Integer addNote(String title, String content, Boolean isPrivate, Byte type) {
        Notes notes = new Notes();
        Long lastNoteId = 0L;
        Notes lastNote = notesMapper.selectNotesDesc(1);
        if (null != lastNote) {
            logger.info("lastNote : " + lastNote.toString());
            lastNoteId = lastNote.getNoteId() == null ? 1 : lastNote.getNoteId() + 1;
        }
        notes.setNoteCreateTime(new Date());
        notes.setNoteModifiedTime(new Date());
        notes.setNoteAuthorId(123L);
        notes.setNoteLikes(0);
        notes.setNotePermission(isPrivate);
        notes.setNoteReads(0);
        notes.setNoteId(lastNoteId);
        notes.setNoteTitle(title);
        notes.setNoteContent(content);
        notes.setNoteType(type);
        notes.setNoteWords(content.length());
        notes.setNoteAuthorName("lee");
        return notesMapper.insert(notes);
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

}
