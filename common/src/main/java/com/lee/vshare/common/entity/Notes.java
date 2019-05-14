package com.lee.vshare.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "v_notes")
public class Notes implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文章Id
     */
    @Column(name = "note_id")
    private Long noteId;

    /**
     * 文章作者Id == 用户Id
     */
    @Column(name = "note_author_id")
    private Long noteAuthorId;

    /**
     * 文章Title
     */
    @Column(name = "note_title")
    private String noteTitle;

    /**
     * 文章作者Name == 用户Name
     */
    @Column(name = "note_author_name")
    private String noteAuthorName;

    /**
     * 文章描述
     */
    @Column(name = "note_describe")
    private String noteDescribe = "";

    /**
     * 文章创建时间
     */
    @Column(name = "note_create_time")
    private Date noteCreateTime;

    /**
     * 文章最后修改时间
     */
    @Column(name = "note_modified_time")
    private Date noteModifiedTime;

    /**
     * 文章字数
     */
    @Column(name = "note_words")
    private Integer noteWords;

    /**
     * 文章阅读数
     */
    @Column(name = "note_reads")
    private Integer noteReads;

    /**
     * 文章分享数
     */
    @Column(name = "note_share")
    private Integer noteShare;

    /**
     * 文章喜欢数
     */
    @Column(name = "note_likes")
    private Integer noteLikes;

    /**
     * 文章权限 0表示public
     */
    @Column(name = "note_permission")
    private Boolean notePermission;

    /**
     * 文章类型 0表示个人文章 1表示技术文档 2表示普通文档
     */
    @Column(name = "note_type")
    private Byte noteType;

    /**
     * 文章Content
     */
    @Column(name = "note_content")
    private String noteContent;

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
     * 获取文章Id
     *
     * @return note_id - 文章Id
     */
    public Long getNoteId() {
        return noteId;
    }

    /**
     * 设置文章Id
     *
     * @param noteId 文章Id
     */
    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    /**
     * 获取文章作者Id == 用户Id
     *
     * @return note_author_id - 文章作者Id == 用户Id
     */
    public Long getNoteAuthorId() {
        return noteAuthorId;
    }

    /**
     * 设置文章作者Id == 用户Id
     *
     * @param noteAuthorId 文章作者Id == 用户Id
     */
    public void setNoteAuthorId(Long noteAuthorId) {
        this.noteAuthorId = noteAuthorId;
    }

    /**
     * 获取文章Title
     *
     * @return note_title - 文章Title
     */
    public String getNoteTitle() {
        return noteTitle;
    }

    /**
     * 设置文章Title
     *
     * @param noteTitle 文章Title
     */
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle == null ? null : noteTitle.trim();
    }

    /**
     * 获取文章作者Name == 用户Name
     *
     * @return note_author_name - 文章作者Name == 用户Name
     */
    public String getNoteAuthorName() {
        return noteAuthorName;
    }

    /**
     * 设置文章作者Name == 用户Name
     *
     * @param noteAuthorName 文章作者Name == 用户Name
     */
    public void setNoteAuthorName(String noteAuthorName) {
        this.noteAuthorName = noteAuthorName == null ? null : noteAuthorName.trim();
    }

    /**
     * 获取文章描述
     *
     * @return note_describe - 文章描述
     */
    public String getNoteDescribe() {
        return noteDescribe;
    }

    /**
     * 设置文章描述
     *
     * @param noteDescribe 文章描述
     */
    public void setNoteDescribe(String noteDescribe) {
        this.noteDescribe = noteDescribe == null ? null : noteDescribe.trim();
    }

    /**
     * 获取文章创建时间
     *
     * @return note_create_time - 文章创建时间
     */
    public Date getNoteCreateTime() {
        return noteCreateTime;
    }

    /**
     * 设置文章创建时间
     *
     * @param noteCreateTime 文章创建时间
     */
    public void setNoteCreateTime(Date noteCreateTime) {
        this.noteCreateTime = noteCreateTime;
    }

    /**
     * 获取文章最后修改时间
     *
     * @return note_modified_time - 文章最后修改时间
     */
    public Date getNoteModifiedTime() {
        return noteModifiedTime;
    }

    /**
     * 设置文章最后修改时间
     *
     * @param noteModifiedTime 文章最后修改时间
     */
    public void setNoteModifiedTime(Date noteModifiedTime) {
        this.noteModifiedTime = noteModifiedTime;
    }

    /**
     * 获取文章字数
     *
     * @return note_words - 文章字数
     */
    public Integer getNoteWords() {
        return noteWords;
    }

    /**
     * 设置文章字数
     *
     * @param noteWords 文章字数
     */
    public void setNoteWords(Integer noteWords) {
        this.noteWords = noteWords;
    }

    /**
     * 获取文章阅读数
     *
     * @return note_reads - 文章阅读数
     */
    public Integer getNoteReads() {
        return noteReads;
    }

    /**
     * 设置文章阅读数
     *
     * @param noteReads 文章阅读数
     */
    public void setNoteReads(Integer noteReads) {
        this.noteReads = noteReads;
    }

    /**
     * 获取文章分享数
     *
     * @return note_share - 文章分享数
     */
    public Integer getNoteShare() {
        return noteShare;
    }

    /**
     * 设置文章分享数
     *
     * @param noteShare 文章分享数
     */
    public void setNoteShare(Integer noteShare) {
        this.noteShare = noteShare;
    }

    /**
     * 获取文章喜欢数
     *
     * @return note_likes - 文章喜欢数
     */
    public Integer getNoteLikes() {
        return noteLikes;
    }

    /**
     * 设置文章喜欢数
     *
     * @param noteLikes 文章喜欢数
     */
    public void setNoteLikes(Integer noteLikes) {
        this.noteLikes = noteLikes;
    }

    /**
     * 获取文章权限 0表示public
     *
     * @return note_permission - 文章权限 0表示public
     */
    public Boolean getNotePermission() {
        return notePermission;
    }

    /**
     * 设置文章权限 0表示public
     *
     * @param notePermission 文章权限 0表示public
     */
    public void setNotePermission(Boolean notePermission) {
        this.notePermission = notePermission;
    }

    /**
     * 获取文章类型 0表示个人文章 1表示技术文档 2表示普通文档
     *
     * @return note_type - 文章类型 0表示个人文章 1表示技术文档 2表示普通文档
     */
    public Byte getNoteType() {
        return noteType;
    }

    /**
     * 设置文章类型 0表示个人文章 1表示技术文档 2表示普通文档
     *
     * @param noteType 文章类型 0表示个人文章 1表示技术文档 2表示普通文档
     */
    public void setNoteType(Byte noteType) {
        this.noteType = noteType;
    }

    /**
     * 获取文章Content
     *
     * @return note_content - 文章Content
     */
    public String getNoteContent() {
        return noteContent;
    }

    /**
     * 设置文章Content
     *
     * @param noteContent 文章Content
     */
    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent == null ? null : noteContent.trim();
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
        sb.append(", noteTitle=").append(noteTitle);
        sb.append(", noteAuthorName=").append(noteAuthorName);
        sb.append(", noteDescribe=").append(noteDescribe);
        sb.append(", noteCreateTime=").append(noteCreateTime);
        sb.append(", noteModifiedTime=").append(noteModifiedTime);
        sb.append(", noteWords=").append(noteWords);
        sb.append(", noteReads=").append(noteReads);
        sb.append(", noteShare=").append(noteShare);
        sb.append(", noteLikes=").append(noteLikes);
        sb.append(", notePermission=").append(notePermission);
        sb.append(", noteType=").append(noteType);
        sb.append(", noteContent=").append(noteContent);
        sb.append("]");
        return sb.toString();
    }
}