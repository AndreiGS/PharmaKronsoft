import { Injectable } from "@angular/core";
import { Store } from "@ngxs/store";
import { ContextMenuRow } from "primeng/table";
import { ArticleImportProcess, ArticleImportProcessStatus } from "src/app/shared/models/article/article-import-process";
import { ArticleService } from "../services/article.service";
import { SetLoadingProcess, UnsetLoadingProcess, FetchLoadingProcess, KeepLoadingProcessUpdated } from "./article-store.actions";
import { ArticleStateModel } from "./article-store.model";
import { ArticleState } from "./article-store.state";

@Injectable()
export class ArticleStoreService {
    constructor(private store : Store
                , private articleService: ArticleService) { }

    public get currentArticleStoreSnapshot(): ArticleStateModel {
        return this.store.selectSnapshot(ArticleState);
    }

    public setLoadingProcess(loadingProcess: ArticleImportProcess) {
        this.store.dispatch(new SetLoadingProcess(loadingProcess));
    }

    public unsetLoadingProcess() {
        this.store.dispatch(new UnsetLoadingProcess());
    }

    public fetchLoadingProcess() {
        this.store.dispatch(new FetchLoadingProcess());
    }

    public keepLoadingProcessUpdated() {
        this.articleService.poolArticleImportProcess(this.currentArticleStoreSnapshot.loadingImportProcess!.processId!).subscribe({
            next: (process: ArticleImportProcess) => {
                console.log(process.status);
                this.store.dispatch(new SetLoadingProcess(process));
                if(process.status === ArticleImportProcessStatus.COMPLETED) {
                    this.articleService.stopArticleImportProcessPolling();
                    this.store.dispatch(new UnsetLoadingProcess());
                }
            },
            error: (error) => {
                this.articleService.stopArticleImportProcessPolling();
                this.store.dispatch(new UnsetLoadingProcess());
            }
        });
    }
}