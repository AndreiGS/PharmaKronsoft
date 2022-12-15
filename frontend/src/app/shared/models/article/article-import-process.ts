export enum ArticleImportProcessStatus {
    IN_PROCESS = "IN_PROGRESS",
    COMPLETED = "COMPLETED",
    FAILED = "FAILED"
}

export interface ArticleImportProcess {
    processId: string;
    username: string;
    status: ArticleImportProcessStatus;
    processedRecords: number;
    totalRecords: number;
}
