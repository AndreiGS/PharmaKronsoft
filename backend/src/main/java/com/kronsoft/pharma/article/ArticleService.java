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

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void importJson(MultipartFile file) {
        System.out.println("file");
    }

    public ResponseEntity<String> importArticles(MultipartFile file) {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            ArticleListDto articleList = new ObjectMapper().readValue(file.getBytes(), ArticleListDto.class);
            System.out.println(articleList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("ok");
    }
}
