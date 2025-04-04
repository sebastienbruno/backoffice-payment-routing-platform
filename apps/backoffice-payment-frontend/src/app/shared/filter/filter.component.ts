import { Component, input, model } from '@angular/core';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';

@Component({
  selector: 'bopr-filter',
  standalone: true,
  imports: [
    MatInputModule, MatFormFieldModule,
  ],
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.scss'
})
export class FilterComponent {

  filterLabel = input<string>('Filter');
  placeholder = input<string>('Ex: wind');

  filter = model<string>('');

  onFilterChange(event: Event): void {
    const filterTerm = (event.target as HTMLInputElement).value;
    if (typeof filterTerm === 'string') {
      this.filter.set(filterTerm);
    } else {
      this.filter.set('');
    }
  }

}
