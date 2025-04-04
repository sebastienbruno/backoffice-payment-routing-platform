import { inject } from '@angular/core';
import { patchState, signalStore, type, withHooks, withMethods, withState } from '@ngrx/signals';
import { entityConfig, SelectEntityId, setAllEntities, withEntities } from '@ngrx/signals/entities';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { debounceTime, distinctUntilChanged, map, pipe, switchMap, tap } from 'rxjs';
import { Message } from './models/message.models';
import { MessageApiService } from './message-api.service';

const messageCollectionName = 'messages'
const messageSelectId: SelectEntityId<Message> = (message) => message.id;
const messageConfig = entityConfig({
    entity: type<Message>(),
    collection: messageCollectionName,
    selectId: messageSelectId
})

type State = {
    loading: boolean,
    filterTerm: string,
    page: number,
    size: number,
}

export const MessageStore = signalStore(
    { providedIn: 'root' },
    withState<State>({
        loading: false,
        filterTerm: '',
        page: 0,
        size: 20,
    }),
    withEntities(messageConfig),
    withMethods((store) => {
        const messageService = inject(MessageApiService);
        return {
            setFilterTerm: rxMethod<string>(
                pipe(
                    debounceTime(300),
                    distinctUntilChanged(),
                    tap((filterTerm: string) => patchState(store, { filterTerm })),
                )
            ),
            load: rxMethod<{page: number, size: number}>(pipe(
                tap(() => patchState(store, { loading: true })),
                map((page, size) => messageService.pageMessage().content),
                tap((messages: Message[]) => {
                    patchState(store, setAllEntities(messages, messageConfig));
                }),
                tap(() => patchState(store, { loading: false })),
            )),                
        }
    }),
    withHooks({
        onInit(store) {
            store.load({page: store.page(), size: store.size()});
        },
    })
);
