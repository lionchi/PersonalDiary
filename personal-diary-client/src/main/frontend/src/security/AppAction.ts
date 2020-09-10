import {PersonalDiaryUser} from "../model/PersonalDiaryUser";

export interface AppAction {
    type: ActionType;
    language?: string;
    isLoading?: boolean;
    isDarkMode?: boolean;
    currentUser?: PersonalDiaryUser;
}

type ActionType = 'RESTORE' | 'RESTORE_USER' | 'SIGN_IN' | 'SIGN_OUT' | 'LANGUAGE' | 'THEME' | 'LOADING';