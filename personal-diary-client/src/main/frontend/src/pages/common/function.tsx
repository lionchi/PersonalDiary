import {Directory} from "../../model/Directory";
import React, {ReactElement} from "react";
import {ETag} from "../../model/ETag";
import {Tag} from "antd";
import moment, {Moment} from "moment";
import {logout} from "../../api/LogoutApi";
import {OperationResult} from "../../model/OperationResult";
import {showNotification} from "../../utils/notification";
import i18next from "i18next";
import {stores} from "../../stores/stores";

export const renderTag = (tag: Directory, ln: string): ReactElement => {
    switch (tag.code) {
        case ETag.NOTE:
            return (
                <Tag color='green'>
                    {ln === 'ru' ? tag.nameRu : tag.nameEn}
                </Tag>
            )
        case ETag.BOOKMARK:
            return (
                <Tag color='blue'>
                    {ln === 'ru' ? tag.nameRu : tag.nameEn}
                </Tag>
            )
        case ETag.NOTIFICATION:
            return (
                <Tag color='magenta'>
                    {ln === 'ru' ? tag.nameRu : tag.nameEn}
                </Tag>
            )
        case ETag.REMINDER:
            return (
                <Tag color='red'>
                    {ln === 'ru' ? tag.nameRu : tag.nameEn}
                </Tag>
            )
    }
}

export const compareDates = (date: string): boolean => {
    const now = new Date();
    return convertToMoment(date).startOf('day').isSame(convertToMoment(now).startOf('day'));
}

export const convertToMoment = (date: string | Date): Moment => {
    return moment(date, 'DD.MM.YYYY');
}

export const logoutSystem = async (): Promise<void> => {
    try {
        await logout();
        stores.diaryPageStore.clear();
        stores.sheetPageStore.clear();
        stores.informationPageStore.clear();
    } catch (e) {
        const operationResult: OperationResult = {
            code: 'code.error.logout',
            ruText: 'Ошибка выхода из системы. Повторите попытку',
            enText: 'Logout error. Try again',
            resultType: 'error'
        }
        showNotification(i18next.t('notification.title.logout'), operationResult);
    }
}