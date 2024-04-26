import { Component, Input } from '@angular/core';
import {MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import { Output, EventEmitter } from '@angular/core';

import { Customer } from '../../core/models/customer';


@Component({
  selector: 'app-table-customer',
  standalone: true,
  imports: [ MatTableModule, MatIconModule ],
  templateUrl: './table-customer.component.html',
  styleUrl: './table-customer.component.css'
})
export class TableCustomerComponent {

  @Input() customers: Customer[] = [];
  @Output() customer = new EventEmitter<number>();

  displayedColumns: string[] = ['sharedKey', 'businessId', 'email', 'phone', 'dataAdded', 'editar'];

  customerEdit(customer: number) {
    console.log(this.customers)
    this.customer.emit(customer);
  }

}
