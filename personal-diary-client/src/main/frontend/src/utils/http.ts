import axios from "axios";
import {baseUrl} from "./config";

export const http = axios.create({
    baseURL: baseUrl()
});