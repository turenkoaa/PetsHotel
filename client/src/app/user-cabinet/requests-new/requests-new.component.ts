import {Component, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {filter, take, tap} from 'rxjs/operators';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../ngrx';
import * as fromCabinet from '../ngrx';
import * as cabinetActions from '../ngrx/cabinet.actions';
import {User} from '../../models/user';


@Component({
  selector: 'app-requests-new',
  templateUrl: './requests-new.component.html',
  styleUrls: ['./requests-new.component.css']
})
export class RequestsNewComponent implements OnInit {

  subscriptions: Subscription;
  requestSubscription: Subscription;
  user: User;
  requests: Request[];
  constructor(protected store: Store<fromRoot.AppState>) { }


  ngOnInit() {
    this.requests = [];
    this.store.dispatch(new cabinetActions.GetNewRequestsSuccessAction(null));
    this.subscriptions = this.store.select(fromCabinet.getUserState()).pipe(
      filter(t => t != null),
      take(1)
    ).subscribe((user: User) => {
      this.user = user;
    });

    this.requestSubscription = this.store.select(fromCabinet.getNewRequests()).pipe(
      tap(t => {
        if (t == null) {
          this.store.dispatch(new cabinetActions.RequestNewRequestsAction(this.user.id));
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
    this.store.dispatch(new cabinetActions.RequestNewRequestsAction(this.user.id));
  }

}
