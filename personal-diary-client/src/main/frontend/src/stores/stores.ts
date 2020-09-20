import {DiaryPageStore} from "./DiaryPageStore";

export class Stores {
    readonly diaryPageStore: DiaryPageStore;

    constructor() {
        this.diaryPageStore = new DiaryPageStore();
    }
}

export const stores: Stores = new Stores();