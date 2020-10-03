import {Key} from "antd/lib/table/interface";
import {Filter} from "../model/Filter";

export function createFilterForDiaryTable(filters: Record<string, Key[] | null>): Filter[] {
    let result = new Array<Filter>();
    for (const filtersKey in filters) {
        if (filtersKey === 'confidential') {
            result.push({
                nameFilter: "FIND_BY_CONFIDENTIAL",
                value: filters.confidential ? filters.confidential[0] : null,
                dataType: "Boolean"
            });
        } else if (filtersKey === 'tag') {
            result.push({
                nameFilter: "FIND_BY_TAG",
                value: filters.tag ? filters.tag[0] : null,
                dataType: "String"
            });
        } else if (filtersKey === 'notificationDate') {

        } else if (filtersKey === 'createDate') {

        } else {
            return null;
        }
    }
    return result;
}