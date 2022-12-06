package com.kronsoft.pharma.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kronsoft.pharma.article.dto.ArticleListDto;
import com.kronsoft.pharma.article.mapper.ArticleMapper;
import com.kronsoft.pharma.util.PageOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    public void importArticles(MultipartFile file) {
        try {
            ArticleListDto articleList = new ObjectMapper().readValue(file.getBytes(), ArticleListDto.class);
            articleRepository.saveAll(articleList.getArticles());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public PageOf<Article> getPageOfItems(int pageNumber, int itemsPerPage, String sortBy, String order) {
        Pageable pageable;
        if(sortBy == null || order == null)
            pageable = PageRequest.of(pageNumber, itemsPerPage);
        else if(order.equals("asc"))
            pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(sortBy).ascending());
        else pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(sortBy).descending());
        Page<Article> articlePage = articleRepository.findAll(pageable);
        return articleMapper.mapDbPageToPageOfArticle(articlePage);
    }
}
