package com.lee.vshare.rest.controller;

import com.lee.vshare.common.TaskExecutor.AsyncTask;
import com.lee.vshare.common.dto.Response;
import com.lee.vshare.common.entity.Notes;
import com.lee.vshare.common.service.BlogService;
import com.lee.vshare.common.util.DataUtil;
import com.lee.vshare.common.util.MarkDownUtil;
import com.lee.vshare.common.util.ResponseEnum;
import com.lee.vshare.common.util.response.ResponseUtil;
import com.lee.vshare.rest.controller.presenter.NotesPresenter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-12-19
 * @Time 下午5:05
 */

//@RestController
//@RequestMapping("/api/notes")
//@Api(value = "/notes", tags = "Notes", description = "文章操作")
public class BlogController extends BaseController implements NotesPresenter {

    Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    BlogService notesService;

    @Autowired
    AsyncTask asyncTask;

    @ApiOperation("创建文章")
    @PostMapping("/addNote")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "文章标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "文章内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "isPrivate", value = "是否私有", required = true, dataType = "Boolean"),
            @ApiImplicitParam(name = "type", value = "文章分类", required = true, dataType = "Byte", example = "0")
    })
    @Override
    public Response addNote(String title, String content, Boolean isPrivate, Byte type) {
        logger.info("addNote : "
                + " \ntitle = " + title
                + " \ncontent = " + content
                + " \nisPrivate = " + isPrivate
                + " \ntype = " + type
        );

        if (DataUtil.isEmpty(title, content) || null == isPrivate || type > 2) {
            return ResponseUtil.error(ResponseEnum.PARAMETER_INVALID);
        }

        int result = notesService.addNote(title, content, isPrivate, type);
        if (0 == result) {
            return ResponseUtil.error(ResponseEnum.INSERT_FAILED);
        }
        return ResponseUtil.success(null);
    }

    @ApiOperation("通过文章id删除文章")
    @ApiImplicitParam(name = "ids", value = "文章Id", allowMultiple = true, required = true, dataType = "Long")
    @DeleteMapping("/deleteNotesById")
    @Override
    public Response deleteNotesById(Long... ids) {
        logger.info("deleteNotesById : " + Arrays.toString(ids));
        int result = notesService.deleteNotesById(ids);
        if (0 == result) {
            return ResponseUtil.error(ResponseEnum.PARAMETER_INVALID);
        }
        return ResponseUtil.success(null);
    }

    @ApiOperation("修改文章")
    @PutMapping("/updateNote")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "Long", example = "0"),
            @ApiImplicitParam(name = "title", value = "文章标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "文章内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "isPrivate", value = "是否私有", required = true, dataType = "Boolean"),
            @ApiImplicitParam(name = "type", value = "文章分类", required = true, dataType = "Byte", example = "0")
    })
    @Override
    public Response updateNote(Long id, String title, String content, Boolean isPrivate, Byte type) {
        logger.info("addNote : "
                + " \nid = " + id
                + " \ntitle = " + title
                + " \ncontent = " + content
                + " \nisPrivate = " + isPrivate
                + " \ntype = " + type
        );

        if (DataUtil.isEmpty(title, content) || null == isPrivate || type > 2) {
            return ResponseUtil.error(ResponseEnum.PARAMETER_INVALID);
        }

        int result = notesService.updateNote(id, title, content, isPrivate, type);
        if (0 == result) {
            return ResponseUtil.error(ResponseEnum.DATA_IS_NULL);
        }
        return ResponseUtil.success("update " + result);
    }

    @ApiOperation("获取全部文章列表")
    @GetMapping("/getAllNotes")
    @Override
    public Response getAllNotes() {
        logger.info("getAllNotes  --- ");
        return ResponseUtil.success(notesService.getAllNotes());
    }

    @ApiOperation("跟根据文章id获取文章信息")
    @ApiImplicitParam(name = "ids", value = "文章Id", allowMultiple = true, required = true, dataType = "Long")
    @GetMapping("/getNotesById")
    @Override
    public Response getNotesById(@RequestParam Long... ids) {
        logger.info("getNotesById : " + Arrays.toString(ids));
        try {
            asyncTask.doTask1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Notes> notesList = notesService.getNotesById(ids);
        if (null == notesList || notesList.size() == 0) {
            return null;
        }
        IntStream.range(0, notesList.size()).forEach(i -> {
            String content = notesList.get(i).getNoteContent();
            if (null != content && !content.isEmpty()) {
                notesList.get(i).setNoteContent(MarkDownUtil.markDown(content));
            }
        });
        return ResponseUtil.success(notesList);
    }

}
