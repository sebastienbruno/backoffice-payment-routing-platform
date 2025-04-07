import { Component, computed, inject, Signal, viewChild } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

import { Partner } from "../models/partner.models";
import { PartnerStore } from "../partner-store";

@Component({
    styles: [],
    templateUrl: './partner-list.component.html',
    styleUrl: './partner-list.component.scss',
    selector: 'bopr-partner-list',
    imports: [
      MatTableModule, MatSelectModule, MatDialogModule, MatIconModule, MatPaginatorModule,
      FormsModule,
    ],
})
export class partnerListComponent {

    partnerStore = inject(PartnerStore);
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
    ];
      
    dataSource:Signal<MatTableDataSource<Partner>> = computed(() => {
      const dataSource = new MatTableDataSource<Partner>(this.partnerStore.partners());
      dataSource.paginator = this.paginator();
      return dataSource;
    });
  }