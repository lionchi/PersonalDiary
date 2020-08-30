import {Moment} from "moment";

export interface RegistrationFormData {
    name: string;
    login: string;
    email: string;
    password: string;
    confirmPassword: string;
    prefix?: string;
    phone?: string;
    birthday?: Moment | string;
}