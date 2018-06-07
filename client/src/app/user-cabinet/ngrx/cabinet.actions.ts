import {Action} from '@ngrx/store';
import {Pet} from '../../models/pet';
import {User} from '../../models/user';
import {Response} from '../../models/response';

export const REQUEST_GET_PETS = '[Cabinet] Request get pets';
export const GET_PETS_SUCCESS = '[Cabinet] Get pets success';
export const REQUEST_USER_BY_ID = '[Cabinet] Request user by email';
export const GET_USER_BY_ID_SUCCESS = '[Cabinet] Get user by email success';
export const REQUEST_REQUESTS = '[Cabinet] Request requests';
export const GET_REQUESTS_SUCCESS = '[Cabinet] Get requests success';
export const REQUEST_RESPONSES = '[Cabinet] Request responses';
export const GET_RESPONSES_SUCCESS = '[Cabinet] Get responses success';
export const REQUEST_NEW_REQUESTS = '[Cabinet] Request new requests';
export const GET_NEW_REQUESTS_SUCCESS = '[Cabinet] Get new requsts success';
export const REQUEST_RESPONSES_BY_REQUEST = '[Cabinet] Requset responses by request';
export const GET_RESPONSES_BY_REQUEST_SUCCESS = '[Cabinet] Get responses by request success';
export const REQUST_USERS_TO_BLOCK = '[Admin] Requset users to block';
export const GET_USERS_TO_BLOCK_SUCCESS = '[Admin] Get users to block success';
export const REQUSET_SAVE_REVIEW = '[Admin] Request save review';
export const GET_SAVED_REVIEW_SUCCESS = '[Admin] Saved review success';
export const REQUEST_TO_BLOCK_USER = '[Admin] Request to block user';
export const GET_BLOCK_USER_SUCCESS = '[Admin] Block user success';
export const REQUEST_CANCEL_REQUEST = '[Admin] Request cancel request';
export const GET_CALCEL_REQUEST_SUCCESS = '[Admin] Cancel request success';
export const REQUEST_CREATE_RESPONSE = '[Admin] Request create response';
export const GET_CREATE_RESPONSE_SUCCESS = '[Admin] Get create response success';
export const REQUEST_USER_FOR_LIKE = '[Unauthorised] Request user for like';
export const GET_USER_FOR_LIKE_SUCCESS = '[UnAuthorised] Get user for like success';
export const REQUEST_ALL_USERS = '[Cabinet] Request all users';
export const GET_ALL_USERS_SUCCESS = '[Cabinet] Get all users success';
export const REQUEST_CREATE_REQUEST = '[Cabinet] Request create request';
export const GET_CREATE_REQUEST_SUCCESS = '[Cabinet] Get create request success';
export const REQUEST_ACCEPT_RESPONSE = '[Cabinet] Request accept response';
export const GET_ACCEPT_RESPONSE_SUCCESS = '[Cabinet] Get accept response success';
export const REQUEST_SEND_PAY = '[Cabinet] Request send pay';
export const GET_SEND_PAY_SUCCESS = '[Cabinet] Get send pay success';
export const REQUEST_REJECT_ALL = '[Cabinet] Request reject all';
export const GET_REJECT_ALL = '[Cabinet] Get reject all';

export class RequestGetPetsAction implements Action {
    readonly type = REQUEST_GET_PETS;

    constructor(public payload: number) { }
}

export class GetPetsSuccessAction implements Action {
    readonly type = GET_PETS_SUCCESS;

    constructor(public payload: Pet[]) { }
}

export class RequsetUserByIdAction implements Action {
    readonly type = REQUEST_USER_BY_ID;

    constructor(public payload: string) { }
}

export class GetUserByIdSuccessAction implements Action {
    readonly type = GET_USER_BY_ID_SUCCESS;

    constructor(public payload: User) { }
}

export class RequestRequsetsAction implements Action {
    readonly type = REQUEST_REQUESTS;

    constructor(public payload: number) { }
}

export class GetRequestsSuccessAction implements Action {
    readonly type = GET_REQUESTS_SUCCESS;

    constructor(public payload: Request[]) { }
}

export class RequestResponsesAction implements Action {
    readonly type = REQUEST_RESPONSES;

    constructor(public payload: number) { }
}

export class GetResponsesSuccess implements Action {
    readonly type = GET_RESPONSES_SUCCESS;

    constructor(public payload: Response[]) { }
}

