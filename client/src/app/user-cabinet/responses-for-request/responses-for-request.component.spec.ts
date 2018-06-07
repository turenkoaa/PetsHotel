import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ResponsesForRequestComponent} from './responses-for-request.component';

describe('ResponsesForRequestComponent', () => {
  let component: ResponsesForRequestComponent;
  let fixture: ComponentFixture<ResponsesForRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResponsesForRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResponsesForRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
