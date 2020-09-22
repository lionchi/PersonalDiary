import "./SheetPage.css"
import {inject, observer} from "mobx-react";
import React, {ReactElement, useCallback, useContext, useEffect, useMemo} from "react";
import {RouteComponentProps, withRouter} from "react-router";
import {AppContext} from "../../security/AppContext";
import {Button, Checkbox, Col, DatePicker, Form, Input, Row, Select, Typography} from "antd";
import i18next from "i18next";
import {renderTag} from "../common/function";
import TextArea from "antd/lib/input/TextArea";
import {ETag} from "../../model/ETag";
import BasePage from "../BasePage";
import {IMatchParams} from "../../model/IMatchParams";
import {SheetPageStore} from "../../stores/SheetPageStore";
import {Page} from "../../model/Page";
import {Moment} from "moment";
import {updatePage} from "../../api/DiaryApi";
import {showNotification} from "../../utils/notification";
import {EResultType} from "../../model/EResultType";

const {Title} = Typography;
const {Option} = Select;

interface IEditSheetPageProps extends RouteComponentProps<IMatchParams> {
    sheetPageStore?: SheetPageStore;
}

const EditSheetPage = inject("sheetPageStore")(observer((props: IEditSheetPageProps): ReactElement => {
    const appContext = useContext(AppContext);
    const [sheetForm] = Form.useForm();

    useEffect(() => {
        appContext.setLoading(true);
        props.sheetPageStore.fetchTags();
        props.sheetPageStore.fetchPageByPageId(Number(props.match.params.pageId));
        appContext.setLoading(false);
    }, [props.match.params.pageId]);

    useMemo(() => {
        const {initValues} = props.sheetPageStore;
        sheetForm.setFields([
            {name: 'recordingSummary', value: initValues.recordingSummary},
            {name: 'tag', value: initValues.tag},
            {name: 'content', value: initValues.content},
            {name: 'notificationDate', value: initValues.notificationDate},
            {name: 'confidential', value: initValues.confidential},
        ]);
    }, [sheetForm, props.sheetPageStore.initValues]);

    const tags = useMemo(() => props.sheetPageStore.tags, [props.sheetPageStore.tags]);

    const onFinish = useCallback(async (formData: Page) => {
        appContext.setLoading(true);
        if (formData.notificationDate) {
            const notificationDateAsMoment = formData.notificationDate as Moment;
            formData = {...formData, notificationDate: notificationDateAsMoment.format("DD.MM.YYYY")};
        }
        const {data} = await updatePage({
            ...formData,
            id: Number(props.match.params.pageId),
            tag: tags.find(item => item.code === sheetForm.getFieldValue('tag'))
        });
        showNotification(i18next.t('notification.title.edit_page'), data);
        if (data.resultType !== EResultType.ERROR) {
            setTimeout(() => {
                props.history.goBack();
            }, 500)
        }
        appContext.setLoading(false);
    }, [tags, appContext, props, sheetForm]);

    return (
        <BasePage>
            <Row justify="center" align="middle" className="row">
                <Col flex="650px">
                    <Title level={3} className="center">{i18next.t('form.page.edit_title')}</Title>
                    <Form form={sheetForm} name="sheet_form"
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
                            <Checkbox>{i18next.t('form.page.confidential')}</Checkbox>
                        </Form.Item>

                        <Form.Item className="center">
                            <Button type="primary" htmlType="submit">{i18next.t('form.page.edit_btn')}</Button>
                        </Form.Item>
                    </Form>
                </Col>
            </Row>
        </BasePage>
    )
}))

export default withRouter(EditSheetPage);