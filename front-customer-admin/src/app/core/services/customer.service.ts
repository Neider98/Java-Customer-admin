import { Injectable } from '@angular/core';
import { Customer } from '../models/customer';
import { HttpClient } from '@angular/common/http';
import { Response } from '../models/response';
import { Observable, catchError, map, of, tap } from 'rxjs';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private apiUrl : string = "http://localhost:8080/api/v1/customers";

  constructor (private httpClient : HttpClient) {}

  getCustomerById(customerId: number): Observable<Customer | undefined> {
    const url = `${this.apiUrl}/${customerId}`;
    return this.httpClient.get<Response>(url).pipe(
      map((response: Response) => response.message[0] as Customer),
      catchError(() => of(undefined))
    );
  }

  getCustomers(): Observable<Customer[]>{
    return this.httpClient.get<Response>(this.apiUrl + "/").pipe(
      map((response: Response) => response.message as Customer[])
    );
  }

  saveCustomer(customer: Customer): Observable<Customer> {
    return this.httpClient.post<Customer>(this.apiUrl, customer).pipe(
      tap(() => console.log('Cliente guardado exitosamente')),
      catchError(error => {
        console.error('Error al guardar el cliente:', error);
        throw error; 
      })
    );
  }

  editCustomer(customer: Customer): Observable<Customer> {
    const url = `${this.apiUrl}/${customer.id}`;
    return this.httpClient.put<Customer>(url, customer).pipe(
      tap(() => console.log('Cliente editado exitosamente')),
      catchError(error => {
        console.error('Error al editar el cliente:', error);
        throw error;
      })
    );
  }

  exportCsv(): void {
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
