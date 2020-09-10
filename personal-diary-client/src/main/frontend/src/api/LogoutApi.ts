import {AxiosResponse} from "axios";
import {http} from "../utils/http";

export function logout(): Promise<AxiosResponse> {
    return http.post('/api/logout', {});
}