import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./layout/header.component";
import { FooterComponent } from "./layout/footer.component";

@Component({
  imports: [RouterOutlet, HeaderComponent, FooterComponent],
  selector: 'bopr-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'backoffice-payment-frontend';
}
