import {OperationResult} from "../model/OperationResult";
import {notification} from "antd";
import {EConstantValueString} from "../common/EConstantValueString";
import {IconType} from "antd/lib/notification";

export function showNotification(title: string, operationResult: OperationResult) {
    const ln = localStorage.getItem(EConstantValueString.LANGUAGE) ? localStorage.getItem(EConstantValueString.LANGUAGE) : 'ru';
    const description = ln === 'ru' ? operationResult.ruText: operationResult.enText;

    notification.open({
        message: title,
        description: description,
        type: operationResult.resultType as IconType
    });
}