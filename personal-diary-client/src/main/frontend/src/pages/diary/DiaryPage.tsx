import React, {ReactElement} from "react";
import BasePage from "../BasePage";
import {withRouter} from "react-router";
import {Button, Col, Row} from "antd";
import {accountInformation} from "../../api/AccountApi";

const DiaryPage = (): ReactElement => {

    const handleTest = async (): Promise<void> => {
        const {data} = await accountInformation();
        console.log(data.username);
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

