import { ComponentFixture, TestBed } from "@angular/core/testing"
import { PartnerFormComponent } from "./partner-form.component"
import { UniqueAliasValidator } from "./unique-alias.validator";
import { By } from "@angular/platform-browser";
import { Observable, of } from "rxjs";
import { NgModel, ValidationErrors } from "@angular/forms";

describe('Partner form', () => {

    let fixture: ComponentFixture<PartnerFormComponent>;
    let mockUniqueAliasValidator: Observable<ValidationErrors | null>;

    beforeEach(() => {
        mockUniqueAliasValidator = of(null);
        TestBed.configureTestingModule({
            providers: [
                {
                    provide: UniqueAliasValidator, useValue: {
                        validate: () => mockUniqueAliasValidator,
                    }
                }
            ]
        })
        fixture = TestBed.createComponent(PartnerFormComponent);
        fixture.detectChanges();
    });

    it('should display alias error', () => {
        mockUniqueAliasValidator = of({uniqueAlias: true});
        const aliasInputEl = fixture.debugElement.query(By.css('input[name="alias"]'));
        aliasInputEl.triggerEventHandler('input', { target: { value: 'test-alias' } });
        aliasInputEl.triggerEventHandler('blur');
        fixture.detectChanges();
        const aliasErrorEl = fixture.debugElement.query(By.css('[data-testid="alias-error"]'));
        expect(aliasErrorEl).toBeTruthy();
    });

    [
        { fieldName: 'alias' },
        { fieldName: 'type' },
        { fieldName: 'description', fieldType: 'textarea'},
    ].forEach(({fieldName, fieldType = 'input'}) => {
        it(`should ${fieldType} notify required ${fieldName} error`, () => {
            const inputEl = fixture.debugElement.query(By.css(`${fieldType}[name="${fieldName}"]`));
            inputEl.triggerEventHandler('input', { target: { value: '' } });
            inputEl.triggerEventHandler('blur');
            fixture.detectChanges();
            expect(inputEl.classes['ng-invalid']).toBeTruthy();
            const ngModel = fixture.debugElement.query(By.directive(NgModel)).injector.get(NgModel);
            expect(ngModel.hasError('required')).toBeTruthy();
        })       
    });



})