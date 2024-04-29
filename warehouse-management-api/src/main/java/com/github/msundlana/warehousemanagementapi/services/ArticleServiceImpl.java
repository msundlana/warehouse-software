package com.github.msundlana.warehousemanagementapi.services;

import com.github.msundlana.warehousemanagementapi.exception.ArticleNotFoundException;
import com.github.msundlana.warehousemanagementapi.models.Article;
import com.github.msundlana.warehousemanagementapi.models.ArticleDTO;
import com.github.msundlana.warehousemanagementapi.repositories.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends BaseService<Article,ArticleDTO> implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);


    @Autowired
    private ArticleRepository articleRepository;

    public ArticleServiceImpl() {
        super(Article.class, ArticleDTO.class);
    }


    @Override
    public Page<ArticleDTO> getAllArticles(Pageable pageable) {
        logger.info("Retrieving all articles with pagination: {}", pageable);
        var articles = articleRepository.findAll(pageable);
        logger.info("Retrieved {} articles", articles.getTotalElements());
        return articles.map(this::convertToDto);
    }

    @Override
    public ArticleDTO getArticleById(Long id) {
        logger.info("Retrieving article by ID: {}", id);
        var article = findArticleById(id);
        logger.info("Article found: {}", article);
        return this.convertToDto(article);
    }
    @Override
    public List<ArticleDTO> addArticles(List<ArticleDTO> articleDTOLists){
        logger.info("Adding {} articles", articleDTOLists.size());
        var articles = this.convertToEntity(articleDTOLists);
        var savedArticles = articleRepository.saveAll(articles);
        logger.info("{} articles added successfully", savedArticles.size());
        return this.convertToDto(savedArticles);
    }

    @Override
    public ArticleDTO addArticle(ArticleDTO articleDto) {
        logger.info("Adding new article: {}", articleDto);
        var article = articleRepository.save(this.convertToEntity(articleDto));
        logger.info("Article added: {}", article);
        return this.convertToDto(article);
    }

    @Override
    public ArticleDTO updateArticle(long id, ArticleDTO articleDto) {
        logger.info("Updating article with ID {}: {}", id, articleDto);
        var article = findArticleById(id);
        this.convertToEntity(articleDto, article);
        var savedArticle = articleRepository.save(article);
        logger.info("Article updated: {}", savedArticle);
        return this.convertToDto(savedArticle);
    }

    @Override
    public void deleteArticle(long id) {
        logger.info("Deleting article with ID: {}", id);
        articleRepository.deleteById(id);
        logger.info("Article deleted with ID: {}", id);
    }

    private Article findArticleById(long id){
        return articleRepository.findById(id).orElseThrow(()
                -> new ArticleNotFoundException("Article not found with id: " + id));
    }



}
