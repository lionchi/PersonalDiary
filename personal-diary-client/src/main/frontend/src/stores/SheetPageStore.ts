import {action, observable} from "mobx";
import {Tag} from "../model/Tag";
import {Page} from "../model/Page";
import {downloadTags, getPage} from "../api/DiaryApi";
import moment from "moment";

export class SheetPageStore {
    @observable tags: Array<Tag>;
    @observable initValues: Page;

    constructor() {
        this.tags = [];
        this.initValues = {
            id: null,
            diaryId: null,
            content: null,
            recordingSummary: null,
            notificationDate: null,
            createDate: null,
            tag: null,
            confidential: false
        };
    }

    @action
    public async fillTags(): Promise<void> {
        if (this.tags.length === 0) {
            const {data} = await downloadTags()
            this.tags = data;
        }
    }

    @action
    public async setInitValuesByPageId(pageId: number): Promise<void> {
        const {data} = await getPage(pageId);
        const tag = data.tag as Tag;
        this.initValues = {
            ...data,
            tag: tag.code,
            notificationDate: data.notificationDate ? moment(data.notificationDate, 'DD.MM.YYYY') : null
        };
    }

    @action
    public setInitValuesEmpty(): void {
        this.initValues = {
            id: null,
            diaryId: null,
            content: null,
            recordingSummary: null,
            notificationDate: null,
            createDate: null,
            tag: null,
            confidential: false
        };
    }
}