import React, {ReactElement, useCallback, useContext} from "react";
import {Button, Col, Form, Input, Row, Typography} from "antd";
import {LockOutlined, UserOutlined} from '@ant-design/icons';
import {LoginFormData} from "../../model/LoginFormData";
import "./LoginPage.css"
import BasePage from "../BasePage";
import i18next from "i18next";
import {AppContext} from "../../security/AppContext";
import {authorization} from "../../api/AuthorizationApi";
import {showNotification, showNotificationClient} from "../../utils/notification";
import {RouteComponentProps, withRouter} from "react-router";
import has from 'lodash/has';

const {Title, Text, Link} = Typography;

const LoginPage = (props: RouteComponentProps): ReactElement => {

    const appContext = useContext(AppContext);

    const onFinish = useCallback(async (formData: LoginFormData) => {
        try {
            await authorization(formData);
            appContext.signIn();
            props.history.push('/diary');
        } catch (e) {
            if (has(e.response.data, 'code')) {
                showNotification(i18next.t('notification.title.authorization'), e.response.data);
            } else {
                showNotificationClient(i18next.t('notification.title.authorization'), i18next.t('notification.error.authorization'), 'error');
            }
        }
    }, [appContext, props])

    return (
        <BasePage>
            <Row justify="center" align="middle" className="row">
                <Col flex="400px">
                    <Title level={3} className="form-title">{i18next.t('form.login.title')}</Title>
                    <Form name="login_form" initialValues={{}} onFinish={onFinish}>
                        <Form.Item name="username"
                                   rules={[{required: true, message: i18next.t('form.login.error.user_name')}]}>
                            <Input prefix={<UserOutlined className="site-form-item-icon"/>}
                                   placeholder={i18next.t('form.login.user_name')}
                                   maxLength={25}/>
                        </Form.Item>
                        <Form.Item name="password"
                                   rules={[{required: true, message: i18next.t('form.login.error.password')}]}>
                            <Input prefix={<LockOutlined className="site-form-item-icon"/>} type="password"
                                   placeholder={i18next.t('form.login.password')}/>
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" htmlType="submit"
                                    className="form-btn">{i18next.t('form.login.sign_in')}</Button>
                        </Form.Item>
                        <Form.Item>
                            <Text className="form-text-size">{i18next.t('form.login.or')}</Text>
                            <Link className="form-text-size"
                                  href={"/registration"}>{i18next.t('form.login.register_now')}</Link>
                        </Form.Item>
                    </Form>
                </Col>
            </Row>
        </BasePage>
    )
}

export default withRouter(LoginPage);