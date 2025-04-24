import { Component, DestroyRef, inject } from "@angular/core";
import { MatDialog, MatDialogModule } from "@angular/material/dialog";
import { MatButtonModule } from "@angular/material/button";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { tap } from "rxjs";

import { PartnerFormComponent } from "./partner-form/partner-form.component";
import { partnerListComponent } from "./partner-list/partner-list.component";
import { CreatePartner } from './models/partner.models';
import { PartnerApiService } from './partner-api.service';

@Component({
    imports: [MatDialogModule, partnerListComponent, MatButtonModule],
    styles: [`
    :host {
        display: flex;
        justify-content: center;
    }
    .partner-container {
        width: 90%;
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
        <bopr-partner-list />
    </div>
    `,
    selector: 'bopr-partner-page'
})
export class PartnerPageComponent {

    dialog = inject(MatDialog);
    partnerService = inject(PartnerApiService);
    private destroyRef = inject(DestroyRef);


    onCreateAction() {
        const dialogRef = this.dialog.open(PartnerFormComponent, {
            height: '80%',
            width: '70%',
        })
        dialogRef.afterClosed().pipe(
            tap((createPartner: CreatePartner) => {
                if(createPartner) {
                    this.partnerService.createPartner(createPartner)
                }
            })
        )
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe();
    }
}