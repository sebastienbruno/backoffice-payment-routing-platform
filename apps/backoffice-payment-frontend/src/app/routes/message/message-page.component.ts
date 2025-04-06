import { Component } from "@angular/core";

import { MessageListComponent } from "./message-list/message-list.component";

@Component({
    styles: [`
    :host {
        display: flex;
        justify-content: center;
    }
    .message-container {
        width: 80%;
    }
    `],
    imports: [
        MessageListComponent
    ],
    template: `
    <div class="message-container">
        <bopr-message-list class="message-list-content" />
    </div>
    `,
    selector: 'bopr-message-page'
})
export class MessagePageComponent {

}