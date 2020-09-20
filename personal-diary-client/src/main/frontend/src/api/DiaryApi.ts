import {AxiosResponse} from "axios";
import {OperationResult} from "../model/OperationResult";
import {http} from "../utils/http";

export function createDiaryApi(userId: number): Promise<AxiosResponse<OperationResult>> {
    return http.post("/user-api/diary/create", {}, {params: {userId: userId}});
}