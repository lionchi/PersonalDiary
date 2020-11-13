import {RouteComponentProps, withRouter} from "react-router";
import React, {ReactElement, useCallback, useContext, useEffect, useMemo} from "react";
import BasePage from "../BasePage";
import {inject, observer} from "mobx-react";
import "./SheetPage.css"
import {IMatchParams} from "../../model/IMatchParams";
import i18next from "i18next";
import {Button, Checkbox, Col, DatePicker, Form, Input, Row, Select, Typography, Tooltip} from "antd";
import {Page} from "../../model/Page";
import TextArea from "antd/lib/input/TextArea";
import {AppContext} from "../../security/AppContext";
import {ETag} from "../../model/ETag";
import {renderTag} from "../common/function";
import {showNotification} from "../../utils/notification";
import {EResultType} from "../../model/EResultType";
import {Moment} from "moment";
import {createPage} from "../../api/DiaryApi";
import {SheetPageStore} from "../../stores/SheetPageStore";

const {Title} = Typography;
const {Option} = Select;

interface IDiaryNewPageProps extends RouteComponentProps<IMatchParams> {
    sheetPageStore?: SheetPageStore;
}

const NewSheetPage = inject("sheetPageStore")(observer((props: IDiaryNewPageProps): ReactElement => {
    const appContext = useContext(AppContext);
    const [sheetForm] = Form.useForm();

    useEffect(() => {
        (async () => {
            appContext.setLoading(true);
            await props.sheetPageStore.fetchTags();
            appContext.setLoading(false);
        })();
    }, [])

    const tags = useMemo(() => props.sheetPageStore.tags, [props.sheetPageStore.tags]);

    const onFinish = useCallback(async (formData: Page) => {
        appContext.setLoading(true);
        if (formData.notificationDate) {
            const notificationDateAsMoment = formData.notificationDate as Moment;
            formData = {...formData, notificationDate: notificationDateAsMoment.format("DD.MM.YYYY")};
        }
        const {data} = await createPage({
            ...formData,
            diaryId: appContext.currentUser.diaryId,
            tag: tags.find(item => item.code === sheetForm.getFieldValue('tag'))
        });
        showNotification(i18next.t('notification.title.create_page'), data);
        if (data.resultType !== EResultType.ERROR) {
            setTimeout(() => {
                props.history.goBack();
            }, 500)
        }
        appContext.setLoading(false);
    }, [tags, appContext, props, sheetForm]);

    return (
        <BasePage showBackButton>
            <Row justify="center" align="middle" className="row">
                <Col flex="650px">
                    <Title level={3} className="center">{i18next.t('form.page.create_title')}</Title>
                    <Form form={sheetForm} name="sheet_form" initialValues={{confidential: false}}
                          onFinish={onFinish} scrollToFirstError labelCol={{span: 10}}>
                        <Form.Item name="recordingSummary" label={i18next.t('form.page.recording_summary')}
                                   rules={[{required: true, message: i18next.t('form.page.error.recording_summary')}]}>
                            <Input allowClear/>
                        </Form.Item>

                        <Form.Item name="tag" label={i18next.t('form.page.tag')}
                                   rules={[{required: true, message: i18next.t('form.page.error.tag')}]}>
                            <Select showArrow allowClear>
                                {tags.map(item => (<Option key={item.id}
                                                           value={item.code}>{renderTag(item, appContext.language)}</Option>))}
                            </Select>
                        </Form.Item>

                        <Form.Item
                            name="content"
                            label={i18next.t('form.page.content')}
                            rules={[{required: true, message: i18next.t('form.page.error.content')}]}>
                            <TextArea rows={10} allowClear/>
                        </Form.Item>

                        <Form.Item name="notificationDate" label={i18next.t('form.page.notification_date')}
                                   dependencies={['tag']}
                                   rules={[
                                       ({getFieldValue}) => ({
                                           validator(rule, value) {
                                               const tagCode = getFieldValue('tag');
                                               if (!value && (tagCode === ETag.NOTIFICATION || tagCode === ETag.REMINDER)) {
                                                   return Promise.reject(i18next.t('form.page.error.notification_date'));
                                               }
                                               return Promise.resolve();
                                           },
                                       }),
                                   ]}>
                            <DatePicker className="width-100" format="DD.MM.YYYY"/>
                        </Form.Item>

                        <Form.Item name="confidential" valuePropName="checked" wrapperCol={{push: 10}}>
                            <Tooltip title={i18next.t('form.page.tooltip_confidential')}>
                                <Checkbox>{i18next.t('form.page.confidential')}</Checkbox>
                            </Tooltip>
                        </Form.Item>

                        <Form.Item className="center">
                            <Button type="primary" htmlType="submit">{i18next.t('form.page.create_btn')}</Button>
                        </Form.Item>
                    </Form>
                </Col>
            </Row>
        </BasePage>
    )
}))

export default withRouter(NewSheetPage);