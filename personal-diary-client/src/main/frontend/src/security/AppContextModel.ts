export interface AppContextModel {
    isLoading: boolean | undefined;
    isSignOut: boolean;
    isDarkMode: boolean | undefined;
    needTokenRestore: boolean;
    userToken: string | undefined | null;
    language: string | undefined | null;
    signIn: (token: string) => void;
    signOut: () => void;
    setLoading: (loading: boolean) => void;
    setLanguage: (ln: string) => void;
    setDarkMode: (isDarkMode: boolean, theme: string) => void;
}