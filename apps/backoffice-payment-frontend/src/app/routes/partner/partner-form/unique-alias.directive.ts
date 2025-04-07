import { Directive, forwardRef, inject } from '@angular/core';
import { AsyncValidator, NG_ASYNC_VALIDATORS, AbstractControl, ValidationErrors } from '@angular/forms';
import { Observable } from 'rxjs';
import { UniqueAliasValidator } from './unique-alias.validator';

@Directive({
  selector: '[boprUniqueAlias]',
  providers: [
    {
      provide: NG_ASYNC_VALIDATORS,
      useExisting: forwardRef(() => UniqueAliasDirective),
      multi: true,
    },
  ],
})
export class UniqueAliasDirective implements AsyncValidator {
  private readonly validator = inject(UniqueAliasValidator);

  validate(control: AbstractControl): Observable<ValidationErrors | null> {
    return this.validator.validate(control);
  }
}
