import {AxiosResponse} from "axios";
import {UserFormData} from "../model/UserFormData";
import {http} from "../utils/http";
import {OperationResult} from "../model/OperationResult";

export function getUserInfo(userLogin: string): Promise<AxiosResponse<UserFormData>> {
    return http.get(`/user-api/findByLogin/${userLogin}`)
}

export function updateUserInfo(userData: UserFormData): Promise<AxiosResponse<OperationResult>> {
    return http.put('/user-api/update', userData)
}