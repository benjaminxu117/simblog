package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Article;
import com.example.mapper.ArticleMapper;
import com.example.service.ArticleService;
import com.example.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private AsyncService asyncService;
    
    @Override
    public IPage<Article> getArticlePage(Page<Article> page, String status) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByDesc("create_time");
        return this.page(page, queryWrapper);
    }
    
    @Override
    public Article getArticleDetail(Long id) {
        return this.getById(id);
    }
    
    @Override
    public boolean createArticle(Article article) {
        article.setViewCount(0);
        article.setLikeCount(0);
        article.setCommentCount(0);
        boolean result = this.save(article);
        
        if (result) {
            // 异步生成文章摘要
            asyncService.generateArticleSummaryAsync(article.getId());
        }
        
        return result;
    }
    
    @Override
    public boolean updateArticle(Article article) {
        return this.updateById(article);
    }
    
    @Override
    public boolean deleteArticle(Long id) {
        return this.removeById(id);
    }
    
    @Override
    public void incrementViewCount(Long id) {
        // 使用Redis缓存浏览量，避免频繁更新数据库
        String key = "article:view:" + id;
        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, 1, TimeUnit.HOURS);
        
        // 异步更新统计数据
        asyncService.updateStatisticsAsync(id, "view");
    }
} 