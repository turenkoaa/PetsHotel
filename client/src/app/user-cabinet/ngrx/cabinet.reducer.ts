import * as cabinetActions from './cabinet.actions';
import {Pet} from '../../models/pet';
import {User} from '../../models/user';
import {Response} from '../../models/response';

export interface CabinetState {
    pets: Pet[];
    user: User;
    requests: Request[];
    responses: Response[];
    newRequests: Request[];
    userForLike: User;
    responsesByRequest: Response[];
    usersToBlock: User[];
    allUsers: User[];
}

export const initialState: CabinetState = {
    pets: [],
    user: null,
    requests: [],
    responses: [],
    newRequests: [],
    responsesByRequest: [],
    usersToBlock: null,
    userForLike: null,
    allUsers: null,
};

export function reducer(state = initialState, action: cabinetActions.CabinetActions): CabinetState {
    const actionType = !action ? '' : action.type;
    switch (actionType) {

        case cabinetActions.GET_PETS_SUCCESS:
            // console.log('reduces get pets');
            return {
                ...state,
                pets: <Pet[]>action.payload
            };

        case cabinetActions.GET_USER_BY_ID_SUCCESS:
            return {
                ...state,
                user: <User>action.payload
            };

        case cabinetActions.GET_REQUESTS_SUCCESS:
            return {
                ...state,
                requests: <Request[]>action.payload
            };

        case cabinetActions.GET_RESPONSES_SUCCESS:
            return {
                ...state,
                responses: <Response[]>action.payload
            };

        case cabinetActions.GET_NEW_REQUESTS_SUCCESS:
            return {
                ...state,
                newRequests: <Request[]>action.payload
            };

        case cabinetActions.GET_RESPONSES_BY_REQUEST_SUCCESS:
            return {
                ...state,
                responsesByRequest: <Response[]>action.payload
            };

        case cabinetActions.GET_USERS_TO_BLOCK_SUCCESS:
            return {
                ...state,
                usersToBlock: <User[]>action.payload
            };

        case cabinetActions.GET_USER_FOR_LIKE_SUCCESS:
            return {
                ...state,
                userForLike: <User>action.payload
            };

        case cabinetActions.GET_ALL_USERS_SUCCESS:
            return {
                ...state,
                allUsers: <User[]>action.payload
            };

        default:
            return state;
    }
}

export const getPets = (state: CabinetState) => state.pets;
export const getUser = (state: CabinetState) => state.user;
export const getRequests = (state: CabinetState) => state.requests;
export const getResponses = (state: CabinetState) => state.responses;
export const geNewRequests = (state: CabinetState) => state.newRequests;
export const getResponsesByRequest = (state: CabinetState) => state.responsesByRequest;
export const getUsersToBlock = (state: CabinetState) => state.usersToBlock;
export const getUserForLike = (state: CabinetState) => state.userForLike;
export const getAllUsers = (state: CabinetState) => state.allUsers;
