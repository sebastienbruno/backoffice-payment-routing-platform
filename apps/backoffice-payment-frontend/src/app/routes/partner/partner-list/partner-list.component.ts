import { Component, computed, inject, Signal, viewChild } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

import { Partner } from "../models/partner.models";
import { PartnerStore } from "../partner-store";
import { MatButtonModule } from "@angular/material/button";
import { PartnerApiService } from "../partner-api.service";

@Component({
    styles: [],
    templateUrl: './partner-list.component.html',
    styleUrl: './partner-list.component.scss',
    selector: 'bopr-partner-list',
    imports: [
      MatTableModule, MatSelectModule, MatDialogModule, MatIconModule, MatPaginatorModule, MatButtonModule,
      FormsModule,
    ],
})
export class partnerListComponent {

    partnerStore = inject(PartnerStore);
    parterService = inject(PartnerApiService);
    dialog = inject(MatDialog);
    paginator = viewChild.required(MatPaginator);

    partnerList = this.partnerStore.partners;
    displayedColumns: string[] = [
      'alias',
      'type',
      'direction',
      'application',
      'processedFlowType',
      'description',
      'deleteAction',
    ];
      
    dataSource:Signal<MatTableDataSource<Partner>> = computed(() => {
      const dataSource = new MatTableDataSource<Partner>(this.partnerStore.partners());
      dataSource.paginator = this.paginator();
      return dataSource;
    });

    onDeleteAction(partner: Partner) {
      this.parterService.deletePartner(partner);
    }
  }