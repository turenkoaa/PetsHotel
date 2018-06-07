import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RequestsInfoComponent} from './requests-info.component';

describe('RequestsInfoComponent', () => {
  let component: RequestsInfoComponent;
  let fixture: ComponentFixture<RequestsInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RequestsInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestsInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
