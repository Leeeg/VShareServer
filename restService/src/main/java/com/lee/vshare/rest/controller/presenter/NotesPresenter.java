package com.lee.vshare.rest.controller.presenter;

import com.lee.vshare.common.dto.Response;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-12-19
 * @Time 下午5:45
 */
public interface NotesPresenter {

    Response addNote(String title, String content, Boolean isPrivate, Byte type);//创建文章

    Response deleteNotesById(Long... ids);//通过文章id删除文章

    Response updateNote(Long id, String title, String content, Boolean isPrivate, Byte type);//修改文章

    Response getAllNotes();//获取全部文章列表

    Response getNotesById(Long... ids);//通过文章id获取文章信息

}
