package com.kronsoft.pharma.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(value="/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> importData(@RequestPart(value="json-file") MultipartFile file) {
        return articleService.importArticles(file);
    }

    @GetMapping(value="/test")
    public ResponseEntity<String> response() {
        return ResponseEntity.ok("Ok");
    }

}
