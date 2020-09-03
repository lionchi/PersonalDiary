import {PersonalDiaryUser} from "../model/PersonalDiaryUser";

export interface AppContextModel {
    isLoading: boolean;
    isSignOut: boolean;
    isDarkMode: boolean;
    needTokenRestore: boolean;
    userToken: string;
    language: string;
    currentUser: PersonalDiaryUser;

    signIn: (token: string) => void;
    signOut: () => void;
    setLoading: (loading: boolean) => void;
    setLanguage: (ln: string) => void;
    setDarkMode: (isDarkMode: boolean, theme: string) => void;
}