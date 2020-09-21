import {Tag} from "./Tag";
import {Moment} from "moment";

export interface Page {
    id: number;
    diaryId?: number;
    content: string;
    tag: Tag | string;
    notificationDate?: Moment | string;
    createDate: string;
    recordingSummary: string;
    confidential: boolean;
}