import { Component } from "@angular/core";
import { MatToolbar } from "@angular/material/toolbar"
import { MatButtonModule } from "@angular/material/button"
import { RouterModule } from "@angular/router";

@Component({
    selector: 'bopr-header',
    imports: [MatToolbar, RouterModule, MatButtonModule],
    template: `
  <mat-toolbar class="toolbar">
    <span>BOPR</span>
    <div class="spacer"></div>
    <nav class="navbar">
      <a mat-button routerLinkActive="active-link" routerLink="/message" >Message</a>
      <a mat-button routerLinkActive="active-link" routerLink="/partner">Partner</a>
    </nav>
  </mat-toolbar>
    `,
    styleUrl: './header.component.scss',
})
export class HeaderComponent {

}