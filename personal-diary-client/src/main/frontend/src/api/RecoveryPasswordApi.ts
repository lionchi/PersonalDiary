import {AxiosResponse} from "axios";
import {OperationResult} from "../model/OperationResult";
import {http} from "../utils/http";

export function recoveryPassword(newPassword: string, token: string): Promise<AxiosResponse<OperationResult>> {
    return http.put('/user-api/recoveryPassword', {newPassword: newPassword, token: token});
}

export function sendRecoveryPasswordMail(email: string, ln: string): Promise<AxiosResponse<OperationResult>> {
    return http.post('/mail-api/sendRecoveryPasswordMail', {userEmail: email}, {params: {ln: ln}});
}