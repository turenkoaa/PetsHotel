import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from '@ngrx/effects';
import {map, switchMap} from 'rxjs/operators';
import {Action, Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {PetsService} from '../../services/pet.service';


import * as fromRoot from '../../ngrx';
import * as petsActions from './pets.actions';
import {Item} from '../../models/item';

@Injectable()
export class PetsEffects {
    constructor(private actions$: Actions, private petsService: PetsService, private store: Store<fromRoot.AppState>) {
    }

    @Effect()
    requestGetItems$: Observable<Action> = this.actions$.pipe(
        ofType(petsActions.REQUEST_GET_ITEMS),
        switchMap((action: petsActions.RequestGetItemsAction) => {
            return this.petsService.getItems();
        }),
        map((pets: Item[]) => new petsActions.GetItemsSuccessAction(pets))
    );
}
