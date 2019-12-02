package com.tuniu.bi.umsj.base.service;

import com.tuniu.bi.umsj.base.dao.entity.Article;
import com.tuniu.bi.umsj.base.dao.mapper.ArticleMapper;
import com.tuniu.bi.umsj.base.vo.markdown.SaveMarkdownVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author wll
 * @Date 2019/12/2 18:13
 * @Description
 **/
@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public int saveArticle(SaveMarkdownVo saveMarkdownVo) {
        Article article = new Article();
        article.setContent(saveMarkdownVo.getContent());
        article.setCreateTime(new Date());
        return articleMapper.insertSelective(article);
    }

    @Override
    public Article articleDetail(Integer id) {
        return articleMapper.selectByPrimaryKey(id);
    }
}
