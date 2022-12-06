export interface PageOf<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
}