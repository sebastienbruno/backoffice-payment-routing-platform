import { httpResource } from '@angular/common/http';
import { computed, Injectable, Signal, signal } from '@angular/core';
import { Message } from './models/message.models';
//import { API_URL } from 'src/environments/url';

@Injectable({
  providedIn: 'root'
})
export class MessageApiService {
  pageMessage = signal({
    content: [
      {id: 1, payload: 'hello payload', recipients: ['first', 'second'], sender: 'backoffic'},
      {id: 2, payload: 'hello payload', recipients: ['first', 'second'], sender: 'backoffic'},
      {id: 3, payload: 'hello payload', recipients: ['first', 'second'], sender: 'backoffic'},
      {id: 4, payload: 'hello payload', recipients: ['first', 'second'], sender: 'backoffic'},
      {id: 5, payload: 'hello payload', recipients: ['first', 'second'], sender: 'backoffic'},
      {id: 6, payload: 'hello payload', recipients: ['first', 'second'], sender: 'backoffic'},
      {id: 7, payload: 'hello payload', recipients: ['first', 'second'], sender: 'backoffic'},
      {id: 8, payload: 'hello payload', recipients: ['first', 'second'], sender: 'backoffic'},
      {id: 9, payload: 'hello payload', recipients: ['first', 'second'], sender: 'backoffic'},
      {id: 10, payload: 'hello payload', recipients: ['first', 'second'], sender: 'backoffic'},
    ],
    totalElements: 10,
    empty: false,
    first: true,
    last: false,
    size: 5,
    number: 0,
    numberOfElements: 5,
    totalPages: 2,
  });

  page = computed(() => this.pageMessage().number);
  size = computed(() => this.pageMessage().size);
  httpResource
    
}
