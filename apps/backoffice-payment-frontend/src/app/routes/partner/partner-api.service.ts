import { HttpClient, httpResource } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';

import { API_URL_PARTNER } from '../../../environments/url';
import { CreatePartner, Partner } from './models/partner.models';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PartnerApiService {

  httpClient = inject(HttpClient);

  partnerChanged = signal<boolean>(true);
  partnersResource = httpResource<Partner[]>(() => {
    this.partnerChanged();
    return API_URL_PARTNER;
  })

  createPartner(createPartner: CreatePartner) {
    this.httpClient.post(API_URL_PARTNER, createPartner).pipe(
      tap(() => this.partnerChanged.update(a => !a)),
    ).subscribe();
  }

  deletePartner(partner: Partner) {
    this.httpClient.delete(`${API_URL_PARTNER}/${partner.id}`).pipe(
      tap(() => this.partnerChanged.update(a => !a)),
    ).subscribe();
  }

}
