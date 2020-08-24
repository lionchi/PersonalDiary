import {CSSProperties, ReactElement, ReactNode} from "react";
import React from "react";
import {Col, Layout, Typography, Row} from "antd";
import "./BasePage.css"

const {Header, Content, Footer} = Layout;
const {Title} = Typography;

interface IBasePageProps {
    children: ReactNode;
}

const BasePage = (props: IBasePageProps): ReactElement => {
    return (
        <Layout>
            <Header className="header">
                <Row justify="space-between" align="middle" className="row-height-100">
                    <Col>
                        <Title level={4} style={{color: "white"}}>Личный дневник</Title>
                    </Col>
                </Row>
            </Header>
            <Content className="content">
                {props.children}
            </Content>
            <Footer className="footer">
                Личный дневник ©2020 создано Jpixel
            </Footer>
        </Layout>
    )
}

export default BasePage;