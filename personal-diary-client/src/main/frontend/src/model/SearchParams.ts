import {Filter} from "./Filter";
import {OrderParameter} from "./OrderParameter";

export interface SearchParams {
    pageNumber: number;
    pageSize: number;
    additionalFilter?: Filter[];
    orderParameters?: OrderParameter[];
}