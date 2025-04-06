import { Component, computed, inject, Signal } from "@angular/core";
import { MessageStore } from "../message-store";
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { Message, MessageDetailDataDialog } from "../models/message.models";
import { FormsModule } from "@angular/forms";
import { MatIconModule } from '@angular/material/icon';
import { MatIconButton } from "@angular/material/button";
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MessageDetailComponent } from "../message-detail/message-detail.component";
import { tap } from "rxjs";

@Component({
    styles: [],
    templateUrl: './message-list.component.html',
    styleUrl: './message-list.component.scss',
    selector: 'bopr-message-list',
    imports: [
      MatTableModule, MatSelectModule, MatDialogModule,MatIconButton, MatIconModule,
      FormsModule,
    ],
})
export class MessageListComponent {

    messageStore = inject(MessageStore);
    dialog = inject(MatDialog);

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
  
    onClickMessage(message: Message) {
      console.log('view message detail', message.id)
      const messageDetailDialogRef = this.dialog.open(MessageDetailComponent, {
        height: '80%',
        width: '60%',
        maxWidth: '100%',
        disableClose: true,
        data: message as MessageDetailDataDialog
      }); 
      messageDetailDialogRef.afterClosed().pipe(
        tap(() => messageDetailDialogRef.close()),
      ).subscribe();
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