package com.tuniu.bi.umsj.base.controller.api;

import com.tuniu.bi.umsj.base.service.ArticleService;
import com.tuniu.bi.umsj.base.utils.ResponseUtils;
import com.tuniu.bi.umsj.base.vo.Response;
import com.tuniu.bi.umsj.base.vo.markdown.SaveMarkdownVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wll
 * @Date 2019/12/2 17:37
 * @Description
 **/
@Api(tags = "Markdown")
@RestController
@RequestMapping("/mark")
public class MarkdownController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@RequestBody SaveMarkdownVo saveMarkdownVo) {
        System.out.println(saveMarkdownVo.toString());
        return ResponseUtils.success(articleService.saveArticle(saveMarkdownVo));
    }


    @RequestMapping(value = "/articleDetail", method = RequestMethod.GET)
    public Response articleDetail(Integer id) {
        return ResponseUtils.success(articleService.articleDetail(id));
    }
}