export class RequestNewRequestsAction implements Action {
    readonly type = REQUEST_NEW_REQUESTS;

    constructor(public payload: number) { }
}

export class GetNewRequestsSuccessAction implements Action {
    readonly type = GET_NEW_REQUESTS_SUCCESS;

    constructor(public payload: Request[]) { }
}

export class RequestResponseByrequestAction implements Action {
    readonly type = REQUEST_RESPONSES_BY_REQUEST;

    constructor(public payload: number) { }
}

export class GetResponsesByRequestSuccessAction implements Action {
    readonly type = GET_RESPONSES_BY_REQUEST_SUCCESS;

    constructor(public payload: Response[]) { }

}

export class RequestUsersToBlockAction implements Action {
    readonly type = REQUST_USERS_TO_BLOCK;

    constructor() { }
}

export class GetUsersToBlockSuccessAction implements Action {
    readonly type = GET_USERS_TO_BLOCK_SUCCESS;

    constructor(public payload: User[]) { }
}

export class RequestSaveReviewAction implements Action {
    readonly type = REQUSET_SAVE_REVIEW;

    constructor(public payload: {comment: string, like: number, userId: number}) { }
}

export class GetSavedReviewSuccessAction implements Action {
    readonly type = GET_SAVED_REVIEW_SUCCESS;

    constructor() { }
}

export class RequsetBlockUserAction implements Action {
    readonly type = REQUEST_TO_BLOCK_USER;

    constructor(public payload: number) { }
}

export class GetBlockUserSuccessAction implements Action {
    readonly type = GET_BLOCK_USER_SUCCESS;

    constructor() { }
}

export class RequestCancelRequest implements Action {
    readonly type = REQUEST_CANCEL_REQUEST;

    constructor(public payload: number) { }
}

export class GetCancelRequestSuccess implements Action {
    readonly type = GET_CALCEL_REQUEST_SUCCESS;

    constructor() { }
}

export class RequestCreateResponseAction implements Action {
    readonly type = REQUEST_CREATE_RESPONSE;

    constructor(public payload: {requestId: number, authorId: number, cost: number}) { }
}

export class GetCreateResponseSuccessAction implements Action {
    readonly type = GET_CREATE_RESPONSE_SUCCESS;

    constructor() { }
}

export class RequestUserForLikeAction implements Action {
    readonly type = REQUEST_USER_FOR_LIKE;

    constructor(public payload: number) { }
}

export class GetUserForLikeSuccessAction implements Action {
    readonly type = GET_USER_FOR_LIKE_SUCCESS;

    constructor(public payload: User) { }
}

export class RequestAllUsersAction implements Action {
    readonly type = REQUEST_ALL_USERS;

    constructor() { }
}

export class GetAllUsersSuccessAction implements Action {
    readonly type = GET_ALL_USERS_SUCCESS;

    constructor(public payload: User[]) { }
}

export class RequestCreateRequestAction implements Action {
    readonly type = REQUEST_CREATE_REQUEST;

    constructor(public payload: { userId: number, startDate: string, endDate: string, petsId: number, cost: number}) { }
}

export class GetCreateRequesetSuccessAction implements Action {
    readonly type = GET_CREATE_REQUEST_SUCCESS;

    constructor() { }
}

export class RequestAcceptResponseAction implements Action {
    readonly type = REQUEST_ACCEPT_RESPONSE;

    constructor(public payload: {requestId: number, responseId: number}) { }
}

export class GetAcceptResponseSuccess implements Action {
    readonly type = GET_ACCEPT_RESPONSE_SUCCESS;

    constructor() { }
}

export class RequestSendPayAction implements Action {
    readonly type = REQUEST_SEND_PAY;

    constructor(public payload: number) { }
}

export class GetSendPaySuccessAction implements Action {
    readonly type = GET_SEND_PAY_SUCCESS;

    constructor() { }
}

export class RequestRejectAllAction implements Action {
    readonly type = REQUEST_REJECT_ALL;

    constructor(public payload: number) { }
}

export class GetRejectAllSuccess implements Action {
    readonly type = GET_REJECT_ALL;

    constructor() { }
}

export type CabinetActions =
| GetPetsSuccessAction
| GetUserByIdSuccessAction
| GetRequestsSuccessAction
| GetResponsesSuccess
| GetNewRequestsSuccessAction
| GetResponsesByRequestSuccessAction
| GetUsersToBlockSuccessAction
| GetUserForLikeSuccessAction
| GetAllUsersSuccessAction;
