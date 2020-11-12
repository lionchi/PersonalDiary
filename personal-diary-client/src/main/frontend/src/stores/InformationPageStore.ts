import {action, observable} from "mobx";
import {UserFormData} from "../model/UserFormData";
import {StatisticsData} from "../model/StatisticsData";
import {getUserInfo} from "../api/InformationApi";
import {convertToMoment} from "../pages/common/function";

export class InformationPageStore {
    @observable initFormValues: UserFormData;
    @observable initStatisticsValue: StatisticsData;

    constructor() {
        this.initFormValues = {
            name: null,
            email: null,
            birthday: null,
            password: null,
            confirmNewPassword: null,
            prefix: '7',
            phone: null
        };
        this.initStatisticsValue = {
            quantityPage: 0,
            quantityConfPage: 0,
            quantityNonConfPage: 0,
            quantityNotificationPage: 0,
            quantityBookmarkPage: 0,
            quantityNotePage: 0,
            quantityRemainderPage: 0,
            rate: 0
        };
    }

    @action
    public async fetchData(currentUserLogin: string): Promise<void> {
        const responseUserInfo = await getUserInfo(currentUserLogin);
        this.initFormValues = {
            ...responseUserInfo.data,
            birthday: responseUserInfo.data.birthday ? convertToMoment(responseUserInfo.data.birthday as string) : null
        };
    }

    @action
    public updateInitFormValues(formData: UserFormData): void {
        this.initFormValues = {
            name: formData.name,
            email: formData.email,
            birthday: formData.birthday ? convertToMoment(formData.birthday as string) : null,
            password: null,
            confirmNewPassword: null,
            prefix: formData.prefix ? formData.prefix : '7',
            phone: formData.phone
        }
    }
}