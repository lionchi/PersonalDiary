import axios from "axios";
import {baseUrl} from "./config";
import promise from 'promise';
import {EConstantValueString} from "../common/EConstantValueString";

export const httpSecurity = axios.create({
    baseURL: baseUrl(),
});

httpSecurity.interceptors.request.use(
    async (config) => {
        const accessToken = localStorage.getItem(EConstantValueString.ACCESS_TOKEN);

        if (accessToken) {
            config.headers.authorization = `Bearer ${accessToken}`;
        }

        return config;
    },
    (error) => {
        return promise.reject(error);
    },
);