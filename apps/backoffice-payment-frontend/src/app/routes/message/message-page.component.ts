import { Component } from "@angular/core";
import { MessageFilterComponent } from "./message-filter.component";
import { MessageListComponent } from "./message-list/message-list.component";

@Component({
    styles: [],
    imports: [
        MessageFilterComponent, MessageListComponent
    ],
    template: `
    <div class="message-container">
        <h1>Messages overview</h1>
        <!-- <bopr-message-filter /> -->
        <bopr-message-list class="message-list-content" />
    </div>
    `,
    selector: 'bopr-message-page'
})
export class MessagePageComponent {

}