import {AxiosResponse} from "axios";
import {UserFormData} from "../model/UserFormData";
import {http} from "../utils/http";

export function getUserInfo(userLogin: string): Promise<AxiosResponse<UserFormData>> {
    return http.get(`/user-api/findByLogin/${userLogin}`)
}