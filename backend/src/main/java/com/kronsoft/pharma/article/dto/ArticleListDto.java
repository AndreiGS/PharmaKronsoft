package com.kronsoft.pharma.article.dto;

import com.kronsoft.pharma.article.Article;

import java.util.ArrayList;

public class ArticleListDto {

    ArrayList<Article> articles;

    public ArticleListDto(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public ArticleListDto() {
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}
