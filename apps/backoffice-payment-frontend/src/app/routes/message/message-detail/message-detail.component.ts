import { MatChipsModule } from '@angular/material/chips';
import { Component, inject } from "@angular/core";
import { MessageDetailDataDialog } from "../models/message.models";
import { MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { MatButton } from "@angular/material/button";
import {MatDividerModule} from '@angular/material/divider';

@Component({
    selector: 'bopr-message-detail',
    templateUrl: './message-detail.component.html',
    styleUrl: 'message-detail.component.scss',
    imports: [MatButton, MatDialogModule, MatChipsModule, MatDividerModule ]
})
export class MessageDetailComponent {
    data: MessageDetailDataDialog = inject(MAT_DIALOG_DATA);
}