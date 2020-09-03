import React, {ReactElement, useContext} from "react";
import BasePage from "../BasePage";
import {withRouter} from "react-router";
import {Button, Col, Row} from "antd";
import {AppContext} from "../../security/AppContext";

const DiaryPage = (): ReactElement => {

    const authContext = useContext(AppContext);

    const handleTest = async (): Promise<void> => {
        console.log(authContext.currentUser.username);
    }

    return (
        <BasePage>
            <Row justify="center" align="middle" className="row">
                <Col>
                    <Button type="primary" onClick={handleTest}>Получить информацию о пользователе</Button>
                </Col>
            </Row>
        </BasePage>
    )
}

export default withRouter(DiaryPage);

