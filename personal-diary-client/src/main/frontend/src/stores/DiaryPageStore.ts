import {action, observable} from "mobx";
import {Page} from "../model/Page";
import {SearchParams} from "../model/SearchParams";
import {getPageAll} from "../api/DiaryApi";
import {OrderParameter} from "../model/OrderParameter";
import {Filter} from "../model/Filter";

export class DiaryPageStore {
    @observable pages: Array<Page>;
    @observable pageTotalCount: number;
    @observable pageCurrentNumber: number;
    private searchParams: SearchParams;

    constructor() {
        this.pages = [];
        this.pageTotalCount = 0;
        this.pageCurrentNumber = 1;
        this.searchParams = {
            pageNumber: 0,
            pageSize: 8,
            additionalFilter: [],
            orderParameters: [{direction: 'desc', nameSort: "SORT_BY_CREATE_DATE"}]
        };
    }

    @action
    public async fetchPage(diaryId: number): Promise<void> {
        this.searchParams = {
            ...this.searchParams,
            additionalFilter: [{nameFilter: "FIND_BY_DIARY_ID", value: diaryId, dataType: "Long"}]
        }
        const {data} = await getPageAll(this.searchParams);
        this.pageTotalCount = data.totalCount;
        this.pages = data.pages;
    }

    @action
    public async nextPage(page: number, pageSize?: number): Promise<void> {
        const interfacePage = page - 1;
        this.pageCurrentNumber = page;
        if (this.searchParams.pageNumber < interfacePage || this.pages.length < pageSize) {
            this.searchParams = {...this.searchParams, pageNumber: interfacePage, pageSize: pageSize ? pageSize : 8}
            const {data} = await getPageAll(this.searchParams);
            this.pages = [...this.pages, ...data.pages];
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
        this.pages = data.pages;
    }

    @action
    public async filterPage(filters: Filter[]): Promise<void> {
        for (let filter of filters) {
            const currentFilters = this.searchParams.additionalFilter.filter(item => item.nameFilter !== filter.nameFilter);
            if (filter.value) {
                this.searchParams = {
                    ...this.searchParams,
                    pageNumber: 0,
                    pageSize: 8,
                    additionalFilter: [...currentFilters, filter]
                };
            } else {
                this.searchParams = {
                    ...this.searchParams,
                    pageNumber: 0,
                    pageSize: 8,
                    additionalFilter: currentFilters
                };
            }
        }
        this.pageCurrentNumber = 1;
        const {data} = await getPageAll(this.searchParams);
        this.pageTotalCount = data.totalCount
        this.pages = data.pages;
    }

    @action
    public deletePage(pageId: number): void {
        this.pages = this.pages.filter(item => item.id !== pageId);
    }
}