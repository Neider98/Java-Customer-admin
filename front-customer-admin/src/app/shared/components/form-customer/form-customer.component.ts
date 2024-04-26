import { CommonModule } from '@angular/common';
import { Customer } from './../../../core/models/customer';
import { Component, EventEmitter, Input, Output, output } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
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
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './form-customer.component.html',
  styleUrl: './form-customer.component.css'
})
export class FormCustomerComponent {

  checkoutForm!: FormGroup;
  
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);

  @Input() customer!: Customer;
  @Output() customerOutput = new EventEmitter<Customer>; 
  @Output() closeForm = new EventEmitter<boolean>();

  closed: boolean = false;

  customerData: Customer = {
    id: 0,
    sharedKey: "", 
    businessId: "", 
    phone: "", 
    email: "", 
    startDate: new Date, 
    endDate: new Date
  };

  constructor(){
    this.checkoutForm = new FormGroup({
      'businessId': new FormControl(this.customerData.businessId, Validators.required),
      'phone': new FormControl(this.customerData.phone, Validators.required),
      'email': new FormControl(this.customerData.email, [Validators.required, Validators.email]),
      'startDate': new FormControl(this.customerData.startDate, Validators.required),
      'endDate': new FormControl(this.customerData.endDate, Validators.required)
    });
  }

  onEndDateChange(event: MatDatepickerInputEvent<Date>) {
    if (event.value) this.customerData.startDate = event.value;
  }
  onStartDateChange(event: MatDatepickerInputEvent<Date>) {
    if (event.value) this.customerData.endDate = event.value;;
  }

  onSubmit() {
    this.closed = true;
    this.closeForm.emit(this.closed)
  }

}
