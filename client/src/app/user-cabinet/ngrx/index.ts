import {createSelector} from '@ngrx/store';
import {AppState} from '../../ngrx';
import * as fromCabinet from './cabinet.reducer';

export const getCabinetStateInit = (state: AppState) => state ? state.cabinet : fromCabinet.initialState;

export function getPetsState() {
    return createSelector(getCabinetStateInit, fromCabinet.getPets);
}

export function getUserState() {
    return createSelector(getCabinetStateInit, fromCabinet.getUser);
}

export function getRequestsState() {
    return createSelector(getCabinetStateInit, fromCabinet.getRequests);
}

export function getResponsesState() {
    return createSelector(getCabinetStateInit, fromCabinet.getResponses);
}

export function getNewRequests() {
    return createSelector(getCabinetStateInit, fromCabinet.geNewRequests);
}

export function getResponsesByRequests() {
    return createSelector(getCabinetStateInit, fromCabinet.getResponsesByRequest);
}

export function getUsersToBLock() {
    return createSelector(getCabinetStateInit, fromCabinet.getUsersToBlock);
}

export function getUserForLike() {
    return createSelector(getCabinetStateInit, fromCabinet.getUserForLike);
}

export function getAllUsers() {
    return createSelector(getCabinetStateInit, fromCabinet.getAllUsers);
}
