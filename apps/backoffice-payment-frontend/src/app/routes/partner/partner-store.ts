import { inject, Injectable, computed } from '@angular/core';
import { Partner } from './models/partner.models';
import { PartnerApiService } from './partner-api.service';

@Injectable({
    providedIn: 'root'
})
export class PartnerStore {

    partnerService = inject(PartnerApiService);

    partners = computed(() => 
        this.partnerService.partnersResource.value()?.content || new Array<Partner>()
    );
}