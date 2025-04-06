import { Component } from "@angular/core";

@Component({
    styles: [`
    :host {
        display: flex;
        justify-content: center;
    }
    .partner-container {
        width: 80%;
    }
    `],
    template: `
    <div class="partner-container">
        <h1>Partner overview</h1>
        <!-- <bopr-partner-filter /> -->
        <!-- <bopr-partner-list class="partner-list-content" /> -->
    </div>
    `,
    selector: 'bopr-partner-page'
})
export class PartnerPageComponent {

}