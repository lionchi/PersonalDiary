import {AxiosResponse} from "axios";
import {httpSecurity} from "../utils/https";
import {PersonalDiaryUser} from "../model/PersonalDiaryUser";

export function accountInformation(): Promise<AxiosResponse<PersonalDiaryUser>> {
    return httpSecurity.get('/api/account/information')
}