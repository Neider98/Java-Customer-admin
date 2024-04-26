import { Customer } from "./customer";

export interface Response {
    status: String,
    message: Customer [],
    code: number,
    date: Date
}