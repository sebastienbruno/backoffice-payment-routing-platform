import { Route } from '@angular/router';

export const appRoutes: Route[] = [
    {
        path: '',
        redirectTo: '/message',
        pathMatch: 'full',
    },
    {
        path: 'message',
        loadComponent: () => import('./routes/message/message-page.component').then((c) => c.MessagePageComponent),
    },
    {
        path: 'partner-list',
        loadComponent: () => import('./routes/partner/partner-list.component').then((c) => c.PartnerListComponent),
    },
    {
        path: '**',
        redirectTo: '',
    },
];
