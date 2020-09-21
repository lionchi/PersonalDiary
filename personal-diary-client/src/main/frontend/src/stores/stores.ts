import {DiaryPageStore} from "./DiaryPageStore";
import {SheetPageStore} from "./SheetPageStore";

export class Stores {
    readonly diaryPageStore: DiaryPageStore;
    readonly sheetPageStore: SheetPageStore;

    constructor() {
        this.diaryPageStore = new DiaryPageStore();
        this.sheetPageStore = new SheetPageStore();
    }
}

export const stores: Stores = new Stores();