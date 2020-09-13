import React, {ReactElement, useCallback, useContext, useState} from "react";
import {Button, Col, Form, Input, Modal, Row, Typography} from "antd";
import {LockOutlined, UserOutlined} from '@ant-design/icons';
import {LoginFormData} from "../../model/LoginFormData";
import "./LoginPage.css"
import BasePage from "../BasePage";
import i18next from "i18next";
import {AppContext} from "../../security/AppContext";
import {authorization, sendRecoveryPasswordMail} from "../../api/AuthorizationApi";
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

    const [visibleModal, setVisibleModal] = useState(false);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [recoveryPasswordForm] = Form.useForm();

    const showModal = useCallback(() => setVisibleModal(true), [])

    const handleCancel = useCallback(() => {
        setVisibleModal(false);
        recoveryPasswordForm.resetFields();
    }, [recoveryPasswordForm])

    const handleOk = useCallback(async () => {
        try {
            setConfirmLoading(true);
            const values = await recoveryPasswordForm.validateFields();
            const {data} = await sendRecoveryPasswordMail(values.email);
            recoveryPasswordForm.resetFields();
            setConfirmLoading(false);
            setVisibleModal(false)
            showNotification(i18next.t('notification.title.recovery_password'), data);
        } catch (e) {
            setConfirmLoading(false);
        }
    }, [recoveryPasswordForm]);

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
                        <Form.Item className="mrg-bottom-0">
                            <Button type="primary" htmlType="submit"
                                    className="form-btn">{i18next.t('form.login.sign_in')}</Button>
                        </Form.Item>
                        <Form.Item className="mrg-bottom-0">
                            <Text className="form-text-size">{i18next.t('form.login.or')}</Text>
                            <Link className="form-text-size"
                                  href={"/registration"}>{i18next.t('form.login.register_now')}</Link>
                        </Form.Item>
                        <Form.Item>
                            <Link className="form-text-size" onClick={showModal}>{i18next.t('form.login.forgot_password')}</Link>
                        </Form.Item>
                    </Form>
                </Col>
            </Row>
            <Modal title={i18next.t('form.login.modal.title')} centered
                   confirmLoading={confirmLoading}
                   visible={visibleModal}
                   onOk={handleOk}
                   onCancel={handleCancel}>
                <Form form={recoveryPasswordForm} name="email_form" initialValues={{}}>
                    <Form.Item
                        name="email"
                        extra={i18next.t('form.login.modal.email_extra')}
                        rules={[
                            {
                                type: 'email',
                                message: i18next.t('form.login.modal.error.email_error_1')
                            },
                            {
                                required: true,
                                message: i18next.t('form.login.modal.error.email_error_2')
                            }
                        ]}
                    >
                        <Input placeholder="Email" maxLength={255}/>
                    </Form.Item>
                </Form>
            </Modal>
        </BasePage>
    )
}

export default withRouter(LoginPage);