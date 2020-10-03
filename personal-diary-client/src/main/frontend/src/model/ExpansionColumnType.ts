import {ColumnType} from "antd/lib/table";
import {NameSortType} from "./OrderParameter";
import {ColumnGroupType} from "antd/lib/table/interface";

export interface ExpansionColumnType<RecordType> extends ColumnType<RecordType> {
    nameSort?: NameSortType;
}

export declare type ExpansionColumnsType<RecordType = unknown> = (ColumnGroupType<RecordType> | ExpansionColumnType<RecordType>)[];