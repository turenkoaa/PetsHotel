import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PetsFeedComponent} from './pets-feed.component';

describe('PetsFeedComponent', () => {
  let component: PetsFeedComponent;
  let fixture: ComponentFixture<PetsFeedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PetsFeedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PetsFeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
