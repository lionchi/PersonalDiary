import React, {ReactElement, useCallback} from "react";
import {Button, Col, Form, Input, Row, Typography} from "antd";
import {LockOutlined, UserOutlined} from '@ant-design/icons';
import {LoginFormData} from "../../model/LoginFormData";
import "./LoginPage.css"
import BasePage from "../BasePage";

const {Title, Text, Link} = Typography;

const LoginPage = (): ReactElement => {

    const onFinish = useCallback((data: LoginFormData) => {
        console.log(data.username);
    }, [])

    return (
        <BasePage>
            <Row justify="center" align="middle" className="row">
                <Col flex="400px">
                    <Title level={3} className="form-title">Авторизация</Title>
                    <Form name="login_form" initialValues={{}} onFinish={onFinish}>
                        <Form.Item name="username"
                                   rules={[{required: true, message: 'Пожалуйста укажите имя пользователя'}]}>
                            <Input prefix={<UserOutlined className="site-form-item-icon"/>}
                                   placeholder="Имя пользователя"/>
                        </Form.Item>
                        <Form.Item name="password" rules={[{required: true, message: 'Пожалуйста укажите пароль'}]}>
                            <Input prefix={<LockOutlined className="site-form-item-icon"/>} type="password"
                                   placeholder="Пароль"/>
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" htmlType="submit" className="form-btn">Войти</Button>
                        </Form.Item>
                        <Form.Item>
                            <Text className="form-text-size">Или </Text>
                            <Link className="form-text-size" href={"*"}>Зарегистрироваться сейчас</Link>
                        </Form.Item>
                    </Form>
                </Col>
            </Row>
        </BasePage>
    )
}

export default LoginPage;