import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {filter, take, tap} from 'rxjs/operators';
import {Subscription} from 'rxjs/Subscription';
import {Response} from '../../models/response';

import * as fromRoot from '../../ngrx';
import * as fromCabinet from '../ngrx';
import * as cabinetActions from '../ngrx/cabinet.actions';
import {User} from '../../models/user';

@Component({
  selector: 'app-responces-info',
  templateUrl: './responces-info.component.html',
  styleUrls: ['./responces-info.component.css']
})
export class ResponcesInfoComponent implements OnInit {

  responses: Response[];
  subscriptions: Subscription;
  responsesSubscription: Subscription;
  user: User;
  constructor(protected store: Store<fromRoot.AppState>, private cd: ChangeDetectorRef) { }

  ngOnInit() {
    this.responses = [];
    this.subscriptions = this.store.select(fromCabinet.getUserState()).pipe(
      filter(t => t != null),
      take(1)
    ).subscribe((user: User) => {
      this.user = user;
    });
    this.store.dispatch(new cabinetActions.GetResponsesSuccess(null));
    this.responsesSubscription = this.store.select(fromCabinet.getResponsesState()).pipe(
      tap(t => {
        if (t == null) {
          this.store.dispatch(new cabinetActions.RequestResponsesAction(this.user.id));
        }
      }),
      filter(t => t != null)
    )
      .subscribe((responses: Response[]) => {
        this.responses = [];
        responses.forEach(item =>
          this.responses.push(item));
          console.log('[Respnses-info] responses added to array');
      });
  }

  refresh() {
    this.store.dispatch(new cabinetActions.RequestResponsesAction(this.user.id));
  }

}
