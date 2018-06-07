import {createSelector} from '@ngrx/store';
import {AppState} from '../../ngrx';
import * as fromPets from './pets.reducer';

export const getPetsStateInit = (state: AppState) => state ? state.pets : fromPets.initialState;

export function getPetsState() {
    return createSelector(getPetsStateInit, fromPets.getPets);
}
