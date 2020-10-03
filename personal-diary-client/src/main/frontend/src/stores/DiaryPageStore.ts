import {action, observable} from "mobx";
import {Page} from "../model/Page";
import {SearchParams} from "../model/SearchParams";
import {getPageAll, getPageTotalCount} from "../api/DiaryApi";
import {OrderParameter} from "../model/OrderParameter";

export class DiaryPageStore {
    @observable pages: Array<Page>;
    @observable pageTotalCount: number;
    @observable pageCurrentNumber: number;
    private searchParams: SearchParams;

    constructor() {
        this.pages = [];
        this.pageTotalCount = 0;
        this.pageCurrentNumber = 1;
        this.searchParams = {pageNumber: 0, pageSize: 8, additionalFilter: [], orderParameters: [{direction: 'desc', nameSort: "SORT_BY_CREATE_DATE"}]};
    }

    @action
    public async fetchPage(diaryId: number): Promise<void> {
        const responseTotalCount = await getPageTotalCount(diaryId);
        this.pageTotalCount = responseTotalCount.data;

        this.searchParams = {
            ...this.searchParams,
            additionalFilter: [{nameFilter: "FIND_BY_DIARY_ID", value: diaryId}]
        }
        const responsePageAll = await getPageAll(this.searchParams);
        this.pages = responsePageAll.data;
    }

    @action
    public async nextPage(page: number, pageSize?: number): Promise<void> {
        const interfacePage = page - 1;
        this.pageCurrentNumber = page;
        if (this.searchParams.pageNumber < interfacePage || this.pages.length < pageSize) {
            this.searchParams = {...this.searchParams, pageNumber: interfacePage, pageSize: pageSize ? pageSize : 8}
            const {data} = await getPageAll(this.searchParams);
            this.pages = [...this.pages, ...data];
        }
    }

    @action
    public async sortPage(orderParameter: OrderParameter): Promise<void> {
        if (orderParameter) {
            this.searchParams = {...this.searchParams, pageNumber: 0, pageSize: 8, orderParameters: [orderParameter]};
        } else {
            this.searchParams = {...this.searchParams, pageNumber: 0, pageSize: 8, orderParameters: []};
        }
        this.pageCurrentNumber = 1;
        const {data} = await getPageAll(this.searchParams);
        this.pages = data;
    }

    @action
    public deletePage(pageId: number): void {
        this.pages = this.pages.filter(item => item.id !== pageId);
    }
}