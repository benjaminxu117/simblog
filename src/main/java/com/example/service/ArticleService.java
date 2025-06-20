package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Article;

public interface ArticleService extends IService<Article> {
    
    /**
     * 分页获取文章列表
     */
    IPage<Article> getArticlePage(Page<Article> page, String status);
    
    /**
     * 获取文章详情
     */
    Article getArticleDetail(Long id);
    
    /**
     * 创建文章
     */
    boolean createArticle(Article article);
    
    /**
     * 更新文章
     */
    boolean updateArticle(Article article);
    
    /**
     * 删除文章
     */
    boolean deleteArticle(Long id);
    
    /**
     * 增加文章浏览量
     */
    void incrementViewCount(Long id);
} 