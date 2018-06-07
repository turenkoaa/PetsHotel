import {Component, Input, OnInit} from '@angular/core';
import {User} from '../../models/user';

import * as fromRoot from '../../ngrx';
import * as cabinetActions from '../ngrx/cabinet.actions';
import {Store} from '@ngrx/store';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  @Input() user: User;
  @Input() isAdmin: boolean;

  constructor(protected store: Store<fromRoot.AppState>) { }

  ngOnInit() {
  }

  blockUser() {
    this.store.dispatch(new cabinetActions.RequsetBlockUserAction(this.user.id));
  }

}
