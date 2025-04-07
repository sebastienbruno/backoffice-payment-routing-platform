import { ApplicationConfig, inject, provideZoneChangeDetection } from '@angular/core';
import { HttpEvent, HttpHandlerFn, HttpRequest, provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { finalize, Observable } from 'rxjs';

import { appRoutes } from './app.routes';
import { LoadingService } from './shared/services/loading.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(appRoutes),
    provideHttpClient(withInterceptors([loadingInterceptor])),
  ],
};

export function loadingInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  const loadingService = inject(LoadingService);
  loadingService.load();
  return next(req).pipe(
    finalize(() => loadingService.loaded())
  );
}