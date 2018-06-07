import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from '@ngrx/effects';
import {map, switchMap} from 'rxjs/operators';
import {Action, Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {CabinetService} from '../../services/cabinet.service';
import {Response} from '../../models/response';

import * as fromRoot from '../../ngrx';
import * as cabinetActions from './cabinet.actions';
import {Pet} from '../../models/pet';
import {User} from '../../models/user';

@Injectable()
export class CabinetEffects {
    constructor(private actions$: Actions, private cabinetService: CabinetService, private store: Store<fromRoot.AppState>) {
    }

    @Effect()
    requestGetItems$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_GET_PETS),
        switchMap((action: cabinetActions.RequestGetPetsAction) => {
            return this.cabinetService.getPets(action.payload);
        }),
        map((pets: Pet[]) => new cabinetActions.GetPetsSuccessAction(pets))
    );

    @Effect()
    requsetGetUserById$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_USER_BY_ID),
        switchMap((action: cabinetActions.RequsetUserByIdAction) => {
            return this.cabinetService.getUserById(action.payload);
        }),
        map((user: User) => new cabinetActions.GetUserByIdSuccessAction(user))
    );

    @Effect()
    requestRequests$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_REQUESTS),
        switchMap((action: cabinetActions.RequestRequsetsAction) => {
            return this.cabinetService.getRequestsAllByAuthor(action.payload);
        }),
        map((requests: Request[]) => new cabinetActions.GetRequestsSuccessAction(requests))
    );

    @Effect()
    requestResponses$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_RESPONSES),
        switchMap((action: cabinetActions.RequestResponsesAction) => {
            return this.cabinetService.getResponsesAlByAuthor(action.payload);
        }),
        map((responses: Response[]) => new cabinetActions.GetResponsesSuccess(responses))
    );

    @Effect()
    requestNewRequests$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_NEW_REQUESTS),
        switchMap((action: cabinetActions.RequestNewRequestsAction) => {
            return this.cabinetService.getNewRequests(action.payload);
        }),
        map((requests: Request[]) => new cabinetActions.GetNewRequestsSuccessAction(requests))
    );

    @Effect()
    requsetResponseByRequest$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_RESPONSES_BY_REQUEST),
        switchMap((action: cabinetActions.RequestResponseByrequestAction) => {
            return this.cabinetService.getResponsesByRequset(action.payload);
        }),
        map((responses: Response[]) => new cabinetActions.GetResponsesByRequestSuccessAction(responses))
    );

    @Effect()
    requsetUsersToBlock$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUST_USERS_TO_BLOCK),
        switchMap((action: cabinetActions.RequestUsersToBlockAction) => {
            return this.cabinetService.getUsersToBlock();
        }),
        map((users: User[]) => {
            return new cabinetActions.GetUsersToBlockSuccessAction(users);
        })
    );

    @Effect()
    requsetSaveReview$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUSET_SAVE_REVIEW),
        switchMap((action: cabinetActions.RequestSaveReviewAction) => {
            return this.cabinetService.saveReview(
                action.payload.comment,
                action.payload.like,
                action.payload.userId
            );
        }),
        map(() => new cabinetActions.GetSavedReviewSuccessAction())
    );

    @Effect()
    requestToBlockUser$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_TO_BLOCK_USER),
        switchMap((action: cabinetActions.RequsetBlockUserAction) => {
            return this.cabinetService.blockUser(action.payload);
        }),
        map(() => new cabinetActions.GetBlockUserSuccessAction())
    );

    @Effect()
    requestCancelRequest$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_CANCEL_REQUEST),
        switchMap((action: cabinetActions.RequestCancelRequest) => {
            return this.cabinetService.cancelRequset(action.payload);
        }),
        map(() => new cabinetActions.GetCancelRequestSuccess())
    );

    @Effect()
    requestSaveResponse$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_CREATE_RESPONSE),
        switchMap((action: cabinetActions.RequestCreateResponseAction) => {
            return this.cabinetService.createResponse(
                action.payload.requestId,
                action.payload.authorId,
                action.payload.cost
            );
        }), map(() => new cabinetActions.GetCreateResponseSuccessAction())
    );

    @Effect()
    requestUserForLike$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_USER_FOR_LIKE),
        switchMap((action: cabinetActions.RequestUserForLikeAction) => {
            return this.cabinetService.getUser(action.payload);
        }),
        map((user: User) => new cabinetActions.GetUserForLikeSuccessAction(user))
    );

    @Effect()
    requestAllUsers$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_ALL_USERS),
        switchMap((action: cabinetActions.RequestAllUsersAction) => {
            return this.cabinetService.getAllUsers();
        }),
        map((users: User[]) => new cabinetActions.GetAllUsersSuccessAction(users))
    );

    @Effect()
    requestCreateRequest$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_CREATE_REQUEST),
        switchMap((action: cabinetActions.RequestCreateRequestAction) => {
            return this.cabinetService.createRequest(
                action.payload.userId,
                action.payload.startDate,
                action.payload.endDate,
                action.payload.cost,
                action.payload.petsId
            );
        }),
        map(() => new cabinetActions.GetCreateRequesetSuccessAction())
    );

    @Effect()
    requsetAcceptResponse$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_ACCEPT_RESPONSE),
        switchMap((action: cabinetActions.RequestAcceptResponseAction) => {
            return this.cabinetService.acceptResponse(
                action.payload.requestId,
                action.payload.responseId
            );
        }),
        map(() => new cabinetActions.GetAcceptResponseSuccess())
    );

    @Effect()
    requestSendPay$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_SEND_PAY),
        switchMap((action: cabinetActions.RequestSendPayAction) => {
            return this.cabinetService.sendPay(action.payload);
        }),
        map(() => new cabinetActions.GetSendPaySuccessAction())
    );

    @Effect()
    requsetRejectAll$: Observable<Action> = this.actions$.pipe(
        ofType(cabinetActions.REQUEST_REJECT_ALL),
        switchMap((action: cabinetActions.RequestRejectAllAction) => {
            return this.cabinetService.rejectAll(action.payload);
        }),
        map(() => new cabinetActions.GetRejectAllSuccess)
    );
}
