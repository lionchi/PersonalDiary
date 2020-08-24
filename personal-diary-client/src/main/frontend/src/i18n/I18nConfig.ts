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
                        }
                    }
                }
            }
        }
    });
}