import i18next from "i18next";
import React, {ReactElement} from "react";
import {Badge, Button, Checkbox, Popconfirm, Tag, Tooltip, Typography} from "antd";
import {Directory} from "../../model/Directory";
import {Page} from "../../model/Page";
import {DeleteOutlined, EditOutlined} from '@ant-design/icons';
import {compareDates, renderTag} from "../common/function";
import {ExpansionColumnsType} from "../../model/ExpansionColumnType";
import {ETag} from "../../model/ETag";
import DateFilter from "../../components/DateFilter";

const {Text} = Typography;

export const getColumns = (ln: string, onClickDelete: (pageId: number) => void, onClickEdit: (pageId: number) => void): ExpansionColumnsType<Page> => {
    return [
        {
            title: i18next.t('table.diary.column_summary'),
            dataIndex: 'recordingSummary',
            key: 'recordingSummary',
            align: 'center',
            render: (recordingSummary: string, record: Page) => {
                if (record.notificationDate && compareDates(record.notificationDate as string)) {
                    return <Tooltip title={i18next.t('table.diary.tooltip_summary')}>
                        <Badge dot={true}>
                            <Text>
                                {recordingSummary}
                            </Text>
                        </Badge>
                    </Tooltip>
                } else {
                    return <Text>{recordingSummary}</Text>
                }
            }
        },
        {
            title: i18next.t('table.diary.column_tag'),
            dataIndex: 'tag',
            key: 'tag',
            align: 'center',
            sorter: true,
            nameSort: 'SORT_BY_TAG',
            filters: [
                {text: <Tag color='green'>{i18next.t('table.diary.filter.note')}</Tag>, value: ETag.NOTE},
                {text: <Tag color='magenta'>{i18next.t('table.diary.filter.notification')}</Tag>, value: ETag.NOTIFICATION},
                {text: <Tag color='red'>{i18next.t('table.diary.filter.reminder')}</Tag>, value: ETag.REMINDER},
                {text: <Tag color='blue'>{i18next.t('table.diary.filter.bookmark')}</Tag>, value: ETag.BOOKMARK},
            ],
            render: (tag: Directory): ReactElement => (renderTag(tag, ln))
        },
        {
            title: i18next.t('table.diary.column_notification_date'),
            dataIndex: 'notificationDate',
            key: 'notificationDate',
            nameSort: 'SORT_BY_NOTIFICATION_DATE',
            sorter: true,
            filterDropdown: props => <DateFilter {...props}/>,
            filterMultiple: false,
            align: 'center'
        },
        {
            title: i18next.t('table.diary.column_create_date'),
            dataIndex: 'createDate',
            key: 'createDate',
            nameSort: 'SORT_BY_CREATE_DATE',
            defaultSortOrder: 'descend',
            sorter: true,
            filterDropdown: props => <DateFilter {...props}/>,
            filterMultiple: false,
            align: 'center'
        },
        {
            title: i18next.t('table.diary.column_confidential'),
            dataIndex: 'confidential',
            key: 'confidential',
            nameSort: 'SORT_BY_CONFIDENTIAL',
            sorter: true,
            filters: [
                {text: i18next.t('table.diary.filter.confidential_true'), value: true},
                {text: i18next.t('table.diary.filter.confidential_false'), value: false},
            ],
            filterMultiple: false,
            align: 'center',
            render: (confidential: boolean): ReactElement => (
                <Checkbox checked={confidential} disabled/>
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