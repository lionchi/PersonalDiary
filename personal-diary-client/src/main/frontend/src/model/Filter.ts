export interface Filter {
    nameFilter: NameFilterType;
    value: string | number | boolean;
}

export type NameFilterType = 'FIND_BY_DIARY_ID'
    | 'FIND_BY_NOTIFICATION_DATE'
    | 'FIND_BY_CREATE_DATE'
    | 'FIND_BY_CONFIDENTIAL'
    | 'FIND_BY_TAG';

export type DataType =
    'Date'
    | 'String'
    | 'BigDecimal'
    | 'Boolean'
    | 'Integer'
    | 'Long';