import {AxiosResponse} from "axios";
import {PersonalDiaryUser} from "../model/PersonalDiaryUser";
import {http} from "../utils/http";
import {OperationResult} from "../model/OperationResult";
import {UserFormData} from "../model/UserFormData";

export function accountInformation(): Promise<AxiosResponse<PersonalDiaryUser>> {
    return http.get('/api/account/information')
}

export function updateUserInfo(userData: UserFormData): Promise<AxiosResponse<OperationResult>> {
    return http.put('/user-api/update', userData)
}

export function deleteUser(userId: number): Promise<AxiosResponse<OperationResult>> {
    return http.delete("/user-api/delete", {params: {userId: userId}});
}