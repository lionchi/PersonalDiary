import {AxiosResponse} from "axios";
import {OperationResult} from "../model/OperationResult";
import {http} from "../utils/http";
import {Tag} from "../model/Tag";
import {Page} from "../model/Page";

export function createDiary(userId: number): Promise<AxiosResponse<OperationResult>> {
    return http.post("/diary-api/create", {}, {params: {userId: userId}});
}

export function createPage(page: Page): Promise<AxiosResponse<OperationResult>> {
    return http.post("/diary-api/page/create", page);
}

export function getPage(pageId: number): Promise<AxiosResponse<Page>> {
    return http.get(`/diary-api/page/get/${pageId}`);
}

export function deletePage(pageId: number): Promise<AxiosResponse<OperationResult>> {
    return http.delete(`/diary-api/page/delete/${pageId}`);
}

export function downloadTags(): Promise<AxiosResponse<Tag[]>> {
    return http.get("/diary-api/download/tags")
}