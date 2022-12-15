import { ArticleImportProcess } from "src/app/shared/models/article/article-import-process";

export interface ArticleStateModel {
    loadingImportProcess?: ArticleImportProcess
}

export const defaultArticleState: ArticleStateModel = {
    loadingImportProcess: undefined
};