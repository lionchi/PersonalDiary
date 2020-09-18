import React, {Reducer, useCallback, useEffect, useMemo, useReducer} from 'react';
import {stores} from "./stores/stores";
import {BrowserRouter} from "react-router-dom";
import {Route} from "react-router";
import {baseUrl} from "./utils/config";
import RootRouter from "./router/RootRouter";
import {Provider} from "mobx-react";
import {ConfigProvider, Spin} from "antd";
import {AppContextModel} from "./security/AppContextModel";
import {AppAction} from "./security/AppAction";
import {AppContext, AppContextInit} from "./security/AppContext";
import {EConstantValueString} from "./model/EConstantValueString";
import i18next from "i18next";
import {initI18n} from "./i18n/I18nConfig";
import enGB from 'antd/es/locale/en_GB';
import ruRU from 'antd/es/locale/ru_RU';
import get from 'lodash/get';
import isEmpty from 'lodash/isEmpty';
import {accountInformation} from "./api/AccountApi";
import {PermissionsProvider} from "@tshio/react-router-permissions";

function App() {

    const [state, dispatch] = useReducer<Reducer<AppContextModel, AppAction>>((prevState, action) => {
        switch (action.type) {
            case 'RESTORE':
                return {
                    ...prevState,
                    language: get(action, 'language', 'ru'),
                    isDarkMode: action.isDarkMode ? action.isDarkMode : false,
                    needTokenRestore: false,
                    isLoading: false,
                };
            case 'RESTORE_USER':
                return {
                    ...prevState,
                    currentUser: isEmpty(action.currentUser) || !action.currentUser ? {} : action.currentUser,
                    isSignOut: !!(isEmpty(action.currentUser) || !action.currentUser)
                };
            case 'SIGN_IN':
                return {
                    ...prevState,
                    isSignOut: false,
                    currentUser: isEmpty(action.currentUser) || !action.currentUser ? {} : action.currentUser
                };
            case 'SIGN_OUT':
                return {
                    ...prevState,
                    isSignOut: true,
                    currentUser: {}
                };
            case 'LANGUAGE':
                return {
                    ...prevState,
                    language: get(action, 'language', 'ru'),
                };
            case 'THEME':
                return {
                    ...prevState,
                    isDarkMode: action.isDarkMode ? action.isDarkMode : false,
                };
            case 'LOADING':
                return {
                    ...prevState,
                    isLoading: action.isLoading ? action.isLoading : false,
                };
        }
    }, AppContextInit);

    useEffect(() => {
        const ln = localStorage.getItem(EConstantValueString.LANGUAGE);
        const theme = localStorage.getItem(EConstantValueString.THEME);
        if (state.needTokenRestore) {
            dispatch({
                type: 'RESTORE',
                language: ln ? ln : 'ru',
                isDarkMode: theme ? theme === 'dark' : false
            });
            accountInformation()
                .then(({data}) => {
                    dispatch({type: 'RESTORE_USER', currentUser: data});
                })
                .catch(e => console.log())
        }
        initI18n(ln);
    });

    const appContext = useMemo(
        () => ({
            ...state,
            signIn: async (): Promise<void> => {
                const {data} = await accountInformation();
                dispatch({type: 'SIGN_IN', currentUser: data});
            },
            signOut: (): void => {
                dispatch({type: 'SIGN_OUT'});
            },
            setLoading: (loading: boolean): void => {
                dispatch({type: 'LOADING', isLoading: loading})
            },
            setLanguage: async (ln: string): Promise<void> => {
                localStorage.setItem(EConstantValueString.LANGUAGE, ln);
                dispatch({type: 'LANGUAGE', language: ln});
                await i18next.changeLanguage(ln);
            },
            setDarkMode: (isDarkMode: boolean, theme: string): void => {
                localStorage.setItem(EConstantValueString.THEME, theme);
                dispatch({type: 'THEME', isDarkMode: isDarkMode})
            },
        }),
        [state],
    );

    const authorizationStrategy = useCallback((roles: string[], requirement: string[]) => {
        if (roles && requirement) {
            for (let role of roles) {
                for (let requirementRole of requirement) {
                    if (role === requirementRole) {
                        return true;
                    }
                }
            }
        }
    }, []);

    return (
        <AppContext.Provider value={appContext}>
            <ConfigProvider locale={state.language === 'en' ? enGB : ruRU} componentSize={"large"}>
                <Provider {...stores}>
                    <PermissionsProvider permissions={state.currentUser.roles as string[]} authorizationStrategy={authorizationStrategy}>
                        <BrowserRouter basename={baseUrl()}>
                            <Spin size="large" spinning={state.isLoading} style={{marginTop: "15%"}}>
                                <Route component={RootRouter}/>
                            </Spin>
                        </BrowserRouter>
                    </PermissionsProvider>
                </Provider>
            </ConfigProvider>
        </AppContext.Provider>
    );
}

export default App;
