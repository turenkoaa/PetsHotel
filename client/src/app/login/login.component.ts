import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Store} from '@ngrx/store';
import {filter, take, tap} from 'rxjs/operators';
import {Subscription} from 'rxjs/Subscription';

import {User} from '../models/user';
import * as fromCabinet from '../user-cabinet/ngrx';
import * as cabinetActions from '../user-cabinet/ngrx/cabinet.actions';
import * as fromRoot from './../ngrx';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email = new FormControl();
  password = new FormControl();
  user: User;
  subscription: Subscription;
  constructor(protected store: Store<fromRoot.AppState>,  private router: Router) { }

  ngOnInit() {
  }

  onLogin() {
    if (this.email.value) {
      if (this.email.value === 'admin@admin.com') {
        this.router.navigateByUrl(`admin`);
      }
      this.subscription = this.store.select(fromCabinet.getUserState()).pipe(
        tap(t => {
          if (t == null) {
            this.store.dispatch(new cabinetActions.RequsetUserByIdAction(this.email.value));
          }
        }),
        filter(t => t != null),
        take(1)
      ).subscribe((user: User) => {
        this.user = user;
        this.router.navigateByUrl(`userCabinet/${this.user.id}`);
      });
    }
  }

  onBasic() {
    this.router.navigateByUrl(`userCabinet/1`);
  }

}
