import React, {ReactElement, useCallback, useContext} from "react";
import {Button, Col, Form, Input, Row, Typography} from "antd";
import {LockOutlined, UserOutlined} from '@ant-design/icons';
import {LoginFormData} from "../../model/LoginFormData";
import "./LoginPage.css"
import BasePage from "../BasePage";
import i18next from "i18next";
import {AppContext} from "../../security/AppContext";
import {authorization} from "../../api/AuthorizationApi";
import {showNotification} from "../../utils/notification";
import {OperationResult} from "../../model/OperationResult";
import {RouteComponentProps, withRouter} from "react-router";

const {Title, Text, Link} = Typography;

const LoginPage = (props: RouteComponentProps): ReactElement => {

    const appContext = useContext(AppContext);

    const onFinish = useCallback(async (data: LoginFormData) => {
        try {
            const response = await authorization(data);

            const authHeaders = response.headers.authorization as string;

            appContext.signIn(authHeaders.replace('Bearer ', ''));

            props.history.push('/diary');
        } catch (e) {
            const operationResult: OperationResult = {
                code: 'code.error.authorization',
                ruText: 'Ошибка авторизации. Проверьте ваши учетные данные и повторите попытку',
                enText: 'Authorisation error. Check your credentials and try again',
                resultType: 'error'
            }
            showNotification(i18next.t('notification.title.authorization'), operationResult);
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