export interface AppAction {
    type: ActionType;
    token?: string | undefined | null;
    language?: string | undefined | null;
    isLoading?: boolean | undefined;
    isDarkMode?: boolean | undefined;
}

type ActionType = 'RESTORE_TOKEN' | 'SIGN_IN' | 'SIGN_OUT' | 'LANGUAGE' | 'THEME' | 'LOADING';