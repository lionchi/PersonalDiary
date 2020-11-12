export interface StatisticsData {
    quantityPage: number;
    quantityConfPage: number;
    quantityNonConfPage: number;
    quantityNotificationPage: number;
    quantityRemainderPage: number;
    quantityNotePage: number;
    quantityBookmarkPage: number;
    dateOfLastEntry: string;
    dateOfNextNotificationAndReminder: string | number;
}