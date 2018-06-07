import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {filter, tap} from 'rxjs/operators';
import {Subscription} from 'rxjs/Subscription';

import {User} from '../../models/user';
import * as fromRoot from '../../ngrx';
import * as fromCabinet from '../ngrx';
import * as cabinetActions from '../ngrx/cabinet.actions';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  users: User[];
  subscriptions: Subscription;

  constructor(protected store: Store<fromRoot.AppState>) { }

  ngOnInit() {
    this.users = [];
    this.subscriptions = this.store.select(fromCabinet.getUsersToBLock()).pipe(
      tap(t => {
        if (t == null) {
          this.store.dispatch(new cabinetActions.RequestUsersToBlockAction());
        }
      }),
      filter(t => t != null),
    ).subscribe((users: User[]) => {
      users.forEach(user => this.users.push(user));
    });
  }
}
