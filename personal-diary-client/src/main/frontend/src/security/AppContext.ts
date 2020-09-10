import {AppContextModel} from "./AppContextModel";
import {createContext} from "react";

const AppContextInit: AppContextModel = {
    isLoading: true,
    isSignOut: true,
    isDarkMode: false,
    needTokenRestore: true,
    language: 'ru',
    currentUser: {},
    signIn: () => ({}),
    signOut: () => ({}),
    setLoading: () => ({}),
    setLanguage: () => ({}),
    setDarkMode: () => ({})
}


const AppContext = createContext<AppContextModel>(AppContextInit);

export {AppContextInit, AppContext}