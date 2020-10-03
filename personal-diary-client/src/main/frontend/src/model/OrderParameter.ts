export interface OrderParameter {
    nameSort: NameSortType;
    direction: string;
}

export type NameSortType = 'SORT_BY_NOTIFICATION_DATE'
    | 'SORT_BY_TAG'
    | 'SORT_BY_CREATE_DATE'
    | 'SORT_BY_CONFIDENTIAL';