import React, {ReactElement, useCallback, useContext, useEffect, useMemo} from "react";
import BasePage from "../BasePage";
import {RouteComponentProps, withRouter} from "react-router";
import {Button, Col, Row, Table} from "antd";
import {AppContext} from "../../security/AppContext";
import i18next from "i18next";
import {inject, observer} from "mobx-react";
import {DiaryPageStore} from "../../stores/DiaryPageStore";
import {showNotification} from "../../utils/notification";
import {EResultType} from "../../model/EResultType";
import {EConstantValueString} from "../../model/EConstantValueString";
import {getColumns} from "./diaryColumns";
import {Page} from "../../model/Page";
import "./DiaryPage.css"
import {createDiary, deletePage} from "../../api/DiaryApi";
import {SheetPageStore} from "../../stores/SheetPageStore";
import {TablePaginationConfig} from "antd/lib/table";
import {Key, SorterResult, TableCurrentDataSource} from "antd/lib/table/interface";
import {ExpansionColumnType} from "../../model/ExpansionColumnType";
import {createFilterForDiaryTable} from "../../utils/filter";

interface IDiaryPageProps extends RouteComponentProps {
    diaryPageStore?: DiaryPageStore;
    sheetPageStore?: SheetPageStore;
}

const DiaryPage = inject("diaryPageStore", "sheetPageStore")(observer((props: IDiaryPageProps) => {
    const authContext = useContext(AppContext);

    useEffect(() => {
        (async () => {
            if (authContext.currentUser.diaryId) {
                authContext.setLoading(true);
                await props.diaryPageStore.fetchPage(authContext.currentUser.diaryId);
                authContext.setLoading(false);
            }
        })();
    }, []);

    const handleCreateDiary = async (): Promise<void> => {
        authContext.setLoading(true);
        const {data} = await createDiary(authContext.currentUser.id as number);
        if (data.resultType === EResultType.SUCCESS) {
            const diaryId = Number(data.json);
            authContext.currentUser.diaryId = diaryId;
            localStorage.setItem(EConstantValueString.DIARY_ID, String(diaryId));
        }
        authContext.setLoading(false);
        showNotification(i18next.t('notification.title.personal_diary'), data);
    }

    const renderButton = (): ReactElement => {
        return (
            <Row justify="center" align="middle" className="row">
                <Col>
                    <Button type="primary" onClick={handleCreateDiary}>{i18next.t('form.diary.create_btn')}</Button>
                </Col>
            </Row>
        )
    }

    const onClickAdd = useCallback(() => {
        props.history.push("/add/page");
    }, [props.history]);

    const onClickDelete = useCallback(async (pageId: number) => {
        authContext.setLoading(true);
        const {data} = await deletePage(pageId)
        if (data.resultType === EResultType.SUCCESS) {
            props.diaryPageStore.deletePage(pageId);
        }
        showNotification(i18next.t('notification.title.delete_page'), data);
        authContext.setLoading(false);
    }, [authContext, props.diaryPageStore])

    const onClickEdit = useCallback((pageId: number) => {
        props.history.push(`/edit/page/${pageId}`);
    }, [props.history])

    const onChangeSort = useCallback((pagination: TablePaginationConfig,
                                      filters: Record<string, Key[] | null>,
                                      sorter: SorterResult<Page> | SorterResult<Page>[],
                                      extra: TableCurrentDataSource<Page>) => {
        authContext.setLoading(true);
        if (extra.action === 'sort') {
            const currentSorter = sorter as SorterResult<Page>;
            const currentColumn = currentSorter.column as ExpansionColumnType<Page>;
            if (currentSorter.order) {
                const currentDirection = currentSorter.order === 'ascend' ? 'asc' : 'desc';
                props.diaryPageStore.sortPage({direction: currentDirection, nameSort: currentColumn.nameSort});
            } else {
                props.diaryPageStore.sortPage(null);
            }
        } else if (extra.action === 'filter') {
            props.diaryPageStore.filterPage(createFilterForDiaryTable(filters));
        } else if (extra.action === 'paginate') {
            props.diaryPageStore.nextPage(pagination.current, pagination.pageSize);
        }
        authContext.setLoading(false);
    }, [authContext, props.diaryPageStore]);

    const columns = useMemo(() => getColumns(authContext.language, onClickDelete, onClickEdit)
        , [authContext.language, onClickDelete, onClickEdit]);

    const dataSource = useMemo(() => props.diaryPageStore.pages, [props.diaryPageStore.pages]);

    const renderContent = (): ReactElement => {
        return (
            <Row justify="center" align="middle" className="row">
                <Col flex='90%'>
                    <Button onClick={onClickAdd} type="primary" className="margin-bottom-top-16">
                        {i18next.t("table.diary.add_btn")}
                    </Button>
                    <Table rowKey={(record: Page) => record.id}
                           columns={columns}
                           dataSource={dataSource}
                           onChange={onChangeSort}
                           pagination={
                               {
                                   current: props.diaryPageStore.pageCurrentNumber,
                                   total: props.diaryPageStore.pageTotalCount,
                                   pageSize: 8,
                                   pageSizeOptions: ['8', '16', '32', '48', '64'],
                               }
                           }
                           expandable={
                               {
                                   expandedRowRender: (record: Page) => <p>{record.content}</p>,
                                   rowExpandable: (record: Page) => !record.confidential
                               }
                           }
                    />
                </Col>
            </Row>
        )
    }

    return (
        <BasePage>
            {!authContext.currentUser.diaryId ? renderButton() : renderContent()}
        </BasePage>
    )
}))

export default withRouter(DiaryPage);

