import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Pet} from '../../models/pet';
import {FormControl} from '@angular/forms';
import {Store} from '@ngrx/store';

import * as fromRoot from '../../ngrx';
import * as fromCabinet from '../ngrx';
import * as cabinetActions from '../ngrx/cabinet.actions';
import {Subscription} from 'rxjs/Subscription';
import {User} from '../../models/user';
import {filter, take} from 'rxjs/operators';

@Component({
  selector: 'app-pet',
  templateUrl: './pet.component.html',
  styleUrls: ['./pet.component.css']
})
export class PetComponent implements OnInit, OnChanges {

  @Input() pet: Pet;
  show = false;
  subscriptions: Subscription;
  user: User;
  startDate = new FormControl();
  endDate = new FormControl();
  cost = new FormControl();

  constructor(protected store: Store<fromRoot.AppState>) { }

  ngOnInit() {
    this.subscriptions = this.store.select(fromCabinet.getUserState()).pipe(
      filter(t => t != null),
      take(1)
    ).subscribe((user: User) => {
      this.user = user;
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if ('pet' in changes && changes.pet.currentValue) {
      this.show = true;
    }
  }

  addRequest() {
    this.store.dispatch(new cabinetActions.RequestCreateRequestAction({
      userId: this.user.id,
      startDate: this.startDate.value,
      endDate: this.endDate.value,
      petsId: this.pet.id,
      cost: this.cost.value
    }));

  }
}
