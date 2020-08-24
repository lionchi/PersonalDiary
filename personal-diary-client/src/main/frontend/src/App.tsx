import React, {Reducer, useEffect, useMemo, useReducer} from 'react';
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
import {EConstantValueString} from "./common/EConstantValueString";
import i18next from "i18next";
import {initI18n} from "./i18n/I18nConfig";

function App() {

    const [state, dispatch] = useReducer<Reducer<AppContextModel, AppAction>>((prevState, action) => {
        switch (action.type) {
            case 'RESTORE_TOKEN':
                return {
                    ...prevState,
                    userToken: action.token,
                    isSignOut: !!action.token,
                    language: action.language,
                    isDarkMode: action.isDarkMode,
                    needTokenRestore: false,
                    isLoading: false,
                };
            case 'SIGN_IN':
                return {
                    ...prevState,
                    isSignOut: false,
                    userToken: action.token,
                };
            case 'SIGN_OUT':
                return {
                    ...prevState,
                    isSignOut: true,
                    userToken: null,
                };
            case 'LANGUAGE':
                return {
                    ...prevState,
                    language: action.language,
                };
            case 'THEME':
                return {
                    ...prevState,
                    isDarkMode: action.isDarkMode,
                };
            case 'LOADING':
                return {
                    ...prevState,
                    isLoading: action.isLoading,
                };
        }
    }, AppContextInit);

    useEffect(() => {
        const userToken = localStorage.getItem(EConstantValueString.ACCESS_TOKEN);
        const ln = localStorage.getItem(EConstantValueString.LANGUAGE);
        const theme = localStorage.getItem(EConstantValueString.THEME);
        if (state.needTokenRestore) {
            dispatch({type: 'RESTORE_TOKEN', token: userToken, language: ln, isDarkMode: theme === 'dark'});
        }
        initI18n(ln);
    });

    const appContext = useMemo(
        () => ({
            ...state,
            signIn: (token: string): void => {
                localStorage.setItem(EConstantValueString.ACCESS_TOKEN, token);
                dispatch({type: 'SIGN_IN', token: token});
            },
            signOut: (): void => {
                localStorage.removeItem(EConstantValueString.ACCESS_TOKEN);
                dispatch({type: 'SIGN_OUT'});
            },
            setLoading: (loading: boolean): void => {
                dispatch({type: 'LOADING', isLoading: loading})
            },
            setLanguage: (ln: string): void => {
                localStorage.setItem(EConstantValueString.LANGUAGE, ln);
                dispatch({type: 'LANGUAGE', language: ln});
                i18next.changeLanguage(ln);
            },
            setDarkMode: (isDarkMode: boolean, theme: string): void => {
                localStorage.setItem(EConstantValueString.THEME, theme);
                dispatch({type: 'THEME', isDarkMode: isDarkMode})
            },
        }),
        [state],
    );

    return (
        <AppContext.Provider value={appContext}>
            <ConfigProvider componentSize={"large"}>
                <Provider {...stores}>
                    <BrowserRouter basename={baseUrl()}>
                        <Spin size="large" spinning={state.isLoading} style={{marginTop: "15%"}}>
                            <Route component={RootRouter}/>
                        </Spin>
                    </BrowserRouter>
                </Provider>
            </ConfigProvider>
        </AppContext.Provider>
    );
}

export default App;
