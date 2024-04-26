export interface Customer {
    id: number,
    sharedKey: string,
    businessId: string,
    email: string,
    phone: string,
    startDate: Date,
    endDate: Date,
    dateAdded?: Date
}
