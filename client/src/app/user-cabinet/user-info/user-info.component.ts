import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {filter, take} from 'rxjs/operators';
import {Subscription} from 'rxjs/Subscription';

import {User} from '../../models/user';
import * as fromRoot from '../../ngrx';
import * as fromCabinet from '../ngrx';
import {ActivatedRoute} from '@angular/router';


@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {

  user: User;
  urlId: number;
  subscriptions: Subscription;
  private subscription: Subscription;

  constructor(protected store: Store<fromRoot.AppState>, private activateRoute: ActivatedRoute) {
    this.subscription = activateRoute.params.subscribe(params => this.urlId = params['id']);
    console.log(this.urlId);
  }

  ngOnInit() {
    this.subscriptions = this.store.select(fromCabinet.getUserState()).pipe(
      filter(t => t != null),
      take(1)
    ).subscribe((user: User) => {
      this.user = user;
    });
  }

}
