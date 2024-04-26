import { CommonModule } from '@angular/common';
import { Customer } from './../../../core/models/customer';
import { Component, EventEmitter, Input, Output, output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerInputEvent, MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-form-customer',
  standalone: true,
  imports: [
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    CommonModule
  ],
  templateUrl: './form-customer.component.html',
  styleUrl: './form-customer.component.css'
})
export class FormCustomerComponent {

  @Input() customerData: Customer = {
    id: 0,
    sharedKey: "", 
    businessId: "", 
    phone: "", 
    email: "", 
    startDate: new Date, 
    endDate: new Date
  };
  @Output() customerOutput = new EventEmitter<Customer>; 
  @Output() closeForm = new EventEmitter<boolean>();

  closed: boolean = false;

  constructor(){
    console.log(this.customerData)
  }

  onEndDateChange(event: MatDatepickerInputEvent<Date>) {
    if (event.value) this.customerData.startDate = event.value;
  }
  onStartDateChange(event: MatDatepickerInputEvent<Date>) {
    if (event.value) this.customerData.endDate = event.value;;
  }

  onSave() {
    this.closed = true;
    this.closeForm.emit(this.closed)
    this.customerOutput.emit(this.customerData);
  }

}
