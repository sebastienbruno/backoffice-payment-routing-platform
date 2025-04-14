import { ComponentFixture, TestBed } from "@angular/core/testing"
import { MessageDetailComponent } from "./message-detail.component"
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { By } from "@angular/platform-browser";

describe('MessageDetailComponent', () => {

    let fixture: ComponentFixture<MessageDetailComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [{ 
                provide: MAT_DIALOG_DATA,
                useValue: {
                    id: 1,
                    sender: 'sender-test',
                    recipients: ['rec-1', 'rec-2'],
                    payload: 'payload-test',
                }
            }]
        });

        fixture = TestBed.createComponent(MessageDetailComponent);
        fixture.detectChanges();
    })

    it('should have a close button', () => {
        expect(fixture.debugElement.query(By.css('[data-testid="close-button"]'))).toBeTruthy();
    })

    it('should display the detail of a message', () => {
        const testidValues = ['entry-value', 'sender-value', 'recipient-value', 'payload-value'];
        testidValues.forEach(testIdValue => 
            expect(fixture.debugElement.query(By.css(`[data-testid="${testIdValue}"]`))).toBeTruthy()
        );
    })
})