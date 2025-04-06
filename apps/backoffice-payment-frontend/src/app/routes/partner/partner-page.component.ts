import { Component, inject } from "@angular/core";
import { MatDialog, MatDialogModule } from "@angular/material/dialog";
import { MatButtonModule } from "@angular/material/button";
import { tap } from "rxjs";

import { PartnerFormComponent } from "./partner-form/partner-form.component";
import { partnerListComponent } from "./partner-list/partner-list.component";

@Component({
    imports: [MatDialogModule, partnerListComponent, MatButtonModule],
    styles: [`
    :host {
        display: flex;
        justify-content: center;
    }
    .partner-container {
        width: 80%;
        display: flex;
        flex-direction: column;
    }

    .partner-header {
        display: flex;
        justify-content: space-between;
        align-items: baseline;
    }
    `],
    template: `
    <div class="partner-container">
        <div class="partner-header">
            <h1>Partner manager</h1>
            <div class="partner-actions">
                <button (click)="onCreateAction()" mat-raised-button color="primary">
                    Create
                </button>
            </div>
        </div>
        <bopr-partner-list class="partner-list-content" />
    </div>
    `,
    selector: 'bopr-partner-page'
})
export class PartnerPageComponent {

    dialog = inject(MatDialog);
    onCreateAction() {
        const dialogRef = this.dialog.open(PartnerFormComponent, {
            height: '80%',
            width: '70%',
        })
        dialogRef.afterClosed().pipe(
            tap(reason => {
                if(reason) {
                    console.log('reason', reason)
                }       
            })
        )
    }
}