export interface Filter {
    fieldName: string;
    nameOperation: NameOperationType;
    value: any;
}

export type NameOperationType = 'FIND_BY_DIARY_ID'
    | 'FIND_BY_NOTIFICATION_DATE'
    | 'FIND_BY_CREATE_DATE'
    | 'FIND_BY_CONFIDENTIAL'
    | 'FIND_BY_TAG';