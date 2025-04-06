import { httpResource } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';

import { API_URL_MESSAGE } from './../../../environments/url';

import { PageMessage } from './models/message.models';

@Injectable({
  providedIn: 'root'
})
export class MessageApiService {

  page = signal(0);
  size = signal(10);
  
  pageMessagesResource = httpResource<PageMessage>(() => `${API_URL_MESSAGE}?page=${this.page()}&size=${this.size()}`);
}
