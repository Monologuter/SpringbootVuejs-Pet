package com.evan.aiu.controller;

import com.evan.aiu.entity.Animal;
import com.evan.aiu.entity.Comments;
import com.evan.aiu.result.Result;
import com.evan.aiu.result.ResultFactory;
import com.evan.aiu.service.AnimalService;
import com.evan.aiu.service.CommentsService;
import com.evan.aiu.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class CommentsController {
    @Autowired
    CommentsService commentsService;

    @CrossOrigin
    @GetMapping("/api/comments")
    public Result listComments() throws Exception {
        return ResultFactory.buildSuccessResult(commentsService.list());
    }

    @GetMapping("/api/commentsbyaid")
    public Result listCommentsByAid(@RequestParam Integer aid) throws Exception {
        return ResultFactory.buildSuccessResult(commentsService.listbyaid(aid));
    }

    @GetMapping("/api/commentsbyname")
    public Result listCommentsbyName(@RequestParam String adoptname) throws Exception {
        return ResultFactory.buildSuccessResult(commentsService.listByAdoptname(adoptname));
    }

    @GetMapping("/api/deletecomments")
    public Result deleteComments(Integer id) throws Exception {
        commentsService.deleteComments(id);
        return ResultFactory.buildSuccessResult("删除成功！");
    }

    @CrossOrigin
    @PostMapping("/api/comments")
    public Result saveComments(@RequestBody Comments comments) throws Exception {
        commentsService.addOrUpdate(comments);
        return ResultFactory.buildSuccessResult("评论成功！");
    }

}
