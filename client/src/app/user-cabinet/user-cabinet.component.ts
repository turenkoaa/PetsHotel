import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Store} from '@ngrx/store';
import {filter, take, tap} from 'rxjs/operators';
import {Subscription} from 'rxjs/Subscription';

import {User} from '../models/user';
import * as fromCabinet from '../user-cabinet/ngrx';
import * as cabinetActions from '../user-cabinet/ngrx/cabinet.actions';
import * as fromRoot from './../ngrx';
import {ActivatedRoute, Router} from '@angular/router';


@Component({
  selector: 'app-user-cabinet',
  templateUrl: './user-cabinet.component.html',
  styleUrls: ['./user-cabinet.component.css']
})
export class UserCabinetComponent implements OnInit {

  urlId: number;
  isOwner = false;
  usersReady = false;
  user: User;
  selectedUser = new FormControl();
  allUsers: User[];
  private subscription: Subscription;
  private subscriptions: Subscription;
  userSubscription: Subscription;

  constructor(
    protected store: Store<fromRoot.AppState>,
    private activateRoute: ActivatedRoute,
    private cd: ChangeDetectorRef,
    private router: Router) {
    }

  ngOnInit() {
    this.subscription = this.activateRoute.params.subscribe(params => {
      this.urlId = +params['id'];
      this.subscriptions = this.store.select(fromCabinet.getUserState()).pipe(
        filter(t => t != null),
        take(1)
      ).subscribe((user: User) => {
        this.user = user;
        if (user.id === this.urlId) {
          this.isOwner = true;
        } else {
          this.isOwner = false;
        }
      });
    });
    this.allUsers = [];

    this.userSubscription = this.store.select(fromCabinet.getAllUsers()).pipe(
      tap(t => {
        if (t == null) {
          this.store.dispatch(new cabinetActions.RequestAllUsersAction());
        }
      }),
      filter(t => t != null),
    ).subscribe((users: User[]) => {
      users.forEach(user =>
        this.allUsers.push(user)
      );
      this.usersReady = true;
      this.cd.markForCheck();
    });
  }
  userSelect(user: User) {
    this.router.navigateByUrl(`userCabinet/${user.id}`);
  }

}
