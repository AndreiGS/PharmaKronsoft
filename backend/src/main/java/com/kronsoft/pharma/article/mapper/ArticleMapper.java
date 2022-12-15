package com.kronsoft.pharma.article.mapper;

import com.kronsoft.pharma.article.Article;
import com.kronsoft.pharma.importProcess.ImportProcess;
import com.kronsoft.pharma.importProcess.dto.ImportProcessResponseDto;
import com.kronsoft.pharma.util.PageOf;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
    private final ModelMapper modelMapper;

    public ArticleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PageOf<Article> mapDbPageToPageOfArticle(Page<Article> dbPage) {
        return modelMapper.map(dbPage, PageOf.class);
    }

    public ImportProcessResponseDto mapProcessToDto(ImportProcess process) {
        return modelMapper.map(process, ImportProcessResponseDto.class);
    }
}
