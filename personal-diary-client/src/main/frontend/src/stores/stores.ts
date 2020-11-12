import {DiaryPageStore} from "./DiaryPageStore";
import {SheetPageStore} from "./SheetPageStore";
import {InformationPageStore} from "./InformationPageStore";

export class Stores {
    readonly diaryPageStore: DiaryPageStore;
    readonly sheetPageStore: SheetPageStore;
    readonly informationPageStore: InformationPageStore;

    constructor() {
        this.diaryPageStore = new DiaryPageStore();
        this.sheetPageStore = new SheetPageStore();
        this.informationPageStore = new InformationPageStore();
    }
}

export const stores = new Stores();