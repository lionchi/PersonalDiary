import {Directory} from "../../model/Directory";
import React, {ReactElement} from "react";
import {ETag} from "../../model/ETag";
import {Tag} from "antd";
import moment, {Moment} from "moment";

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