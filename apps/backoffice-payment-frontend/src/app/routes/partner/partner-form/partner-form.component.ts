import { Direction, DIRECTION_LIST, Partner, PROCESSED_FLOW_TYPE_LIST, ProcessedFlowType } from './../models/partner.models';
import { Component } from "@angular/core";
import { TitleCasePipe } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { MatFormField, MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'bopr-partner-form',
    templateUrl: './partner-form.component.html',
    styleUrl: './partner-form.component.scss',
    imports: [
        FormsModule,
        MatFormField, MatInputModule, MatSelectModule, MatDialogModule,
        MatButtonModule,
        TitleCasePipe,
    ]
})
export class PartnerFormComponent {

    directionOptions = DIRECTION_LIST;
    processedFlowTypeOptions = PROCESSED_FLOW_TYPE_LIST;

    partner: Partner = {
        alias: '',
        description: '', 
        direction: Direction.INBOUND,
        processedFlowType: ProcessedFlowType.MESSAGE,
        type: '',
        application: '',
    };
}