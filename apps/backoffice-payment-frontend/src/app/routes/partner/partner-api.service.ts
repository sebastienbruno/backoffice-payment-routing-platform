import { HttpClient, httpResource } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable, take, tap } from 'rxjs';

import { API_URL_PARTNER } from '../../../environments/url';
import { CreatePartner, Partner } from './models/partner.models';

@Injectable({
  providedIn: 'root'
})
export class PartnerApiService {

  httpClient = inject(HttpClient);

  partnersResource = httpResource<Partner[]>(() => {
    return API_URL_PARTNER;
  })

  createPartner(createPartner: CreatePartner) {
    this.httpClient.post(API_URL_PARTNER, createPartner).pipe(
      tap(() => this.partnersResource.reload()),
    )
    .pipe(take(1))
    .subscribe();
  }

  deletePartner(partner: Partner) {
    this.httpClient.delete(`${API_URL_PARTNER}/${partner.id}`).pipe(
      tap(() => this.partnersResource.reload()),
    )
    .pipe(take(1))
    .subscribe();
  }

  aliasExists(alias: string): Observable<boolean> {
    return this.httpClient.get<Partner[]>(`${API_URL_PARTNER}?alias=${alias}`).pipe(
      map(result => result.length > 0)
    );
  }
}
