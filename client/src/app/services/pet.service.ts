import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import * as fromRoot from '../ngrx';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {Item} from '../models/item';
import {of} from 'rxjs/observable/of';

@Injectable()
export class PetsService {

  items: Item[];
  list = [0, 1, 2];

  constructor(protected store: Store<fromRoot.AppState>, protected http: HttpClient) { }

  getItems(): Observable<Item[]> {
    this.items = [];
    // tslint:disable-next-line:forin
    for ( const i in this.list) {
      this.items.push({
        name: `Pets ${i}`,
        description: `Description ${i}`
      });
    }
    return of(this.items);
  }
}
