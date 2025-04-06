import { HttpClient, httpResource } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { API_URL_PARTNER } from '../../../environments/url';
import { CreatePartner, Partner } from './models/partner.models';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PartnerApiService {

  httpClient = inject(HttpClient);

  partnersResource = httpResource<Partner[]>(() => API_URL_PARTNER);


  createPartner(createPartner: CreatePartner) {
    this.httpClient.post(API_URL_PARTNER, createPartner).pipe(
      tap(v => console.log('result', v))
    ).subscribe();
  }

}
