import { Component } from "@angular/core";
import { MessageListComponent } from "./message-list/message-list.component";

@Component({
    styles: [],
    imports: [
        MessageListComponent
    ],
    template: `
    <div class="message-container">
        <!-- <bopr-message-filter /> -->
        <bopr-message-list class="message-list-content" />
    </div>
    `,
    selector: 'bopr-message-page'
})
export class MessagePageComponent {

}