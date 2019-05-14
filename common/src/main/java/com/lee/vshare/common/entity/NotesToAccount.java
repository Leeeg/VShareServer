package com.lee.vshare.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "v_notes_account")
public class NotesToAccount implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 笔记Id
     */
    @Column(name = "note_id")
    private Long noteId;

    /**
     * 用户Id
     */
    @Column(name = "note_author_id")
    private Long noteAuthorId;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取笔记Id
     *
     * @return note_id - 笔记Id
     */
    public Long getNoteId() {
        return noteId;
    }

    /**
     * 设置笔记Id
     *
     * @param noteId 笔记Id
     */
    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    /**
     * 获取用户Id
     *
     * @return note_author_id - 用户Id
     */
    public Long getNoteAuthorId() {
        return noteAuthorId;
    }

    /**
     * 设置用户Id
     *
     * @param noteAuthorId 用户Id
     */
    public void setNoteAuthorId(Long noteAuthorId) {
        this.noteAuthorId = noteAuthorId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", noteId=").append(noteId);
        sb.append(", noteAuthorId=").append(noteAuthorId);
        sb.append("]");
        return sb.toString();
    }
}