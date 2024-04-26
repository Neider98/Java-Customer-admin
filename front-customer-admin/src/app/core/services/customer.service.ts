import { Injectable } from '@angular/core';
import { Customer } from '../models/customer';
import { HttpClient } from '@angular/common/http';
import { Response } from '../models/response';
import { Observable, catchError, map, of } from 'rxjs';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private apiUrl : string = "http://localhost:8080/api/v1/customers";

  constructor (private httpClient : HttpClient) {}

  getCustomerById(customerId: number): Observable<Customer | undefined> {
    const url = `${this.apiUrl}/${customerId}`;
    return this.httpClient.get<Customer>(url).pipe(
      map((customer: Customer) => customer),
      catchError(() => of(undefined))
    );
  }

  getCustomers(): Observable<Customer[]>{
    return this.httpClient.get<Response>(this.apiUrl + "/").pipe(
      map((response: Response) => response.message as Customer[])
    );
  }

  saveCustomer(customer: Customer):void {
    console.log(customer);
  }

  editCustomer(customer: Customer):void {
    console.log(customer)
  }

  exportCsv(): void {
    console.log(this.apiUrl + '/export')
    this.httpClient.get(this.apiUrl + '/export', { responseType: 'blob' }).subscribe({
      next: (data: Blob) => {
        const blob = new Blob([data], { type: 'text/csv' });
        saveAs(blob, 'clientes.csv');
      },
      error: (error) => {
        console.error('Error al exportar los clientes:', error);
        // Manejar el error, si es necesario
      }
    });
  }

}
