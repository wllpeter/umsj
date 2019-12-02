package com.tuniu.bi.umsj.base.service;

import com.tuniu.bi.umsj.base.dao.entity.Article;
import com.tuniu.bi.umsj.base.vo.markdown.SaveMarkdownVo;

/**
 * @Author wll
 * @Date 2019/12/2 18:13
 * @Description
 **/
public interface ArticleService {
    /**
     * 保存文章
     *
     * @param saveMarkdownVo
     * @return
     */
    int saveArticle(SaveMarkdownVo saveMarkdownVo);

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    Article articleDetail(Integer id);
}
