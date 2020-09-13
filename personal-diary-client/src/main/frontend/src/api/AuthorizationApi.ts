import {AxiosResponse} from "axios";
import {http} from "../utils/http";
import {LoginFormData} from "../model/LoginFormData";
import {OperationResult} from "../model/OperationResult";

export function authorization(values: LoginFormData): Promise<AxiosResponse> {
    return http.post('/auth', values);
}

export function sendRecoveryPasswordMail(email: string): Promise<AxiosResponse<OperationResult>> {
    return http.post('/mail-api/sendRecoveryPasswordMail', {userEmail: email});
}