import {AxiosResponse} from "axios";
import {httpSecurity} from "../utils/https";

export function logout(): Promise<AxiosResponse> {
    return httpSecurity.post('/logout', {});
}