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

interface IDiaryPageProps extends RouteComponentProps {
    diaryPageStore?: DiaryPageStore;
    sheetPageStore?: SheetPageStore;
}

const DiaryPage = inject("diaryPageStore", "sheetPageStore")(observer((props: IDiaryPageProps) => {
    const authContext = useContext(AppContext);

    const downloadDiary = useEffect(() => {
        if (authContext.currentUser.diaryId) {
            //todo
        }
    }, [authContext]);

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
        authContext.setLoading(true);
        props.sheetPageStore.fillTags();
        props.sheetPageStore.setInitValuesEmpty();
        authContext.setLoading(false);
        props.history.push("/add/page");
    }, [authContext, props.sheetPageStore, props.history]);

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
        authContext.setLoading(true);
        props.sheetPageStore.fillTags();
        props.sheetPageStore.setInitValuesByPageId(pageId);
        authContext.setLoading(false);
        props.history.push(`/edit/page/${pageId}`);
    }, [authContext, props.sheetPageStore, props.history])

    const columns = useMemo(() => getColumns(authContext.language, onClickDelete, onClickEdit)
        , [authContext.language, onClickDelete, onClickEdit]);

    const renderContent = (): ReactElement => {
        return (
            <Row justify="center" align="middle" className="row">
                <Col flex='90%'>
                    <Button onClick={onClickAdd} type="primary" className="margin-bottom-16">
                        {i18next.t("table.diary.add_btn")}
                    </Button>
                    <Table rowKey={(record: Page) => record.id}
                           columns={columns}
                           dataSource={props.diaryPageStore.pages}
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

