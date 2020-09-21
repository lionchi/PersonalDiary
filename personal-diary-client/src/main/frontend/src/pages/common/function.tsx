import {Directory} from "../../model/Directory";
import React, {ReactElement} from "react";
import {ETag} from "../../model/ETag";
import {Tag} from "antd";

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