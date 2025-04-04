import { Component, inject } from "@angular/core";
import { MessageStore } from "../message-store";

@Component({
    styles: [],
    templateUrl: './message-list.component.html',
    selector: 'bopr-message-list'
})
export class MessageListComponent {

    messageStore = inject(MessageStore);

    messageList = this.messageStore.messagesEntities;
}