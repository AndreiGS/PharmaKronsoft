package com.kronsoft.pharma.article;

import com.kronsoft.pharma.article.mapper.ArticleMapper;
import com.kronsoft.pharma.importProcess.ImportProcess;
import com.kronsoft.pharma.importProcess.ImportProcessRepository;
import com.kronsoft.pharma.importProcess.ImportProcessService;
import com.kronsoft.pharma.importProcess.ProcessStatus;
import com.kronsoft.pharma.util.PageOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ImportProcessService importProcessService;
    private final ImportProcessRepository importProcessRepository;
    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ImportProcessService importProcessService, ImportProcessRepository importProcessRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.importProcessService = importProcessService;
        this.importProcessRepository = importProcessRepository;
        this.articleMapper = articleMapper;
    }

    public ImportProcess importArticles(MultipartFile file) {
        ImportProcess process = new ImportProcess(null, ProcessStatus.IN_PROGRESS, 0, 1000);
        process = importProcessRepository.save(process);
        importProcessService.asyncImportArticles(process);
        return process;
       /* try {
            *//*ArticleListDto articleList = new ObjectMapper().readValue(file.getBytes(), ArticleListDto.class);
            List<Article> savedEntities = articleRepository.saveAll(articleList.getArticles());
            System.out.println(savedEntities);*//*
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
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
