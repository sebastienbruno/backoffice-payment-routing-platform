import { httpResource } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { PageMessage } from '../../shared/models/message.models';
import { API_URL_MESSAGE } from '../../../environments/url';

@Injectable({
  providedIn: 'root'
})
export class MessageApiService {

  page = signal(0);
  size = signal(10);
  
  pageMessagesResource = httpResource<PageMessage>(() => `${API_URL_MESSAGE}?page=${this.page()}&size=${this.size()}`);
}
