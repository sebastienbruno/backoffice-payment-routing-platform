import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";
import { MatToolbar } from "@angular/material/toolbar"
import { MatButtonModule } from "@angular/material/button"

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