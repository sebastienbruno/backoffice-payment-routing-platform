import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatDatepickerModule } from '@angular/material/datepicker'
import {provideNativeDateAdapter} from '@angular/material/core';

import { FilterComponent } from '../../shared/filter/filter.component';
import { MessageStore } from './message-store';

@Component({
  selector: 'bopr-message-filter',
  standalone: true,
  providers: [provideNativeDateAdapter()],
  imports: [
    FilterComponent,
    FormsModule, ReactiveFormsModule,
    MatSlideToggleModule, MatFormFieldModule, MatDatepickerModule
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
  <div class="daily-synop-filter-container">
    <bopr-filter [filter]="filterTerm()" (filterChange)="onFilterTerm($event)" placeholder="Enter message informations" class="filter"/>
  </div>
  `,
  styles: [`
  .message-filter-container {
    margin: 0px 30px;
    display: flex;
    align-items: baseline;
  }

  .filter {
    min-width: 35%;
    flex: 2; 
  }
  `],
})
export class MessageFilterComponent {

  messageStore = inject(MessageStore);

  filterTerm = this.messageStore.filterTerm;

  onFilterTerm(filterTerm: string) {
    this.messageStore.setFilterTerm(filterTerm);
  }

}
