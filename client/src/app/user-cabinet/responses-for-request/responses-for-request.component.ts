import {Component, Input, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {filter, take, tap} from 'rxjs/operators';
import {Subscription} from 'rxjs/Subscription';
import {Response} from '../../models/response';

import * as fromRoot from '../../ngrx';
import * as fromCabinet from '../ngrx';
import * as cabinetActions from '../ngrx/cabinet.actions';

@Component({
  selector: 'app-responses-for-request',
  templateUrl: './responses-for-request.component.html',
  styleUrls: ['./responses-for-request.component.css']
})
export class ResponsesForRequestComponent implements OnInit {

  @Input() requestId: number;
  @Input() requestClosed: boolean;

  subscriptions: Subscription;
  responses: Response[];
  responsesSubscription: Subscription;
  constructor(protected store: Store<fromRoot.AppState>) { }

  ngOnInit() {
    this.responses = [];
    this.store.dispatch(new cabinetActions.GetResponsesByRequestSuccessAction(null));

    this.subscriptions = this.store.select(fromCabinet.getResponsesByRequests()).pipe(
      tap(t => {
        if (t == null) {
          this.store.dispatch(new cabinetActions.RequestResponseByrequestAction(this.requestId));
        }
      }),
      filter(t => t != null),
      take(1)
    )
      .subscribe((responses: Response[]) => {
        responses.forEach(item =>
          this.responses.push(item)
        );
      });
  }

  accepResponse(responseId: number) {
    this.store.dispatch(new cabinetActions.RequestAcceptResponseAction({
      requestId: this.requestId,
      responseId: responseId
    }
    ));
  }

  pay() {
    this.store.dispatch(new cabinetActions.RequestSendPayAction(this.requestId));
  }


}
