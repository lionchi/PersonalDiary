import {AxiosResponse} from "axios";
import {PersonalDiaryUser} from "../model/PersonalDiaryUser";
import {http} from "../utils/http";

export function accountInformation(): Promise<AxiosResponse<PersonalDiaryUser>> {
    return http.get('/api/account/information')
}