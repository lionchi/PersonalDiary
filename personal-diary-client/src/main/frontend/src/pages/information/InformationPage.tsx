import "./InformationPage.css"
import {RouteComponentProps, withRouter} from "react-router";
import {InformationPageStore} from "../../stores/InformationPageStore";
import {inject, observer} from "mobx-react";
import React, {ReactElement, useContext, useEffect, useMemo} from "react";
import BasePage from "../BasePage";
import {
    Button,
    Card,
    Carousel,
    Col,
    DatePicker,
    Form,
    Input,
    Popconfirm,
    Row,
    Select,
    Statistic, Tag,
    Typography
} from "antd";
import i18next from "i18next";
import {useForm} from "antd/lib/form/Form";
import {UserFormData} from "../../model/UserFormData";
import {AppContext} from "../../security/AppContext";
import {Moment} from "moment";
import {showNotification} from "../../utils/notification";
import {EResultType} from "../../model/EResultType";
import {deleteDiary} from "../../api/DiaryApi";
import {deleteUser, updateUserInfo} from "../../api/AccountApi";
import {logout} from "../../api/LogoutApi";
import {OperationResult} from "../../model/OperationResult";
import Countdown from "antd/lib/statistic/Countdown";
import {logoutSystem} from "../common/function";

const {Title, Text} = Typography;
const {Option} = Select;

interface InformationPageProps extends RouteComponentProps {
    informationPageStore?: InformationPageStore;
}

const InformationPage = inject('informationPageStore')(observer((props: InformationPageProps): ReactElement => {

    const [userForm] = useForm();

    const appContext = useContext(AppContext);

    useEffect(() => {
        (async () => {
            appContext.setLoading(true);
            await props.informationPageStore.fetchData(appContext.currentUser);
            appContext.setLoading(false);
        })();
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
        showNotification(i18next.t('notification.title.edit_user'), data);
        if (data.resultType !== EResultType.ERROR) {
            props.informationPageStore.updateInitFormValues(formData);
            userForm.setFields([
                {name: 'password', value: null},
                {name: 'confirmNewPassword', value: null},
            ]);
        }
        appContext.setLoading(false);
    }

    const onClickDelete = async () => {
        appContext.setLoading(true);
        const responseDeleteDiary = await deleteDiary(appContext.currentUser.diaryId);
        showNotification(i18next.t('notification.title.delete_diary'), responseDeleteDiary.data);
        const responseDeleteUser = await deleteUser(appContext.currentUser.id);
        showNotification(i18next.t('notification.title.delete_user'), responseDeleteUser.data);
        await logoutSystem();
        appContext.signOut();
        props.history.push('/login');
        appContext.setLoading(false);
    }

    return (
        <BasePage showBackButton>
            <Carousel className='test' draggable>
                <Row justify="space-between" className='row'>
                    <Col className='col' style={{width: '60%', margin: '0 20% 0 20%'}}>
                        <Title level={3}
                               className="center">{i18next.t('form.information.title_information_user')}</Title>
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
                </Row>
                <Row justify="space-between" className='row'>
                    <Col className='col' style={{width: '60%', margin: '0 20% 0 20%'}}>
                        <Title level={3} className="center">{i18next.t('form.information.title_static')}</Title>
                        <Card className='mgb-8' title={i18next.t('form.information.title_dateOfLastEntry')}>
                            {props.informationPageStore.initStatisticsValue.dateOfLastEntry
                                ?
                                <Statistic value={props.informationPageStore.initStatisticsValue.dateOfLastEntry}/>
                                : <Text
                                    className='text-size-16px'>{i18next.t('form.information.default_dateOfLastEntry')}</Text>
                            }
                        </Card>
                        <Card className='mgb-8' title={i18next.t('form.information.title_dateOfNextNotificationAndReminder')}>
                            <Countdown
                                value={props.informationPageStore.initStatisticsValue.dateOfNextNotificationAndReminder}
                                format={i18next.t('form.information.format_countdown')}/>
                        </Card>
                        <Card className='mgb-8' title={i18next.t('form.information.title_quantity_page')}>
                            <Statistic value={props.informationPageStore.initStatisticsValue.quantityPage}/>
                        </Card>
                        <Card className='mgb-8' title={i18next.t('form.information.title_conf_and_non_conf_page')}>
                            <Statistic value={props.informationPageStore.initStatisticsValue.quantityConfPage}
                                       suffix={`/ ${props.informationPageStore.initStatisticsValue.quantityNonConfPage}`}/>
                        </Card>
                        <Card title={i18next.t('form.information.title_all_tag')}>
                            <div className='div-flex-row'>
                                <Text
                                    className='text-size-24px'>{props.informationPageStore.initStatisticsValue.quantityNotificationPage} - <Tag
                                    color='magenta'>
                                    {i18next.t('form.information.title_notification')}
                                </Tag></Text>
                                <Text
                                    className='text-size-24px'>{props.informationPageStore.initStatisticsValue.quantityRemainderPage} - <Tag
                                    color='red'>
                                    {i18next.t('form.information.title_remainder')}
                                </Tag></Text>
                                <Text
                                    className='text-size-24px'>{props.informationPageStore.initStatisticsValue.quantityNotePage} - <Tag
                                    color='green'>
                                    {i18next.t('form.information.title_note')}
                                </Tag></Text>
                                <Text
                                    className='text-size-24px'>{props.informationPageStore.initStatisticsValue.quantityBookmarkPage} - <Tag
                                    color='blue'>
                                    {i18next.t('form.information.title_bookmark')}
                                </Tag></Text>
                            </div>
                        </Card>
                    </Col>
                </Row>
            </Carousel>
        </BasePage>
    )
}));

export default withRouter(InformationPage);