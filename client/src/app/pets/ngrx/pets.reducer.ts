import {Item} from '../../models/item';
import * as petsActions from './pets.actions';

export interface PetsState {
    pets: Item[];
}

export const initialState: PetsState = {
    pets: [],
};

export function reducer(state = initialState, action: petsActions.PetsActions): PetsState {
    const actionType = !action ? '' : action.type;
    switch (actionType) {

        case petsActions.GET_ITEMS_SUCCESS:
            return {
                ...state,
                pets: <Item[]>action.payload
            };
        default:
            return state;
    }
}

export const getPets = (state: PetsState) => state.pets;
