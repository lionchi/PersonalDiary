import {Tag} from "./Tag";

export interface Page {
    id: number;
    diaryId?: number;
    content: string;
    tag: Tag;
    notificationDate?: string;
    createDate: string;
    recordingSummary: string;
    confidential: boolean;
}