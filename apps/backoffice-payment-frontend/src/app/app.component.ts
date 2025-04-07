import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatProgressBarModule } from '@angular/material/progress-bar';

import { HeaderComponent } from "./layout/header.component";
import { FooterComponent } from "./layout/footer.component";
import { LoadingService } from './shared/services/loading.service';

@Component({
  imports: [
    RouterOutlet, 
    HeaderComponent, FooterComponent,
    MatProgressBarModule,
  ],
  selector: 'bopr-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'backoffice-payment-frontend';

  loadingService = inject(LoadingService);

}
