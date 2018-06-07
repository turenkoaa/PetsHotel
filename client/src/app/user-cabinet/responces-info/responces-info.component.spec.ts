import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ResponcesInfoComponent} from './responces-info.component';

describe('ResponcesInfoComponent', () => {
  let component: ResponcesInfoComponent;
  let fixture: ComponentFixture<ResponcesInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResponcesInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResponcesInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
