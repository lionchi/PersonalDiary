import {action, observable} from "mobx";
import {Page} from "../model/Page";

export class DiaryPageStore {
    @observable pages: Array<Page>;

    constructor() {
        this.pages = [];
    }

    @action
    public deletePage(pageId: number) {
        const foundPage = this.pages.find(item => item.id === pageId);
        if (foundPage) {
            this.pages.splice(this.pages.indexOf(foundPage), 1);
        }
    }
}