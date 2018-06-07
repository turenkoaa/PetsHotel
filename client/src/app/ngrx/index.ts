import {ActionReducer, ActionReducerMap, MetaReducer} from '@ngrx/store';
import {storeFreeze} from 'ngrx-store-freeze';
import * as fromPets from './../pets/ngrx/pets.reducer';
import * as fromCabinet from './../user-cabinet/ngrx/cabinet.reducer';

export interface AppState {
    pets: fromPets.PetsState;
    cabinet: fromCabinet.CabinetState;
}

export const reducers: ActionReducerMap<AppState> = {
    pets: fromPets.reducer,
    cabinet: fromCabinet.reducer,
};

export function logger(reducer: ActionReducer<AppState>): ActionReducer<any, any> {
    return function (state: AppState, action: any): AppState {
        console.log('state', state);
        console.log('action', action);

        return reducer(state, action);
    };
}

/**
 * By default, @ngrx/store uses combineReducers with the reducer map to compose
 * the root meta-reducer. To add more meta-reducers, provide an array of meta-reducers
 * that will be composed to form the root meta-reducer.
 */
export const metaReducers: MetaReducer<AppState>[] = [logger, storeFreeze];
