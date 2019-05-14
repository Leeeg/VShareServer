package com.lee.vshare.service.dao;

import com.lee.vshare.common.entity.Notes;
import com.lee.vshare.common.util.mapper.MyMapper;

public interface NotesMapper extends MyMapper<Notes> {

    Notes selectNotesDesc(Integer limit);

}