import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CustomerService } from './customer.service';
import { Customer } from '../models/customer';

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
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getCustomerById', () => {
    it('should retrieve a customer by ID', () => {
      const dummyCustomer: Customer = {
        id: 1,
        sharedKey: 'sharedKey1',
        businessId: 'businessId1',
        email: 'email1@example.com',
        phone: 'phone1',
        startDate: new Date(),
        endDate: new Date(),
        dateAdded: new Date()
      };

      service.getCustomerById(1).subscribe(customer => {
        expect(customer).toEqual(dummyCustomer);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/customers/1');
      expect(req.request.method).toBe('GET');
      req.flush({ status: 'success', message: [dummyCustomer], code: 200, date: new Date() });
    });

    it('should handle error when retrieving a customer by ID', () => {
      service.getCustomerById(1).subscribe(customer => {
        expect(customer).toBeUndefined();
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/customers/1');
      expect(req.request.method).toBe('GET');
      req.error(new ErrorEvent('Error fetching customer'));
    });
  });

  describe('getCustomers', () => {
    it('should retrieve customers', () => {
      const dummyCustomers: Customer[] = [
        {
          id: 1,
          sharedKey: 'sharedKey1',
          businessId: 'businessId1',
          email: 'email1@example.com',
          phone: 'phone1',
          startDate: new Date(),
          endDate: new Date(),
          dateAdded: new Date()
        },
        {
          id: 2,
          sharedKey: 'sharedKey2',
          businessId: 'businessId2',
          email: 'email2@example.com',
          phone: 'phone2',
          startDate: new Date(),
          endDate: new Date(),
          dateAdded: new Date()
        }
      ];

      service.getCustomers().subscribe(customers => {
        expect(customers).toEqual(dummyCustomers);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/customers/');
      expect(req.request.method).toBe('GET');
      req.flush({ status: 'success', message: dummyCustomers, code: 200, date: new Date() });
    });
  });

  describe('saveCustomer', () => {
    it('should save a customer', () => {
      const newCustomer: Customer = {
        id: 0,
        sharedKey: 'newSharedKey',
        businessId: 'newBusinessId',
        email: 'newEmail@example.com',
        phone: 'newPhone',
        startDate: new Date(),
        endDate: new Date(),
        dateAdded: new Date()
      };

      service.saveCustomer(newCustomer).subscribe(customer => {
        expect(customer).toEqual(newCustomer);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/customers');
      expect(req.request.method).toBe('POST');
      req.flush(newCustomer);
    });
  });

  describe('editCustomer', () => {
    it('should edit a customer', () => {
      const updatedCustomer: Customer = {
        id: 1,
        sharedKey: 'updatedSharedKey',
        businessId: 'updatedBusinessId',
        email: 'updatedEmail@example.com',
        phone: 'updatedPhone',
        startDate: new Date(),
        endDate: new Date(),
        dateAdded: new Date()
      };

      service.editCustomer(updatedCustomer).subscribe(customer => {
        expect(customer).toEqual(updatedCustomer);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/customers/1');
      expect(req.request.method).toBe('PUT');
      req.flush(updatedCustomer);
    });
  });
});
