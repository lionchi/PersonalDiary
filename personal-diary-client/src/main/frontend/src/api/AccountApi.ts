import {AxiosResponse} from "axios";
import {httpSecurity} from "../utils/https";
import {PersonalDiary} from "../model/PersonalDiary";

export function accountInformation(): Promise<AxiosResponse<PersonalDiary>> {
    return httpSecurity.get('/api/account/information')
}