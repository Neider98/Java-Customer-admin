import { Component, OnInit } from '@angular/core';
import { SidebarCustomerComponent } from '../../shared/components/sidebar-customer/sidebar-customer.component';
import { TableCustomerComponent } from '../table-customer/table-customer.component';
import { AddCustomerComponent } from '../add-customer/add-customer.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import {MatGridListModule} from '@angular/material/grid-list';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { Customer } from '../../core/models/customer';
import { CustomerService } from '../../core/services/customer.service';
import { Subscription } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTableDataSource } from '@angular/material/table';
import { FormsModule } from '@angular/forms';
import { FormCustomerComponent } from "../../shared/components/form-customer/form-customer.component";

@Component({
    selector: 'app-home',
    standalone: true,
    providers: [CustomerService],
    templateUrl: './home.component.html',
    styleUrl: './home.component.css',
    imports: [
        SidebarCustomerComponent,
        TableCustomerComponent,
        AddCustomerComponent,
        MatSidenavModule,
        MatGridListModule,
        MatDialogModule,
        HttpClientModule,
        MatFormFieldModule,
        FormsModule,
        FormCustomerComponent
    ]
})
export class HomeComponent implements OnInit{

  advancedForm: boolean = false;

  searchTerm : string = "";
  customer!: Customer;
  customers: Customer [] = [];
  subscription: Subscription | undefined;
  filteredCustomers: Customer[] = [];

  constructor(public dialog: MatDialog, private customerService: CustomerService) {}

  ngOnInit(): void {
    this.subscription = this.customerService.getCustomers().subscribe({
      next: (customers: Customer[]) => {
        this.customers = customers;
        this.filteredCustomers = this.customers;
      },
      error: (error) => {
        console.error('Error al obtener los clientes:', error);
      }
    });
  }

  searchSharedKey() {
    this.filteredCustomers = this.customers.filter(customer => 
      customer.sharedKey.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(AddCustomerComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.customer = result;
      this.customerService.saveCustomer(this.customer);
    });
  }

  editCustomer($event: number) {
    this.customerService.getCustomerById($event).subscribe(
      (retrievedCustomer) => {
        if (retrievedCustomer !== undefined) {
          this.customer = retrievedCustomer;
          const dialogRef = this.dialog.open(AddCustomerComponent, {
            data: this.customer,
          });

          dialogRef.afterClosed().subscribe(result => {
            this.customer = result;
            this.customerService.editCustomer(this.customer);
          });
        }
      });
  }

  exportCsv() {
    this.customerService.exportCsv();
  }

  advancedSearch() {
    this.advancedForm = !this.advancedForm;
  }

  closeSearch($event: boolean) {
    this.advancedForm = $event;
  }
}
