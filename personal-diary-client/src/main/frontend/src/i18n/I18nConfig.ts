import i18next from "i18next";

export const initI18n = (language: string | null): void => {
    i18next.init({
        lng: language ? language : 'ru',
        resources: {
            ru: {
                translation: {
                    "header": {
                        "main_title": "Личный дневник",
                        "tooltip_back": "Вернуться назад",
                    },
                    "footer": {
                        "copyright": "Личный дневник ©2020 создано Jpixel",
                        "theme": "Цветовая тема",
                        "translations": "Переводы"
                    },
                    "spin": {
                        "tip": "Загрузка..."
                    },
                    "notification": {
                        "title": {
                            "registration": "Регистрация",
                            "authorization": "Авторизация",
                            "logout": "Выход из системы",
                            "recovery_password": "Восстановление пароля",
                            "personal_diary": "Личный дневник",
                            "create_page": "Создание записи",
                            "delete_page": "Удаление записи",
                            "edit_page": "Редактирование записи"
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
                        },
                        "diary": {
                            "create_btn": "Создать свой словарь"
                        },
                        "page": {
                            "edit_title": "Редактирование записи",
                            "edit_btn": "Сохранить изменения",
                            "create_title": "Создание записи",
                            "create_btn": "Создать запись",
                            "recording_summary": "Краткое содержимое записи",
                            "content": "Содержимое записи",
                            "notification_date": "Дата уведомления/напоминания",
                            "confidential": "Конфиденциальность",
                            "tag": "Тег",
                            "error": {
                                "recording_summary": "Пожалуйста укажите краткое содержимое записи",
                                "content": "Пожалуйста заполните содержимое записи",
                                "notification_date": "Пожалуйста укажите дату уведомления/напоминания",
                                "tag": "Пожалуйста выберите тег для заполняемой записи"
                            }
                        },
                        "information": {
                            "title_information_user": "Ваши данные",
                            "title_static": "Ваша статистика пользолвания дневником",
                            "title_quantity_page": "Количество записей в дневнике",
                            "title_conf_page": "Количество конфиденциальных записей",
                            "title_non_conf_page": "Количество неконфиденциальных записей",
                            "title_notification": "Количество уведомлений",
                            "title_remainder": "Количество напоминаний",
                            "title_note": "Количество заметок",
                            "title_bookmark": "Количество закладок",
                            "title_rate": "Ваша оценка приложению",
                            "change_info": "Изменить данные",
                            "delete": "Удалить свой личный дневник",
                            "popconfirm": "Вы действительно хотите удалить свой личный дневник навсегда ? Все ваши данные будут удалены",
                            "name": "Имя",
                            "new_password": "Новый пароль",
                            "confirm_new_password": "Подтвердите новый пароль",
                            "phone": "Номер телефона",
                            "birthday": "Дата рождения",
                            "error": {
                                "email_error_1": "Введен неверный E-mail",
                                "email_error_2": "Пожалуйста укажите свой E-mail",
                                "name": "Укажите свое имя",
                                "confirm_new_password_error": "Два введенных вами новых пароля не совпадают",
                            }
                        }
                    },
                    "table": {
                        "diary": {
                            "add_btn": "Сделать запись",
                            "popconfirm": "Вы действительно хотите удалить запись ?",
                            "column_summary": "Кртакое содержимое записи",
                            "tooltip_summary": "Событие дня",
                            "column_tag": "Тег",
                            "column_notification_date": "Дата уведомления/напоминания",
                            "column_create_date": "Дата создания записи",
                            "column_confidential": "Конфиденциальность",
                            "column_action": "Действие",
                            "filter": {
                                "confidential_true": "Показывать только конфиденциальные записи",
                                "confidential_false": "Показывать только неконфиденциальные записи",
                                "note": "Заметка",
                                "notification": "Уведомление",
                                "reminder": "Напоминание",
                                "bookmark": "Закладка",
                            }
                        }
                    },
                    "component": {
                        "date_filter": {
                            "placeholder": "Выберите дату для фильтра",
                            "reset": "Сбросить",
                            "confirm": "ОК",
                        }
                    }
                }
            },
            en: {
                translation: {
                    "header": {
                        "main_title": "Personal diary",
                        "tooltip_back": "Come back",
                    },
                    "footer": {
                        "copyright": "Personal diary ©2020 by Jpixel",
                        "theme": "Color theme",
                        "translations": "Translations"
                    },
                    "spin": {
                        "tip": "Loading..."
                    },
                    "notification": {
                        "title": {
                            "registration": "Registration",
                            "authorization": "Authorization",
                            "logout": "Logout",
                            "recovery_password": "Password recovery",
                            "personal_diary": "Personal diary",
                            "create_page": "Create record",
                            "delete_page": "Delete record",
                            "edit_page": "Editing record"
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
                        },
                        "diary": {
                            "create_btn": "Create your dictionary"
                        },
                        "page": {
                            "edit_title": "Editing record",
                            "edit_btn": "Save changes",
                            "create_title": "Create record",
                            "create_btn": "Create record",
                            "recording_summary": "Recoding summary",
                            "content": "Record content",
                            "notification_date": "Date of notification/reminders",
                            "confidential": "Confidentiality",
                            "tag": "Tag",
                            "error": {
                                "recording_summary": "Please enter a brief content of the entry",
                                "content": "Please fill in the content of the entry",
                                "notification_date": "Please enter the date of notification / reminder",
                                "tag": "Please select a tag for the filled entry"
                            }
                        },
                        "information": {
                            "title_information_user": "Your data",
                            "title_static": "Your statistics of using the diary",
                            "title_quantity_page": "Number of entries in the diary",
                            "title_conf_page": "Number of confidential records",
                            "title_non_conf_page": "Number of non-confidential entries",
                            "title_notification": "Number of notifications",
                            "title_remainder": "Number of reminders",
                            "title_note": "Number of notes",
                            "title_bookmark": "Number of bookmarks",
                            "title_rate": "Your rating for the application",
                            "change_info": "To change the data",
                            "delete": "Delete your personal diary",
                            "popconfirm": "Are you sure you want to delete your personal diary permanently ? All your data will be deleted",
                            "name": "Name",
                            "new_password": "New password",
                            "confirm_new_password": "Confirm new password",
                            "phone": "Phone Number",
                            "birthday": "Date of Birth",
                            "error": {
                                "email_error_1": "The enter is not valid E-mail",
                                "email_error_2": "Please enter your E-mail",
                                "name": "Please enter your name",
                                "confirm_new_password_error": "The two new passwords that you entered do not match",
                            }
                        }
                    },
                    "table": {
                        "diary": {
                            "add_btn": "Make an entry",
                            "popconfirm": "Are you sure you want to delete the entry ?",
                            "column_summary": "Recoding summary",
                            "tooltip_summary": "Event of the day",
                            "column_tag": "Tag",
                            "column_notification_date": "Date of notification/reminders",
                            "column_create_date": "Post creation date",
                            "column_confidential": "Confidentiality",
                            "column_action": "Action",
                            "filter": {
                                "confidential_true": "Show only confidential entries",
                                "confidential_false": "Show only non-confidential entries",
                                "note": "Note",
                                "notification": "Notification",
                                "reminder": "Reminder",
                                "bookmark": "Bookmark",
                            }
                        }
                    },
                    "component": {
                        "date_filter": {
                            "placeholder": "Select a date to filter",
                            "reset": "Reset",
                            "confirm": "OK",
                        }
                    }
                }
            }
        }
    });
}