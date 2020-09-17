import React, {ReactElement, useCallback, useContext, useEffect} from "react";
import BasePage from "../BasePage";
import {withRouter} from "react-router";
import {Button, Col, Row} from "antd";
import {AppContext} from "../../security/AppContext";
import i18next from "i18next";

const DiaryPage = (): ReactElement => {

    const authContext = useContext(AppContext);

    const downloadDiary = useEffect(() => {
        if (authContext.currentUser.diaryId) {
            //todo
        }
    }, [authContext]);

    const handleCreateDiary = async (): Promise<void> => {
        authContext.setLoading(true);
        console.log("Дневник успешно создан");
        authContext.setLoading(false);
    }

    const renderButton = useCallback(() => <Button type="primary" onClick={handleCreateDiary}>{i18next.t('form.diary.create_btn')}</Button>,[])

    const renderContent = useCallback(() => <></>, []);

    return (
        <BasePage>
            <Row justify="center" align="middle" className="row">
                <Col>
                    {!authContext.currentUser.diaryId ? renderButton() : renderContent()}
                </Col>
            </Row>
        </BasePage>
    )
}

export default withRouter(DiaryPage);

