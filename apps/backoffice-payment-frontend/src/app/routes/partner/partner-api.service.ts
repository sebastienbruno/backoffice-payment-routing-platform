import { httpResource } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL_PARTNER } from '../../../environments/url';
import { PagePartner } from './models/partner.models';

@Injectable({
  providedIn: 'root'
})
export class PartnerApiService {

  partnersResource = httpResource<PagePartner>(() => API_URL_PARTNER);

}
