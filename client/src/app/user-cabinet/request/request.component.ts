import {Component, Input, OnInit} from '@angular/core';
import {Request} from '../../models/request';

import * as fromRoot from '../../ngrx';
import * as fromCabinet from '../ngrx';
import * as cabinetActions from '../ngrx/cabinet.actions';
import {Store} from '@ngrx/store';
import {User} from '../../models/user';
import {Subscription} from 'rxjs/Subscription';
import {filter} from 'rxjs/operators/filter';
import {take} from 'rxjs/operators/take';

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrls: ['./request.component.css']
})
export class RequestComponent implements OnInit {

  @Input() request: Request;
  @Input() isNew: boolean;
  blockedUser = true;
  user: User;
  showResponse = false;
  rejected = false;
  subscriptions: Subscription;

  constructor(protected store: Store<fromRoot.AppState>) { }

  ngOnInit() {
    if ( this.request.status === 'REJECTED') {
      this.rejected = true;
    }
    this.subscriptions = this.store.select(fromCabinet.getUserState()).pipe(
      filter(t => t != null),
      take(1)
    ).subscribe((user: User) => {
      this.user = user;
      if (user.active) {
        this.blockedUser = false;
      }
    });
  }

  showResponces() {
    this.showResponse = !this.showResponse;
  }

  cancelRequest() {
    this.store.dispatch(new cabinetActions.RequestCancelRequest(this.request.id));
  }

  createResponse() {
    this.store.dispatch(new cabinetActions.RequestCreateResponseAction({
      requestId: this.request.id,
      authorId: this.user.id,
      cost: this.request.cost
    }));
  }

  rejectAll() {
    this.store.dispatch(new cabinetActions.RequestRejectAllAction(this.request.id));
  }
}
