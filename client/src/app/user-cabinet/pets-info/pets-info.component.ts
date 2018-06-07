import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {filter, take, tap} from 'rxjs/operators';
import {Subscription} from 'rxjs/Subscription';

import {Pet} from '../../models/pet';
import * as fromRoot from '../../ngrx';
import * as fromCabinet from '../ngrx';
import * as cabinetActions from '../ngrx/cabinet.actions';
import {User} from '../../models/user';

@Component({
  selector: 'app-pets-info',
  templateUrl: './pets-info.component.html',
  styleUrls: ['./pets-info.component.css']
})
export class PetsInfoComponent implements OnInit {

  subscriptions: Subscription;
  petSubscription: Subscription;
  pets: Pet[];
  user: User;
  constructor(protected store: Store<fromRoot.AppState>, private cd: ChangeDetectorRef) { }

  ngOnInit() {
    this.pets = [];
    this.store.dispatch(new cabinetActions.GetPetsSuccessAction(null));
    this.subscriptions = this.store.select(fromCabinet.getUserState()).pipe(
      filter(t => t != null),
      take(1)
    ).subscribe((user: User) => {
      this.user = user;
    });

    this.petSubscription = this.store.select(fromCabinet.getPetsState()).pipe(
      tap(t => {
        if (t == null) {
          this.store.dispatch(new cabinetActions.RequestGetPetsAction(this.user.id));
        }
      }),
      filter(t => t != null),
      take(1)
    )
      .subscribe((pets: Pet[]) => {
        pets.forEach(item => {
          this.pets.push(item);
        });
        this.cd.markForCheck();
      });
  }

  refresh() {
    this.store.dispatch(new cabinetActions.RequestGetPetsAction(this.user.id));
    this.cd.markForCheck();
  }
}

