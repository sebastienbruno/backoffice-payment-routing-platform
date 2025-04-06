import { Component, computed, inject, Signal } from "@angular/core";
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { Partner } from "../models/partner.models";
import { FormsModule } from "@angular/forms";
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { tap } from "rxjs";
import { PartnerStore } from "../partner-store";
import { PartnerFormComponent } from "../partner-form/partner-form.component";
import { MatPaginatorModule } from '@angular/material/paginator';

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
      return dataSource;
    });
  
    onClickPartner(partner: Partner) {
      console.log('view partner detail', partner.id)
      const partnerDetailDialogRef = this.dialog.open(PartnerFormComponent, {
        height: '80%',
        width: '60%',
        maxWidth: '100%',
        disableClose: true,
      }); 
      partnerDetailDialogRef.afterClosed().pipe(
        tap(() => partnerDetailDialogRef.close()),
      ).subscribe();
    }
  }