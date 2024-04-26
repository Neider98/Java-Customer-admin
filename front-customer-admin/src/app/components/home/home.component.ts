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
    this.getCustomers();
  }

  getCustomers() {
    this.subscription = this.customerService.getCustomers().subscribe({
      next: (customers: Customer[]) => {
        console.log(customers)
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

  searhAdvancedsharedKey(
    
  ) {}

  openDialog(): void {
    const dialogRef = this.dialog.open(AddCustomerComponent);
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('The dialog was closed with result:', result);
        const response = this.customerService.saveCustomer(result);
        response.subscribe(savedCustomer => {
          console.log('Cliente guardado exitosamente:', savedCustomer);
          this.getCustomers();
        }, error => {
          console.error('Error al guardar el cliente:', error);
        });
      } else {
        console.log('El diálogo fue cerrado sin un resultado válido.');
      }
    });
  }

  editCustomer($event: number) {
    this.customerService.getCustomerById($event).subscribe({
      next: (retrievedCustomer) => {
        if (retrievedCustomer !== undefined) {
          this.customer = retrievedCustomer;
          console.log(this.customer);
          const dialogRef = this.dialog.open(AddCustomerComponent, {
            data: this.customer,
          });
  
          dialogRef.afterClosed().subscribe({
            next: (result) => {
              console.log(this.customer);
              if (result !== undefined) {
                this.customer = result;
                this.customerService.editCustomer(this.customer).subscribe({
                  next: (updatedCustomer) => {
                    console.log('Cliente editado exitosamente', updatedCustomer);
                    this.getCustomers();
                  },
                  error: (error) => {
                    console.error('Error al editar el cliente:', error);
                  }
                });
              }
            }
          });
        }
      },
      error: (error) => {
        console.error('Error al obtener el cliente:', error);
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
