import React, {ReactElement} from "react";
import {FilterDropdownProps} from "antd/lib/table/interface";
import {Button, DatePicker} from "antd";
import moment from "moment";
import i18next from "i18next";
import "./Filter.css"

const DateFilter = (props: FilterDropdownProps): ReactElement => {
    return (
        <div className="ant-table-filter-dropdown">
            <div className="div-date-filed">
                <DatePicker
                    className="date-field"
                    placeholder={i18next.t("component.date_filter.placeholder")}
                    format="DD.MM.YYYY"
                    value={props.selectedKeys[0] ? moment(props.selectedKeys[0], 'DD.MM.YYYY') : null}
                    onChange={e => props.setSelectedKeys(e ? [e.format("DD.MM.YYYY")] : [])}
                />
            </div>
            <div className="ant-table-filter-dropdown-btns">
                <Button type="link" onClick={() => props.clearFilters()} size="small"
                        disabled={!props.selectedKeys[0]}>{i18next.t("component.date_filter.reset")}</Button>
                <Button type="primary" onClick={() => props.confirm()}
                        size="small">{i18next.t("component.date_filter.confirm")}</Button>
            </div>
        </div>
    )
}

export default DateFilter;