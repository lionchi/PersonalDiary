import React, {ReactElement, ReactNode, useContext} from "react";
import {Col, Layout, Row, Switch, Typography, Divider, Button,} from "antd";
import "./BasePage.css"
import {AppContext} from "../security/AppContext";
import i18next from "i18next";
import {useThemeSwitcher} from "react-css-theme-switcher";
import {BulbTwoTone, LogoutOutlined} from '@ant-design/icons';
import {logout} from "../api/LogoutApi";
import {OperationResult} from "../model/OperationResult";
import {showNotification} from "../utils/notification";
import {RouteComponentProps, withRouter} from "react-router";

const {Header, Content, Footer} = Layout;
const {Title, Text} = Typography;

interface IBasePageProps extends RouteComponentProps{
    children: ReactNode;
}

const BasePage = (props: IBasePageProps): ReactElement => {

    const appContext = useContext(AppContext);
    const {switcher, themes} = useThemeSwitcher();

    const toggleTranslations = (value: boolean): void => {
        appContext.setLanguage(value ? 'en' : 'ru');
    };

    const toggleTheme = (value: boolean): void => {
        appContext.setDarkMode(value, value ? 'dark' : 'light')
        switcher({theme: value ? themes.dark : themes.light});
    }

    const handleLogout = async (): Promise<void> => {
        try {
            await logout();
            appContext.signOut();
            props.history.push('/login');
        } catch (e) {
            const operationResult: OperationResult = {
                code: 'code.error.logout',
                ruText: 'Ошибка выхода из системы. Повторите попытку',
                enText: 'Logout error. Try again',
                resultType: 'error'
            }
            showNotification(i18next.t('notification.title.logout'), operationResult);
        }
    }

    return (
        <Layout>
            <Header className="header">
                <Row justify="space-between" align="middle" className="row-height-100">
                    <Col>
                        <Title level={4} style={{color: "white"}}>{i18next.t('main_title')}</Title>
                    </Col>
                    {appContext.isSignOut ? null : (
                        <Col>
                            <Button type="text" shape="circle" icon={<LogoutOutlined style={{color: 'white'}}/>} onClick={handleLogout}/>
                        </Col>
                    )}
                </Row>
            </Header>
            <Content>
                {props.children}
            </Content>
            <Footer>
                <Row justify="space-between" align="middle" className="row-height-100">
                    <Col>
                        <Text>{i18next.t('footer.copyright')}</Text>
                    </Col>
                    <Col>
                        <Text style={{marginRight: "10px"}}>{i18next.t('footer.translations')}</Text>
                        <Switch checkedChildren="en" unCheckedChildren="ru"
                                checked={appContext.language === 'en'} onChange={toggleTranslations}/>
                        <Divider type="vertical"/>
                        <Text style={{marginRight: "10px"}}>{i18next.t('footer.theme')}</Text>
                        <Switch checkedChildren={<BulbTwoTone twoToneColor="back"/>}
                                unCheckedChildren={<BulbTwoTone twoToneColor="yellow"/>}
                                checked={appContext.isDarkMode} onChange={toggleTheme}/>
                    </Col>
                </Row>
            </Footer>
        </Layout>
    )
}

export default withRouter(BasePage);