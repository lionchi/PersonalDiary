import {action, observable} from "mobx";
import {Tag} from "../model/Tag";
import {Page} from "../model/Page";
import {downloadTags, getPage} from "../api/DiaryApi";
import {convertToMoment} from "../pages/common/function";

export class SheetPageStore {
    @observable tags: Array<Tag>;
    @observable initValues: Page;
    private isTagsDownloaded: boolean;

    constructor() {
       this.clear();
    }

    @action
    public clear(): void {
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
        this.isTagsDownloaded = false;
    }

    @action
    public async fetchTags(): Promise<void> {
        if (!this.isTagsDownloaded) {
            const {data} = await downloadTags()
            this.tags = data;
            this.isTagsDownloaded = true;
        }
    }

    @action
    public async fetchPageByPageId(pageId: number): Promise<void> {
        const {data} = await getPage(pageId);
        const tag = data.tag as Tag;
        this.initValues = {
            ...data,
            tag: tag.code,
            notificationDate: data.notificationDate ? convertToMoment(data.notificationDate as string) : null
        };
    }
}