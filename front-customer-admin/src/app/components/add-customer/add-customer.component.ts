import { Customer } from './../../core/models/customer';
import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogRef,
  MatDialogContent,
} from '@angular/material/dialog';

import {provideNativeDateAdapter} from '@angular/material/core';
import { FormCustomerComponent } from "../../shared/components/form-customer/form-customer.component";

@Component({
    selector: 'app-add-customer',
    standalone: true,
    providers: [provideNativeDateAdapter()],
    templateUrl: './add-customer.component.html',
    styleUrl: './add-customer.component.css',
    imports: [
        FormCustomerComponent,
        MatDialogContent
    ]
})
export class AddCustomerComponent {

  customerData: Customer = {
    id: 0,
    sharedKey: "", 
    businessId: "", 
    phone: "", 
    email: "", 
    startDate: new Date, 
    endDate: new Date
  };

  constructor(
    public dialogRef: MatDialogRef<AddCustomerComponent>,
    @Inject(MAT_DIALOG_DATA) public customer: Customer
   ) {
    if (customer !== null) {
      this.customerData = customer;
    }
   }

  saveCustomer($event: Customer) {
    this.dialogRef.close($event);
  }

}
