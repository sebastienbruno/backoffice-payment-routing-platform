import { Component, computed, inject, Signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatIconButton } from "@angular/material/button";
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { tap } from "rxjs";

import { Message, MessageDetailDataDialog } from "../models/message.models";
import { MessageStore } from "../message-store";
import { MessageDetailComponent } from "../message-detail/message-detail.component";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";

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
      const messageDetailDialogRef = this.dialog.open(MessageDetailComponent, {
        height: '80%',
        width: '60%',
        maxWidth: '100%',
        disableClose: true,
        data: message as MessageDetailDataDialog
      }); 
      messageDetailDialogRef.afterClosed().pipe(
        tap(() => messageDetailDialogRef.close()),
      )
      .pipe(takeUntilDestroyed())
      .subscribe();
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