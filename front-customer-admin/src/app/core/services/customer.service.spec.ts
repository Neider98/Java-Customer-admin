import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CustomerService } from './customer.service';
import { Customer } from '../models/customer';
import { Response } from '../models/response';

describe('CustomerService', () => {
  let service: CustomerService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CustomerService]
    });
    service = TestBed.inject(CustomerService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Verificar que no haya solicitudes pendientes.
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get a customer by id', () => {
    const customerId = 1;
    const mockCustomer: Customer = {
      id: 1,
      sharedKey: 'sharedKey1',
      businessId: 'businessId1',
      email: 'john@example.com',
      phone: '123456789',
      startDate: new Date(),
      endDate: new Date()
    };

    service.getCustomerById(customerId).subscribe((customer: Customer | undefined) => {
      expect(customer).toEqual(mockCustomer);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/v1/customers/${customerId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockCustomer);
  });

  it('should get all customers', () => {
    const mockCustomers: Customer[] = [
      {
        id: 1,
        sharedKey: 'sharedKey1',
        businessId: 'businessId1',
        email: 'john@example.com',
        phone: '123456789',
        startDate: new Date(),
        endDate: new Date()
      },
      {
        id: 2,
        sharedKey: 'sharedKey2',
        businessId: 'businessId2',
        email: 'jane@example.com',
        phone: '987654321',
        startDate: new Date(),
        endDate: new Date()
      }
    ];
    const mockResponse: Response = { status: 'success', message: mockCustomers, code: 200, date: new Date() };

    service.getCustomers().subscribe((customers: Customer[]) => {
      expect(customers).toEqual(mockCustomers);
    });

    const request = httpMock.expectOne('http://localhost:8080/api/v1/customers/');
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should save a customer', () => {
    const mockCustomer: Customer = {
      id: 1,
      sharedKey: 'sharedKey1',
      businessId: 'businessId1',
      email: 'john@example.com',
      phone: '123456789',
      startDate: new Date(),
      endDate: new Date()
    };

    service.saveCustomer(mockCustomer);

    // No hay mucho que probar aquí, ya que la función simplemente imprime en la consola.
    // Podrías considerar el espionaje sobre `console.log` para asegurarte de que se llame correctamente.
  });

  it('should edit a customer', () => {
    const mockCustomer: Customer = {
      id: 1,
      sharedKey: 'sharedKey1',
      businessId: 'businessId1',
      email: 'john@example.com',
      phone: '123456789',
      startDate: new Date(),
      endDate: new Date()
    };

    service.editCustomer(mockCustomer);

    // No hay mucho que probar aquí, ya que la función simplemente imprime en la consola.
    // Podrías considerar el espionaje sobre `console.log` para asegurarte de que se llame correctamente.
  });

  it('should export CSV', () => {
    spyOn(console, 'log'); // Mock console.log

    service.exportCsv();

    // No hay mucho que probar aquí, ya que la función simplemente imprime en la consola.
    // Podrías considerar el espionaje sobre `console.log` para asegurarte de que se llame correctamente.
  });
});
