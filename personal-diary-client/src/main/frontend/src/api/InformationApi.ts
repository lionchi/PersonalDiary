import {AxiosResponse} from "axios";
import {UserFormData} from "../model/UserFormData";
import {http} from "../utils/http";
import {StatisticsData} from "../model/StatisticsData";

export function getUserInfo(userLogin: string): Promise<AxiosResponse<UserFormData>> {
    return http.get(`/user-api/findByLogin/${userLogin}`)
}

export function getStatistics(diaryId: number): Promise<AxiosResponse<StatisticsData>> {
    return http.get('/diary-api/statistics', {params: {diaryId: diaryId}})
}