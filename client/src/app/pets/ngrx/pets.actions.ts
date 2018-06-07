import {Action} from '@ngrx/store';
import {Item} from '../../models/item';

export const REQUEST_GET_ITEMS = '[Pets] Request get items';
export const GET_ITEMS_SUCCESS = '[Pets] Get items success';

export class RequestGetItemsAction implements Action {
    readonly type = REQUEST_GET_ITEMS;

    constructor() { }
}

export class GetItemsSuccessAction implements Action {
    readonly type = GET_ITEMS_SUCCESS;

    constructor(public payload: Item[]) { }
}

export type PetsActions =
| GetItemsSuccessAction;
