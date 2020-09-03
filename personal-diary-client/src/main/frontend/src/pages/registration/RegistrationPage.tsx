import React, {ReactElement, useCallback} from "react";
import BasePage from "../BasePage";
import "./RegistrationPage.css"
import {Col, Form, Input, Row, Typography, Tooltip, Button, DatePicker, Select} from "antd";
import i18next from "i18next";
import {RegistrationFormData} from "../../model/RegistrationFormData";
import {QuestionCircleOutlined} from '@ant-design/icons';
import {useForm} from "antd/lib/form/Form";
import {RouteComponentProps, withRouter} from "react-router";
import {registration} from "../../api/RegistrationApi";
import {showNotification} from "../../utils/notification";
import {Moment} from "moment";

const {Title} = Typography;
const {Option} = Select;

const RegistrationPage = (props: RouteComponentProps): ReactElement => {

    const [form] = useForm();

    const prefixSelector = (
        <Form.Item name="prefix" noStyle>
            <Select style={{width: 70}}>
                <Option value="7">+7</Option>
            </Select>
        </Form.Item>
    );

    const onFinish = useCallback(async (fieldsValue: RegistrationFormData) => {
        let values = fieldsValue;
        if (fieldsValue.birthday) {
            const birthdayAsMoment = fieldsValue.birthday as Moment;
            values = {...fieldsValue, birthday: birthdayAsMoment.format("DD.MM.YYYY")};
        }
        const {data} = await registration(values);
        showNotification(i18next.t('notification.title.registration'), data);
        if (data.resultType !== 'error') {
            setTimeout(() => {
                props.history.push('/login');
            }, 1000)
        }
    }, [props]);

    return (
        <BasePage>
            <Row justify="center" align="middle" className="row">
                <Col flex="500px">
                    <Title level={3} className="center">{i18next.t('form.registration.title')}</Title>
                    <Form form={form} labelCol={{span: 8}} wrapperCol={{span: 24}}
                          name="registration_form"
                          initialValues={{prefix: "7"}}
                          onFinish={onFinish}
                          scrollToFirstError>
                        <Form.Item name="name"
                                   label={i18next.t('form.registration.name')}
                                   rules={[{required: true, message: i18next.t('form.registration.error.name')}]}>
                            <Input maxLength={255}/>
                        </Form.Item>

                        <Form.Item
                            name="email"
                            label="E-mail"
                            rules={[
                                {
                                    type: 'email',
                                    message: i18next.t('form.registration.error.email_error_1')
                                },
                                {
                                    required: true,
                                    message: i18next.t('form.registration.error.email_error_2')
                                }
                            ]}
                        >
                            <Input maxLength={255}/>
                        </Form.Item>

                        <Form.Item
                            name="password"
                            label={i18next.t('form.registration.password')}
                            rules={[{required: true, message: i18next.t('form.registration.error.password')}]}
                            hasFeedback>
                            <Input.Password/>
                        </Form.Item>

                        <Form.Item
                            name="confirmPassword"
                            label={i18next.t('form.registration.confirm_password')}
                            dependencies={['password']}
                            hasFeedback
                            rules={[
                                {
                                    required: true,
                                    message: i18next.t('form.registration.error.confirm_password_error_1')
                                },
                                ({getFieldValue}) => ({
                                    validator(rule, value) {
                                        if (!value || getFieldValue('password') === value) {
                                            return Promise.resolve();
                                        }
                                        return Promise.reject(i18next.t('form.registration.error.confirm_password_error_2'));
                                    },
                                }),
                            ]}
                        >
                            <Input.Password/>
                        </Form.Item>

                        <Form.Item
                            name="login"
                            label={<span>{i18next.t('form.registration.login')} <Tooltip
                                title={i18next.t('form.registration.tooltip_login')}><QuestionCircleOutlined/></Tooltip></span>}
                            rules={[{
                                required: true,
                                message: i18next.t('form.registration.error.login'),
                                whitespace: true
                            }]}>
                            <Input maxLength={25}/>
                        </Form.Item>

                        <Form.Item name="phone" label={i18next.t('form.registration.phone')}>
                            <Input addonBefore={prefixSelector} maxLength={12}/>
                        </Form.Item>

                        <Form.Item name="birthday" label={i18next.t('form.registration.birthday')}>
                            <DatePicker className="width-100" format="DD.MM.YYYY"/>
                        </Form.Item>

                        <Form.Item className="center">
                            <Button type="primary"
                                    htmlType="submit">{i18next.t('form.registration.register_now')}</Button>
                        </Form.Item>
                    </Form>
                </Col>
            </Row>
        </BasePage>
    )
}

export default withRouter(RegistrationPage);