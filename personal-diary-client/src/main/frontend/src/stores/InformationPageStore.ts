import {action, observable} from "mobx";
import {UserFormData} from "../model/UserFormData";
import {StatisticsData} from "../model/StatisticsData";
import {getStatistics, getUserInfo} from "../api/InformationApi";
import {convertToMoment} from "../pages/common/function";
import {PersonalDiaryUser} from "../model/PersonalDiaryUser";

export class InformationPageStore {
    @observable initFormValues: UserFormData;
    @observable initStatisticsValue: StatisticsData;

    constructor() {
        this.clear();
    }

    @action
    public clear(): void {
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
            dateOfLastEntry: null,
            dateOfNextNotificationAndReminder: null,
        };
    }

    @action
    public async fetchData(currentUser: PersonalDiaryUser): Promise<void> {
        const responseUserInfo = await getUserInfo(currentUser.username);
        this.initFormValues = {
            ...responseUserInfo.data,
            birthday: responseUserInfo.data.birthday ? convertToMoment(responseUserInfo.data.birthday as string) : null
        };
        if (currentUser.diaryId) {
            const responseStatistics = await getStatistics(currentUser.diaryId);
            this.initStatisticsValue = {
                ...responseStatistics.data,
                dateOfNextNotificationAndReminder: responseStatistics.data.dateOfNextNotificationAndReminder
                    ? new Date(responseStatistics.data.dateOfNextNotificationAndReminder).getTime()
                    : null
            }
        }
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