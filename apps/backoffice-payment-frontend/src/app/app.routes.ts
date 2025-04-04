import { Route } from '@angular/router';

export const appRoutes: Route[] = [
    {
        path: '',
        redirectTo: '/message-list',
        pathMatch: 'full',
    },
    {
        path: 'message-list',
        loadComponent: () => import('./routes/message/message-page.component').then((c) => c.MessagePageComponent),
    },
];
