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
                    "notification": {
                        "title": {
                            "registration": "Регистрация",
                            "authorization": "Авторизация",
                            "logout": "Выход из системы",
                            "recovery_password": "Восстановление пароля"
                        },
                        "error": {
                            "authorization": "Неизвестная ошибка во время авторизации. Повторите попытку"
                        }
                    },
                    "form": {
                        "login": {
                            "title": "Авторизация",
                            "sign_in": "Войти",
                            "user_name": "Имя пользователя",
                            "password": "Пароль",
                            "or": "Или ",
                            "register_now": "Зарегистрироваться",
                            "forgot_password": "Забыли пароль ?",
                            "error": {
                                "user_name": "Пожалуйста укажите имя пользователя",
                                "password": "Пожалуйста укажите пароль"
                            },
                            "modal": {
                                "title": "Восстановление пароля",
                                "email_extra": "Для восстановления пароля укажите ваш email",
                                "error": {
                                    "email_error_1": "Введен неверный E-mail",
                                    "email_error_2": "Пожалуйста укажите свой E-mail"
                                }
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
                        },
                        "recovery_password": {
                            "title": "Восстановление пароля",
                            "new_password": "Новый пароль",
                            "confirm_new_password": "Подтвердите новый пароль",
                            "set_password": "Сменить пароль",
                            "error": {
                                "new_password": "Пожалуйста укажите свой новый пароль",
                                "confirm_new_password_error_1": "Пожалуйста подтвердите свой новый пароль",
                                "confirm_new_password_error_2": "Два введенных вами пароля не совпадают",
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
                    "notification": {
                        "title": {
                            "registration": "Registration",
                            "authorization": "Authorization",
                            "logout": "Logout",
                            "recovery_password": "Password recovery"
                        },
                        "error": {
                            "authorization": "Unknown error during authorization. Try again"
                        }
                    },
                    "form": {
                        "login": {
                            "title": "Authorization",
                            "sign_in": "Sign in",
                            "user_name": "User name",
                            "password": "Password",
                            "or": "Or ",
                            "register_now": "Register now",
                            "forgot_password": "Forgot your password ?",
                            "error": {
                                "user_name": "Please enter your username",
                                "password": "Please enter a password"
                            },
                            "modal": {
                                "title": "Password recovery",
                                "email_extra": "To recover your password, enter your email",
                                "error": {
                                    "email_error_1": "The enter is not valid E-mail",
                                    "email_error_2": "Please enter your E-mail"
                                }
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
                        },
                        "recovery_password": {
                            "title": "Password recovery",
                            "new_password": "New password",
                            "confirm_new_password": "Confirm new password",
                            "set_password": "Change password",
                            "error": {
                                "new_password": "Please enter your new password",
                                "confirm_new_password_error_1": "Please confirm your new password",
                                "confirm_new_password_error_2": "The two passwords that you entered do not match",
                            }
                        }
                    }
                }
            }
        }
    });
}