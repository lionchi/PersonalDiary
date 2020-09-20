import {action, observable} from "mobx";
import {Page} from "../model/Page";
import {createDiaryApi} from "../api/DiaryApi";
import {OperationResult} from "../model/OperationResult";

export class DiaryPageStore {
    @observable pages: Array<Page>;

    constructor() {
        this.pages = [];
    }

    @action
    public async createDiary(userId: number): Promise<OperationResult> {
        const {data} = await createDiaryApi(userId);
        return Promise.resolve(data);
    }
}