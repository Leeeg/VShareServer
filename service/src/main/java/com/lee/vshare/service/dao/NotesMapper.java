package com.lee.vshare.service.dao;

import com.lee.vshare.common.entity.Notes;
import com.lee.vshare.common.util.mapper.MyMapper;

import java.util.List;
import java.util.Map;

public interface NotesMapper extends MyMapper<Notes> {

    Notes selectNotesDesc(Integer limit);

    List<Map<String, Object>> selectNotesWithoutContent();

    Notes selectNotesContent(Long noteId);
}