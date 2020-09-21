import i18next from "i18next";
import React, {ReactElement} from "react";
import {Button, Checkbox, Popconfirm} from "antd";
import {Directory} from "../../model/Directory";
import {Page} from "../../model/Page";
import {DeleteOutlined, EditOutlined} from '@ant-design/icons';
import {ColumnsType} from "antd/lib/table";
import {renderTag} from "../common/function";

export const getColumns = (ln: string, onClickDelete: (pageId: number) => void, onClickEdit: (pageId: number) => void): ColumnsType<Page> => {
    return [
        {
            title: i18next.t('table.diary.column_summary'),
            dataIndex: 'recordingSummary',
            key: 'recordingSummary',
            align: 'center'
        },
        {
            title: i18next.t('table.diary.column_tag'),
            dataIndex: 'tag',
            key: 'tag',
            align: 'center',
            render: (tag: Directory): ReactElement => (renderTag(tag, ln))
        },
        {
            title: i18next.t('table.diary.column_notification_date'),
            dataIndex: 'notificationDate',
            key: 'notificationDate',
            align: 'center'
        },
        {
            title: i18next.t('table.diary.column_create_date'),
            dataIndex: 'createDate',
            key: 'createDate',
            align: 'center'
        },
        {
            title: i18next.t('table.diary.column_confidential'),
            dataIndex: 'confidential',
            key: 'confidential',
            align: 'center',
            render: (confidential: boolean): ReactElement => (
                <Checkbox defaultChecked={confidential} disabled/>
            )
        },
        {
            title: i18next.t('table.diary.column_action'),
            dataIndex: '',
            key: 'x',
            align: 'center',
            render: (text: unknown, record: Page) => (
                <>
                    <Popconfirm title={i18next.t('table.diary.popconfirm')} onConfirm={() => onClickDelete(record.id)}>
                        <Button type="text" shape="circle" icon={<DeleteOutlined/>}/>
                    </Popconfirm>
                    <Button onClick={() => onClickEdit(record.id)} type="text" shape="circle" icon={<EditOutlined/>}/>
                </>
            )
        }
    ]
}