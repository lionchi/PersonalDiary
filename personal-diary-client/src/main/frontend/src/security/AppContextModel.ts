import {PersonalDiaryUser} from "../model/PersonalDiaryUser";

export interface AppContextModel {
    isLoading: boolean;
    isSignOut: boolean;
    isDarkMode: boolean;
    needTokenRestore: boolean;
    language: string;
    currentUser: PersonalDiaryUser;

    signIn: () => void;
    signOut: () => void;
    setLoading: (loading: boolean) => void;
    setLanguage: (ln: string) => void;
    setDarkMode: (isDarkMode: boolean, theme: string) => void;
}