import {RouteComponentProps, withRouter} from "react-router";
import React, {ReactElement, useCallback} from "react";
import BasePage from "../BasePage";
import {Button, Col, Form, Input, Row, Typography} from "antd";
import {RecoveryPasswordData} from "../../model/RecoveryPasswordData";
import i18next from "i18next";
import "./RecoveryPasswordPage.css"

const {Title} = Typography;

interface IRecoveryPasswordPage extends RouteComponentProps {

}

const RecoveryPasswordPage = (props: IRecoveryPasswordPage): ReactElement => {

    const onFinish = useCallback((formData: RecoveryPasswordData) => {

    }, []);

    return (
        <BasePage>
            <Row justify="center" align="middle" className="row">
                <Col flex="400px">
                    <Title level={3} className="form-title">{i18next.t('form.recovery_password.title')}</Title>
                    <Form name="recovery_password_form" initialValues={{}} onFinish={onFinish}>
                        <Form.Item
                            name="newPassword"
                            rules={[{required: true, message: i18next.t('form.recovery_password.error.new_password')}]}
                            hasFeedback>
                            <Input.Password placeholder={i18next.t('form.recovery_password.new_password')}/>
                        </Form.Item>
                        <Form.Item
                            name="confirmNewPassword"
                            dependencies={['newPassword']}
                            hasFeedback
                            rules={[
                                {
                                    required: true,
                                    message: i18next.t('form.recovery_password.error.confirm_new_password_error_1')
                                },
                                ({getFieldValue}) => ({
                                    validator(rule, value) {
                                        if (!value || getFieldValue('newPassword') === value) {
                                            return Promise.resolve();
                                        }
                                        return Promise.reject(i18next.t('form.recovery_password.error.confirm_new_password_error_2'));
                                    },
                                }),
                            ]}
                        >
                            <Input.Password placeholder={i18next.t('form.recovery_password.confirm_new_password')}/>
                        </Form.Item>
                        <Form.Item className="center">
                            <Button type="primary"
                                    htmlType="submit">{i18next.t('form.recovery_password.set_password')}</Button>
                        </Form.Item>
                    </Form>
                </Col>
            </Row>
        </BasePage>
    )
}

export default withRouter(RecoveryPasswordPage)