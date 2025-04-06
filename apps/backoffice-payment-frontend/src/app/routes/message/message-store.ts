import { inject, Injectable, computed } from '@angular/core';
import { Message } from './models/message.models';
import { MessageApiService } from './message-api.service';

@Injectable({
    providedIn: 'root'
})
export class MessageStore {

    messageService = inject(MessageApiService);

    messages = computed(() => 
        this.messageService.pageMessagesResource.value()?.content || new Array<Message>()
    );
    page = this.messageService.page;
    size = this.messageService.size;

    fromElementNumber = computed(() => {
        const pageMessage = this.messageService.pageMessagesResource.value();
        if (pageMessage) {
            return (pageMessage.size || 0) * (pageMessage.number || 0);
        }
        return 0;
    })

    toElementNumber = computed(() => {
        const pageMessage = this.messageService.pageMessagesResource.value();
            return Math.min(
                (pageMessage?.size || 0) * ((pageMessage?.number || 0) + 1),
                pageMessage?.totalElements || 0
            );
    });
    
    totalElements = computed(() => 
        this.messageService.pageMessagesResource.value()?.totalElements
    )

    isFirstPage = computed( () => 
        this.messageService.pageMessagesResource.value()?.first
    )

    isLastPage = computed( () => 
        this.messageService.pageMessagesResource.value()?.last
    )

}