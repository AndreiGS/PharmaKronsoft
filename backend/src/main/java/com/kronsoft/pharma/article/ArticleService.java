package com.kronsoft.pharma.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kronsoft.pharma.article.dto.ArticleListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
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
}
