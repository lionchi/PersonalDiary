import React, {ReactElement, ReactNode, useContext} from "react";
import {Button, Col, Divider, Layout, Row, Spin, Switch, Tooltip, Typography,} from "antd";
import "./BasePage.css"
import {AppContext} from "../security/AppContext";
import i18next from "i18next";
import {useThemeSwitcher} from "react-css-theme-switcher";
import {ArrowLeftOutlined, BulbTwoTone, LogoutOutlined} from '@ant-design/icons';
import {RouteComponentProps, withRouter} from "react-router";
import {all} from "../model/ERole";
import CustomAuthorizedSection from "../components/CustomAuthorizedSection";
import {logoutSystem} from "./common/function";

const {Header, Content, Footer} = Layout;
const {Title, Text} = Typography;

interface IBasePageProps extends RouteComponentProps {
    children: ReactNode;
    showBackButton?: boolean;
}

const BasePage = (props: IBasePageProps): ReactElement => {

    const appContext = useContext(AppContext);
    const {switcher, themes, status} = useThemeSwitcher();

    if (status === 'loading') {
        return <Spin className='css-loading-spin' size='large' spinning={true}/>;
    }

    const toggleTranslations = (value: boolean): void => {
        appContext.setLanguage(value ? 'en' : 'ru');
    };

    const toggleTheme = (value: boolean): void => {
        appContext.setDarkMode(value, value ? 'dark' : 'light')
        switcher({theme: value ? themes.dark : themes.light});
    }

    const handleBack = (): void => {
        props.history.goBack();
    }

    const handleLogout = async (): Promise<void> => {
        await logoutSystem();
        appContext.signOut();
        props.history.push('/login');
    }

    const handleClickLink = (): void => {
        props.history.push('/information');
    }

    return (
        <Layout>
            <Header className="header">
                <Row justify="space-between" align="middle" className="row-height-100">
                    <Col>
                        <Title level={4} style={{color: "white"}}>
                            {props.showBackButton && (
                                <Tooltip title={i18next.t('header.tooltip_back')}>
                                    <Button type="text" shape="circle"
                                            icon={<ArrowLeftOutlined style={{color: 'white'}}/>} onClick={handleBack}/>
                                </Tooltip>
                            )}
                            {i18next.t('header.main_title')}
                        </Title>
                    </Col>
                    <CustomAuthorizedSection requires={all}>
                        <Col>
                            <Button type='text' className='login-link' onClick={handleClickLink}>{appContext.currentUser.username}</Button>
                            <Button type="text" shape="circle" icon={<LogoutOutlined style={{color: 'white'}}/>}
                                    onClick={handleLogout}/>
                        </Col>
                    </CustomAuthorizedSection>
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