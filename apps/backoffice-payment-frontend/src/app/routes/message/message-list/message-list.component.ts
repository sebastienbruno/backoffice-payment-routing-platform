import { Component, computed, inject, Signal } from "@angular/core";
import { MessageStore } from "../message-store";
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { Message } from "../models/message.models";
import { FormsModule } from "@angular/forms";
import { MatIcon } from '@angular/material/icon';
import { MatIconButton } from "@angular/material/button";
@Component({
    styles: [],
    templateUrl: './message-list.component.html',
    styleUrl: './message-list.component.scss',
    selector: 'bopr-message-list',
    imports: [
      MatTableModule, MatSelectModule,
      FormsModule, MatIconButton, MatIcon
    ],
})
export class MessageListComponent {

    messageStore = inject(MessageStore);

    messageList = this.messageStore.messages;
    displayedColumns: string[] = [
      'id',
      'sender',
      'recipients',
    ];
      
    dataSource:Signal<MatTableDataSource<Message>> = computed(() => {
      const dataSource = new MatTableDataSource<Message>(this.messageStore.messages());
      return dataSource;
    });
  
    onClickMessage(messageId: string) {
      console.log('view message detail', messageId)
      return;
    }
  
    onChangeSize() {
      this.messageStore.page.set(0)
    }

    onPreviousPage() {
      if (this.messageStore.isFirstPage()) 
        return;
      this.messageStore.page.update(actualPageNumber => actualPageNumber - 1);
    }

    onNextPage() {
      if (this.messageStore.isLastPage()) 
        return;
      this.messageStore.page.update(actualPageNumber => actualPageNumber + 1);
    } 
}