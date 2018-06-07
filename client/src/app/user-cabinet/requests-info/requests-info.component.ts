import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {filter, take, tap} from 'rxjs/operators';
import {Subscription} from 'rxjs/Subscription';

import * as fromRoot from '../../ngrx';
import * as fromCabinet from '../ngrx';
import * as cabinetActions from '../ngrx/cabinet.actions';
import {User} from '../../models/user';

@Component({
  selector: 'app-requests-info',
  templateUrl: './requests-info.component.html',
  styleUrls: ['./requests-info.component.css']
})
export class RequestsInfoComponent implements OnInit {

  user: User;
  subscriptions: Subscription;
  requestsSubscription: Subscription;
  requests: Request[];
  constructor(protected store: Store<fromRoot.AppState>) { }

  ngOnInit() {
    this.requests = [];
    this.store.dispatch(new cabinetActions.GetRequestsSuccessAction(null));

    this.subscriptions = this.store.select(fromCabinet.getUserState()).pipe(
      filter(t => t != null),
      take(1)
    ).subscribe((user: User) => {
      this.user = user;
    });
    this.requestsSubscription = this.store.select(fromCabinet.getRequestsState()).pipe(
      tap(t => {
        if (t == null) {
          this.store.dispatch(new cabinetActions.RequestRequsetsAction(this.user.id));
        }
      }),
      filter(t => t != null)
    )
      .subscribe((requests: Request[]) => {
        this.requests = [];
        requests.forEach(item =>
          this.requests.push(item)
        );
      });
  }

  refresh() {
    this.store.dispatch(new cabinetActions.RequestRequsetsAction(this.user.id));
  }
}
