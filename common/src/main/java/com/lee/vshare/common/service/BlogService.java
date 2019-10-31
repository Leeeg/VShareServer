package com.lee.vshare.common.service;

import com.lee.vshare.common.entity.Notes;

import java.util.List;
import java.util.Map;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-12-20
 * @Time 上午11:40
 */
public interface BlogService {

    Integer addNote(String title, String content,String noteDescribe, Boolean isPrivate, Byte type, Long noteId);//创建文章

    Integer deleteNotesById(Long... ids);//通过文章id删除文章

    Integer updateNote(Long noteId, String title, String content, Boolean isPrivate, Byte type);//修改文章

    List<Notes> getAllNotes();//获取全部文章列表

    List<Notes> getNotesById(Long... ids);//通过文章id获取文章信息

    List<Map<String, Object>> getNotesList();//查询文章列表（不包含内容）
}
