import React, {ReactElement, useContext, useEffect} from "react";
import BasePage from "../BasePage";
import {RouteComponentProps, withRouter} from "react-router";
import {Button, Col, Row} from "antd";
import {AppContext} from "../../security/AppContext";
import i18next from "i18next";
import {inject, observer} from "mobx-react";
import {DiaryPageStore} from "../../stores/DiaryPageStore";
import {showNotification} from "../../utils/notification";
import {EResultType} from "../../model/EResultType";
import {EConstantValueString} from "../../model/EConstantValueString";

interface IDiaryPageProps extends RouteComponentProps {
    diaryPageStore?: DiaryPageStore;
}

const DiaryPage = inject("diaryPageStore")(observer((props: IDiaryPageProps) => {
    const authContext = useContext(AppContext);

    const downloadDiary = useEffect(() => {
        if (authContext.currentUser.diaryId) {
            //todo
        }
    }, [authContext]);

    const handleCreateDiary = async (): Promise<void> => {
        const {diaryPageStore} = props;
        authContext.setLoading(true);
        const operationResult = await diaryPageStore.createDiary(authContext.currentUser.id as number);
        if (operationResult.resultType === EResultType.SUCCESS) {
            const diaryId = Number(operationResult.json);
            authContext.currentUser.diaryId = diaryId;
            localStorage.setItem(EConstantValueString.DIARY_ID, String(diaryId));
        }
        authContext.setLoading(false);
        showNotification(i18next.t('notification.title.personal_diary'), operationResult);
    }

    const renderButton = (): ReactElement => {
        return (
            <Button type="primary" onClick={handleCreateDiary}>{i18next.t('form.diary.create_btn')}</Button>
        )
    }

    const renderContent = (): ReactElement => {
        return (<></>)
    }

    return (
        <BasePage>
            <Row justify="center" align="middle" className="row">
                <Col>
                    {!authContext.currentUser.diaryId ? renderButton() : renderContent()}
                </Col>
            </Row>
        </BasePage>
    )
}))

export default withRouter(DiaryPage);

