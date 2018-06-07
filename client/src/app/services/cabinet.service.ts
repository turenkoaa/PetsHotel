import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import * as fromRoot from '../ngrx';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {Item} from '../models/item';
import {environment} from '../core/enviroment';
import {first} from 'rxjs/operators';

@Injectable()
export class CabinetService {

  items: Item[];
  list = [0, 1, 2];

  constructor(protected store: Store<fromRoot.AppState>, protected http: HttpClient) { }

  getPets(id: number): Observable<any> {
    const url = environment.getPetsByOwner + `${id}`;
    return this.http.get(url).pipe(
      first()
    );
  }

  getUserById(email: string): Observable<any> {
    const url = environment.getUserByEmail + `${email}`;
    return this.http.get(url).pipe(
      first()
    );
  }

  getRequestsAllByAuthor(id: number): Observable<any> {
    const url = environment.getRequestsAllByAuthor + `${id}`;
    return this.http.get(url).pipe(
      first()
    );
  }

  getResponsesAlByAuthor(id: number): Observable<any> {
    const url = environment.getResponsesAllByAuthor + `${id}`;
    return this.http.get(url).pipe(
      first()
    );
  }

  getNewRequests(id: number): Observable<any> {
    const url = environment.getNewRequests + `${id}`;
    return this.http.get(url).pipe(
      first()
    );
  }

  getResponsesByRequset(id: number): Observable<any> {
    const url = environment.getResponsesByRequset + `${id}`;
    return this.http.get(url).pipe(
      first()
    );
  }

  getUsersToBlock(): Observable<any> {
    const url = environment.getUsersToBlock;
    return this.http.get(url).pipe(
      first()
    );
  }

  saveReview(comment: string, like: number, userId: number): Observable<any> {
    const url = environment.getDislikeForUser;
    return this.http.post(url, {comment: comment, like: like, userId: userId}).pipe(first());
  }

  blockUser(id: number) {
    const url = environment.getBlockuser + `${id}`;
    return this.http.put(url, '');
  }

  cancelRequset(id: number) {
    const url = environment.getRequset + `${id}/anulled`;
    return this.http.put(url, '');
  }

  createResponse(requestId: number, authorId: number, cost: number) {
    const url = environment.getCreateResponse;
    return this.http.post(url, {requestId: requestId, authorId: authorId, cost: cost}).pipe(first());

  }

  createRequest(userId: number, startDate: string, endDate: string, cost: number, petId: number) {
    const url = environment.getCreateRequest;
    return this.http.post(url, {userId: userId, startDate: startDate, endDate: endDate, petId: petId, cost: cost}).pipe(first());
  }

  getUser(id: number): Observable<any> {
    const url = environment.getUser + `${id}`;
    return this.http.get(url).pipe(
      first()
    );
  }

  getAllUsers(): Observable<any> {
    const url = environment.getAllUsers;
    return this.http.get(url).pipe(
      first()
    );
  }

  acceptResponse(requestId: number, responseId: number) {
    const url = environment.getRequset + `${requestId}/accept/${responseId}`;
    return this.http.put(url, '');
  }

  sendPay(requestId: number) {
    const url = environment.getRequset + `${requestId}/paid`;
    return this.http.put(url, '');
  }

  rejectAll(requestId: number) {
    const url = environment.getRequset + `${requestId}/reject-responses`;
    return this.http.put(url, '');
  }
}
