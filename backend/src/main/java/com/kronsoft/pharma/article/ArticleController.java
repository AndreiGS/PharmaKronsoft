package com.kronsoft.pharma.article;

import com.kronsoft.pharma.importProcess.ImportProcess;
import com.kronsoft.pharma.util.PageOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(value="/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ImportProcess importData(@RequestPart(value="file") MultipartFile file) {
        return articleService.importArticles(file);
    }

    @GetMapping(value="/all")
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping(value="/page")
    public PageOf<Article> getPageOfArticles(@RequestParam(value="page") int pageNumber
                                            , @RequestParam(value="items") int itemsPerPage
                                            , @RequestParam(value="sortBy") String sortBy
                                            , @RequestParam(value="order") String order) {
        return articleService.getPageOfItems(pageNumber, itemsPerPage, sortBy, order);
    }



}
