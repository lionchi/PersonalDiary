import {Moment} from "moment";

export interface UserFormData {
    login?: string;
    name: string;
    email: string;
    password: string;
    confirmNewPassword: string;
    prefix?: string;
    phone?: string;
    birthday?: Moment | string;
}