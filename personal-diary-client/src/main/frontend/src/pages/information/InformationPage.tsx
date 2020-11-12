import "./InformationPage.css"
import {RouteComponentProps, withRouter} from "react-router";
import {InformationPageStore} from "../../stores/InformationPageStore";
import {inject, observer} from "mobx-react";
import React, {ReactElement, useContext, useEffect, useMemo} from "react";
import BasePage from "../BasePage";
import {Button, Card, Col, DatePicker, Form, Input, Popconfirm, Rate, Row, Select, Statistic, Typography} from "antd";
import i18next from "i18next";
import {useForm} from "antd/lib/form/Form";
import {UserFormData} from "../../model/UserFormData";
import {AppContext} from "../../security/AppContext";
import {Moment} from "moment";
import {updateUserInfo} from "../../api/InformationApi";
import {showNotification} from "../../utils/notification";
import {EResultType} from "../../model/EResultType";

const {Title} = Typography;
const {Option} = Select;

interface InformationPageProps extends RouteComponentProps {
    informationPageStore?: InformationPageStore;
}

const InformationPage = inject('informationPageStore')(observer((props: InformationPageProps): ReactElement => {

    const [userForm] = useForm();

    const appContext = useContext(AppContext);

    useEffect(() => {
        appContext.setLoading(true);
        props.informationPageStore.fetchData(appContext.currentUser.username);
        appContext.setLoading(false);
    }, []);

    useMemo(() => {
        const {initFormValues} = props.informationPageStore;
        userForm.setFields([
            {name: 'name', value: initFormValues.name},
            {name: 'email', value: initFormValues.email},
            {name: 'prefix', value: initFormValues.prefix ? initFormValues.prefix : '7'},
            {name: 'phone', value: initFormValues.phone},
            {name: 'birthday', value: initFormValues.birthday},
        ]);
    }, [props.informationPageStore.initFormValues]);

    const prefixSelector = (
        <Form.Item name="prefix" noStyle>
            <Select style={{width: 70}}>
                <Option value="7">+7</Option>
            </Select>
        </Form.Item>
    );

    const onFinish = async (formData: UserFormData) => {
        appContext.setLoading(true);
        if (formData.birthday) {
            const birthdayAsMoment = formData.birthday as Moment;
            formData = {...formData, birthday: birthdayAsMoment.format("DD.MM.YYYY")};
        }
        const {data} = await updateUserInfo({
            ...formData,
            login: appContext.currentUser.username,
        });
        showNotification(i18next.t('notification.title.edit_page'), data);
        if (data.resultType !== EResultType.ERROR) {
            props.informationPageStore.updateInitFormValues(formData);
        }
        appContext.setLoading(false);
    }

    const onClickDelete = async () => {

    }

    return (
        <BasePage showBackButton>
            <Row justify="space-between" className='row'>
                <Col className='col' flex='50%'>
                    <Title level={3} className="center">{i18next.t('form.information.title_information_user')}</Title>
                    <Form form={userForm} labelCol={{span: 6}} wrapperCol={{span: 12}}
                          name="user_form"
                          initialValues={{prefix: "7"}}
                          onFinish={onFinish}
                          scrollToFirstError>
                        <Form.Item name="name"
                                   label={i18next.t('form.information.name')}
                                   rules={[{required: true, message: i18next.t('form.information.error.name')}]}>
                            <Input maxLength={255}/>
                        </Form.Item>

                        <Form.Item
                            name="email"
                            label="E-mail"
                            rules={[
                                {
                                    type: 'email',
                                    message: i18next.t('form.information.error.email_error_1')
                                },
                                {
                                    required: true,
                                    message: i18next.t('form.information.error.email_error_2')
                                }
                            ]}
                        >
                            <Input maxLength={255}/>
                        </Form.Item>

                        <Form.Item
                            name="password"
                            label={i18next.t('form.information.new_password')}
                            hasFeedback>
                            <Input.Password/>
                        </Form.Item>

                        <Form.Item
                            name="confirmNewPassword"
                            label={i18next.t('form.information.confirm_new_password')}
                            dependencies={['password']}
                            hasFeedback
                            rules={[
                                ({getFieldValue}) => ({
                                    validator(rule, value) {
                                        if (!value || getFieldValue('password') === value) {
                                            return Promise.resolve();
                                        }
                                        return Promise.reject(i18next.t('form.information.error.confirm_new_password_error'));
                                    },
                                }),
                            ]}
                        >
                            <Input.Password/>
                        </Form.Item>

                        <Form.Item name="phone" label={i18next.t('form.information.phone')}>
                            <Input addonBefore={prefixSelector} maxLength={12}/>
                        </Form.Item>

                        <Form.Item name="birthday" label={i18next.t('form.information.birthday')}>
                            <DatePicker className="width-100" format="DD.MM.YYYY"/>
                        </Form.Item>

                        <Form.Item wrapperCol={{offset: 0}}>
                            <Button className='form-btn' style={{width: '50%'}} type="primary"
                                    htmlType="submit">{i18next.t('form.information.change_info')}</Button>
                        </Form.Item>
                        <Form.Item wrapperCol={{offset: 0}}>
                            <Popconfirm title={i18next.t('form.information.popconfirm')}
                                        onConfirm={onClickDelete}>
                                <Button className='form-btn' danger type="primary"
                                        htmlType="submit">{i18next.t('form.information.delete')}</Button>
                            </Popconfirm>
                        </Form.Item>
                    </Form>
                </Col>
                <Col className='col' flex='50%'>
                    <Title level={3} className="center">{i18next.t('form.information.title_static')}</Title>
                    <Card className='mgb-15' title={i18next.t('form.information.title_quantity_page')}>
                        <Statistic value={props.informationPageStore.initStatisticsValue.quantityPage}/>
                    </Card>
                    <div className='div-flex-row'>
                        <Card title={i18next.t('form.information.title_conf_page')}>
                            <Statistic value={props.informationPageStore.initStatisticsValue.quantityConfPage}/>
                        </Card>
                        <Card title={i18next.t('form.information.title_non_conf_page')}>
                            <Statistic value={props.informationPageStore.initStatisticsValue.quantityNonConfPage}/>
                        </Card>
                    </div>
                    <div className='div-flex-row'>
                        <Card title={i18next.t('form.information.title_notification')}>
                            <Statistic value={props.informationPageStore.initStatisticsValue.quantityNotificationPage}/>
                        </Card>
                        <Card title={i18next.t('form.information.title_remainder')}>
                            <Statistic value={props.informationPageStore.initStatisticsValue.quantityRemainderPage}/>
                        </Card>
                        <Card title={i18next.t('form.information.title_note')}>
                            <Statistic value={props.informationPageStore.initStatisticsValue.quantityNotePage}/>
                        </Card>
                        <Card title={i18next.t('form.information.title_bookmark')}>
                            <Statistic value={props.informationPageStore.initStatisticsValue.quantityBookmarkPage}/>
                        </Card>
                    </div>
                    <Card className='mgb-15' title={i18next.t('form.information.title_rate')}>
                        <Rate disabled={props.informationPageStore.initStatisticsValue.rate !== 0} allowHalf
                              defaultValue={props.informationPageStore.initStatisticsValue.rate ? props.informationPageStore.initStatisticsValue.rate : 2.5}/>
                    </Card>
                </Col>
            </Row>
        </BasePage>
    )
}));

export default withRouter(InformationPage);