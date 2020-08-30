import {AxiosResponse} from "axios";
import {RegistrationFormData} from "../model/RegistrationFormData";
import {http} from "../utils/http";
import {OperationResult} from "../model/OperationResult";

export function registration(values: RegistrationFormData): Promise<AxiosResponse<OperationResult>> {
    return http.post('/registration-api/registration', values);
}