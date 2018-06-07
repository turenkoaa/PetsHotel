import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UnauthorisedUserComponent} from './unauthorised-user.component';

describe('UnauthorisedUserComponent', () => {
  let component: UnauthorisedUserComponent;
  let fixture: ComponentFixture<UnauthorisedUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnauthorisedUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnauthorisedUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
