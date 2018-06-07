import {Component, OnInit} from '@angular/core';
import {Item} from '../../models/item';
import * as fromRoot from '../../ngrx';
import * as fromPets from '../ngrx';
import * as petsActions from '../ngrx/pets.actions';
import {Store} from '@ngrx/store';
import {Subscription} from 'rxjs/Subscription';
import {filter, tap} from 'rxjs/operators';

@Component({
  selector: 'app-pets-feed',
  templateUrl: './pets-feed.component.html',
  styleUrls: ['./pets-feed.component.css']
})
export class PetsFeedComponent implements OnInit {

  items: Item[];
  subscriptions: Subscription;

  constructor(protected store: Store<fromRoot.AppState>) { }

  ngOnInit() {
    this.items = [];
    this.store.dispatch(new petsActions.GetItemsSuccessAction(null));
    this.subscriptions = this.store.select(fromPets.getPetsState()).pipe(
      tap( t => {
        if (t == null) {
          this.store.dispatch(new petsActions.RequestGetItemsAction());
        }
      }),
      filter( t => t != null)
    )
    .subscribe((items: Item[]) => {
      items.forEach(item => {
        this.items.push(item);
      });
    });
  }

  addItem() {
    this.items.push({
      name: `Pets`,
      description: `Description`
    });
  }

}
