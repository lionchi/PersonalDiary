import {AxiosResponse} from "axios";
import {http} from "../utils/http";
import {LoginFormData} from "../model/LoginFormData";

export function authorization(values: LoginFormData): Promise<AxiosResponse> {
    return http.post('/auth', values);
}