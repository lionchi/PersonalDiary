import {PersonalDiaryUser} from "../model/PersonalDiaryUser";

export interface AppAction {
    type: ActionType;
    token?: string;
    language?: string;
    isLoading?: boolean;
    isDarkMode?: boolean;
    currentUser?: PersonalDiaryUser;
}

type ActionType = 'RESTORE_TOKEN' | 'RESTORE_USER' | 'SIGN_IN' | 'SIGN_OUT' | 'LANGUAGE' | 'THEME' | 'LOADING';