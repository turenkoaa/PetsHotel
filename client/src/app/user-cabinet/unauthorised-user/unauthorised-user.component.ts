import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {filter, take, tap} from 'rxjs/operators';
import {Subscription} from 'rxjs/Subscription';

import {User} from '../../models/user';
import * as fromRoot from '../../ngrx';
import * as fromCabinet from '../ngrx';
import * as cabinetActions from '../ngrx/cabinet.actions';
import {FormControl} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-unauthorised-user',
  templateUrl: './unauthorised-user.component.html',
  styleUrls: ['./unauthorised-user.component.css']
})
export class UnauthorisedUserComponent implements OnInit {

  user: User;
  subscriptions: Subscription;
  cliked = false;
  comment = new FormControl();

  urlId: number;
  private subscription: Subscription;

  constructor(private activateRoute: ActivatedRoute, protected store: Store<fromRoot.AppState>) {
    this.subscription = activateRoute.params.subscribe(params => {
      this.urlId = params['id'];
      this.store.dispatch(new cabinetActions.GetUserForLikeSuccessAction(null));
      this.subscriptions = this.store.select(fromCabinet.getUserForLike()).pipe(
        tap(t => {
          this.store.dispatch(new cabinetActions.RequestUserForLikeAction(this.urlId));
        }),
        filter(t => t != null),
        take(1)
      ).subscribe((user: User) => {
        this.user = user;
      });
    });
    console.log(this.urlId);
  }

  ngOnInit() {
  }

  onLike() {
    this.store.dispatch(new cabinetActions.RequestSaveReviewAction({
      comment: this.comment.value,
      like: 1,
      userId: this.user.id
    }));
    this.cliked = true;
  }

  onDislike() {
    this.store.dispatch(new cabinetActions.RequestSaveReviewAction({
      comment: this.comment.value,
      like: 0,
      userId: this.user.id
    }));
    this.cliked = true;
  }

}
