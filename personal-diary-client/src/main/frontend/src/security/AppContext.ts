import {AppContextModel} from "./AppContextModel";
import {createContext} from "react";

const AppContextInit: AppContextModel = {
    isLoading: true,
    isSignOut: true,
    needTokenRestore: true,
    language: 'ru',
    userToken: null,
    signIn: () => ({}),
    signOut: () => ({}),
    setLoading: () => ({}),
    setLanguage: () => ({})
}


const AppContext = createContext<AppContextModel>(AppContextInit);

export {AppContextInit, AppContext}