import { Injectable } from "@angular/core";
import { Action, Selector, State, StateContext } from "@ngxs/store";
import { catchError, tap } from "rxjs";
import { ArticleImportProcess, ArticleImportProcessStatus } from "src/app/shared/models/article/article-import-process";
import { ArticleService } from "../services/article.service";
import { FetchLoadingProcess, FetchLoadingProcessCompleted, FetchLoadingProcessFailed, KeepLoadingProcessUpdated, KeepLoadingProcessUpdatedCompleted, KeepLoadingProcessUpdatedFailed, SetLoadingProcess, UnsetLoadingProcess } from "./article-store.actions";
import { ArticleStateModel, defaultArticleState } from "./article-store.model";
import { ArticleStoreService } from "./article-store.service";


@State<ArticleStateModel>({
    name: 'article',
    defaults: defaultArticleState
})
@Injectable()
export class ArticleState {
    constructor(public articleService: ArticleService
                , public articleStoreService: ArticleStoreService) { }

    @Selector()
    static loadingProcess(state: ArticleStateModel) {
        return state.loadingImportProcess;
    }

    @Action(SetLoadingProcess)
    public setLoadingProcess(ctx : StateContext<ArticleStateModel>, action: SetLoadingProcess) {
        ctx.setState({
            ...ctx.getState(),
            loadingImportProcess: action.loadingProcess
        });
    }

    @Action(UnsetLoadingProcess)
    public unsetLoadingProcess(ctx : StateContext<ArticleStateModel>, action: UnsetLoadingProcess) {
        ctx.setState({
            ...ctx.getState(),
            loadingImportProcess: undefined
        });
    }

    @Action(FetchLoadingProcess)
    public fetchLoadingProcess(ctx : StateContext<ArticleStateModel>, action: FetchLoadingProcess) {
        this.articleService.fetchLoadingImportProcess().pipe(
            tap((response: ArticleImportProcess | null) => {
                ctx.dispatch(new FetchLoadingProcessCompleted(response));
            }),
            catchError((error) => {
                return ctx.dispatch(new FetchLoadingProcessFailed(error));
              })
        );
    }
    
    @Action(FetchLoadingProcessCompleted)
    public fetchLoadingProcessCompleted(ctx : StateContext<ArticleStateModel>, action: FetchLoadingProcessCompleted) {
        ctx.setState({
            ...ctx.getState(),
            loadingImportProcess: action.loadingProcess ?? undefined
        });
    }

    @Action(FetchLoadingProcessFailed)
    public fetchLoadingProcessFailed(ctx : StateContext<ArticleStateModel>, action: FetchLoadingProcessFailed) {
        ctx.setState({
            ...ctx.getState(),
            loadingImportProcess: undefined
        });
    }

}