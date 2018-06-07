import {Component, Input, OnInit} from '@angular/core';
import {Response} from '../../models/response';
import * as fromRoot from '../../ngrx';
import {Store} from '@ngrx/store';

@Component({
  selector: 'app-response',
  templateUrl: './response.component.html',
  styleUrls: ['./response.component.css']
})
export class ResponseComponent implements OnInit {

  @Input() response: Response;
  @Input() requestId: number;

  constructor(protected store: Store<fromRoot.AppState>) { }

  ngOnInit() {
  }

}
