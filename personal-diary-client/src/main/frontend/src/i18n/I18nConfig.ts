import i18next from "i18next";

export const initI18n = (language: string | null): void => {
    i18next.init({
        lng: language ? language : 'ru',
        resources: {
            ru: {
                translation: {
                    "main_title": "Личный дневник",
                    "footer": {
                        "copyright": "Личный дневник ©2020 создано Jpixel",
                        "theme": "Цветовая тема",
                        "translations": "Переводы"
                    },
                    "form": {
                        "login": {
                            "title": "Авторизация",
                            "sign_in": "Войти",
                            "user_name": "Имя пользователя",
                            "password": "Пароль",
                            "or": "Или ",
                            "register_now": "Зарегистрироваться",
                            "error": {
                                "user_name": "Пожалуйста укажите имя пользователя",
                                "password": "Пожалуйста укажите пароль"
                            }
                        },
                        "registration": {
                            "title": "Регистрация",
                            "name": "Имя",
                            "password": "Пароль",
                            "confirm_password": "Подтвердите пароль",
                            "login": "Логин",
                            "tooltip_login": "Имя, которое будет использоваться при входе в систему",
                            "phone": "Номер телефона",
                            "birthday": "Дата рождения",
                            "register_now": "Зарегистрироваться",
                            "error": {
                                "email_error_1": "Введен неверный E-mail",
                                "email_error_2": "Пожалуйста укажите свой E-mail",
                                "name": "Укажите свое имя",
                                "password": "Пожалуйста укажите свой пароль",
                                "confirm_password_error_1": "Пожалуйста подтвердите свой пароль",
                                "confirm_password_error_2": "Два введенных вами пароля не совпадают",
                                "login": "Пожалуйста укажите ваш логин"
                            }
                        }
                    }
                }
            },
            en: {
                translation: {
                    "main_title": "Personal diary",
                    "footer": {
                        "copyright": "Personal diary ©2020 by Jpixel",
                        "theme": "Color theme",
                        "translations": "Translations"
                    },
                    "form": {
                        "login": {
                            "title": "Authorization",
                            "sign_in": "Sign in",
                            "user_name": "User name",
                            "password": "Password",
                            "or": "Or ",
                            "register_now": "Register now",
                            "error": {
                                "user_name": "Please enter your username",
                                "password": "Please enter a password"
                            }
                        },
                        "registration": {
                            "title": "Registration",
                            "name": "Name",
                            "password": "Password",
                            "confirm_password": "Confirm password",
                            "login": "Login",
                            "tooltip_login": "The name to be used when entering the system",
                            "phone": "Phone Number",
                            "birthday": "Date of Birth",
                            "register_now": "Register now",
                            "error": {
                                "email_error_1": "The enter is not valid E-mail",
                                "email_error_2": "Please enter your E-mail",
                                "name": "Please enter your name",
                                "password": "Please enter your password",
                                "confirm_password_error_1": "Please confirm your password",
                                "confirm_password_error_2": "The two passwords that you entered do not match",
                                "login": "Please enter your login"
                            }
                        }
                    }
                }
            }
        }
    });
}