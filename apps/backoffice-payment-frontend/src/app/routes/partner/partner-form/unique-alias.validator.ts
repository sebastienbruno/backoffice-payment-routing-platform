import { AbstractControl, AsyncValidator, ValidationErrors } from '@angular/forms';
import { inject, Injectable } from '@angular/core';
import { catchError, map, Observable, of } from 'rxjs';

import { PartnerApiService } from '../partner-api.service';

@Injectable({
    providedIn: 'root'
})
export class UniqueAliasValidator implements AsyncValidator {
    private readonly partnerService = inject(PartnerApiService);

    validate(control: AbstractControl): Observable<ValidationErrors | null> {
        return this.partnerService.aliasExists(control.value).pipe(
            map(isTaken => (isTaken ? {uniqueAlias: true} : null)),
            catchError(() => of(null)),
        )
    }
}