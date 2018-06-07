import {Component, Input, OnInit} from '@angular/core';
import {Item} from '../../models/item';

@Component({
  selector: 'app-pets-item',
  templateUrl: './pets-item.component.html',
  styleUrls: ['./pets-item.component.css']
})
export class PetsItemComponent implements OnInit {

  @Input() item: Item;
  answer: string;
  showAnswer = false;

  constructor() { }

  ngOnInit() {
  }

  addAnswer() {
    this.showAnswer = true;
  }

}
