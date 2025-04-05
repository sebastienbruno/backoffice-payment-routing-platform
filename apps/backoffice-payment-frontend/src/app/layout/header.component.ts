import { Component } from "@angular/core";
import { MatToolbar } from "@angular/material/toolbar"
import { RouterModule } from "@angular/router";
import { MatTabsModule } from "@angular/material/tabs"

@Component({
    selector: 'bopr-header',
    imports: [MatToolbar, RouterModule, MatTabsModule],
    template: `
<div class="toolbar">
  <mat-toolbar>
    <span>BO Payment Routing Plaform</span>
    <nav mat-tab-nav-bar [tabPanel]="tabPanel">
      <a mat-tab-link routerLinkActive="active-link" routerLink="/message" >Message</a>
      <a mat-tab-link routerLinkActive="active-link" routerLink="/partner">Partner</a>
    </nav>
    <mat-tab-nav-panel #tabPanel></mat-tab-nav-panel>
    <span class="spacer"></span>
  </mat-toolbar>
</div>
    `,
    styles: [`
.spacer {
    flex: 1 1 auto;
}

    `],
})
export class HeaderComponent {

}