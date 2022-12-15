import { HttpErrorResponse } from "@angular/common/http";
import { ArticleImportProcess } from "src/app/shared/models/article/article-import-process";

export class SetLoadingProcess {
    static readonly type = '[ArticleStore] Loading Process stored.';
    constructor(public loadingProcess: ArticleImportProcess) { }
}

export class UnsetLoadingProcess {
    static readonly type = '[ArticleStore] Loading Process deleted.';
    constructor() { }
}

export class FetchLoadingProcess {
    static readonly type = '[ArticleStore] Fetching loading process.';
    constructor() { }
}

export class FetchLoadingProcessCompleted {
    static readonly type = '[ArticleStore] Fetching loading process completed.';
    constructor(public loadingProcess: ArticleImportProcess | null) { }
}

export class FetchLoadingProcessFailed {
    static readonly type = '[ArticleStore] Fetching loading process failed.';
    constructor(public error: HttpErrorResponse) { }
}

export class KeepLoadingProcessUpdated {
    static readonly type = '[ArticleStore] Pooling for loading process updated.';
    constructor(public loadingProcessId: string) { }
}

export class KeepLoadingProcessUpdatedCompleted {
    static readonly type = '[ArticleStore] Pooling completed.';
    constructor() { }
}

export class KeepLoadingProcessUpdatedFailed {
    static readonly type = '[ArticleStore] Pooling failed.';
    constructor(public error: HttpErrorResponse) { }
}