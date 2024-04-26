import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormCustomerComponent } from './form-customer.component';

describe('FormCustomerComponent', () => {
  let component: FormCustomerComponent;
  let fixture: ComponentFixture<FormCustomerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormCustomerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FormCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
