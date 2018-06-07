import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PetsInfoComponent} from './pets-info.component';

describe('PetsInfoComponent', () => {
  let component: PetsInfoComponent;
  let fixture: ComponentFixture<PetsInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PetsInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PetsInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
