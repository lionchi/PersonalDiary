import React, {ReactElement, ReactNode, useContext} from "react";
import {Col, Layout, Row, Switch, Typography,} from "antd";
import "./BasePage.css"
import {AppContext} from "../security/AppContext";
import i18next from "i18next";

const {Header, Content, Footer} = Layout;
const {Title, Text} = Typography;

interface IBasePageProps {
    children: ReactNode;
}

const BasePage = (props: IBasePageProps): ReactElement => {

    const appContext = useContext(AppContext);

    const handleChange = (value: boolean): void => {
        appContext.setLanguage(value ? 'en' : 'ru');
    };

    return (
        <Layout>
            <Header className="header">
                <Row justify="space-between" align="middle" className="row-height-100">
                    <Col>
                        <Title level={4} style={{color: "white"}}>{i18next.t('main_title')}</Title>
                    </Col>
                </Row>
            </Header>
            <Content className="content">
                {props.children}
            </Content>
            <Footer>
                <Row justify="space-between" align="middle" className="row-height-100">
                    <Col>
                        <Text>{i18next.t('footer')}</Text>
                    </Col>
                    <Col>
                        <Switch checkedChildren="en" unCheckedChildren="en"
                                checked={appContext.language === 'en'} onChange={handleChange}/>
                    </Col>
                </Row>
            </Footer>
        </Layout>
    )
}

export default BasePage;