import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { appRoutes } from './app.routes';
import { PartnerPageComponent } from './routes/partner/partner-page.component';
import { MessagePageComponent } from './routes/message/message-page.component';


describe('AppRoot', () => {
  let harness: RouterTestingHarness;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      providers: [
        provideRouter(appRoutes),
        // TODO How can I stub the MessagePageComponent and avoid provideHttpClient and HttpClientTesting
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    })
    harness = await RouterTestingHarness.create();
  });

  it('should navigate to /message and load MessagePageComponent', async () => {
    await expect(harness.navigateByUrl('/message', MessagePageComponent)).resolves.toBeDefined();
  });

  it('should navigate to /partner and load PartnerPageComponent', async () => {
    await expect(harness.navigateByUrl('/partner', PartnerPageComponent)).resolves.toBeDefined();
  });

  it('should redirect "" to /message and load MessagePageComponent', async () => {
    await expect(harness.navigateByUrl('', MessagePageComponent)).resolves.toBeDefined();
  });

  it('should redirect unknown routes to /message and load MessagePageComponent', async () => {
    await expect(harness.navigateByUrl('/not-found', MessagePageComponent)).resolves.toBeDefined();
  });

});