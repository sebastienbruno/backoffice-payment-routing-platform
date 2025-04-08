import { TestBed } from '@angular/core/testing';
import { RouterTestingHarness } from '@angular/router/testing';
import { provideRouter } from '@angular/router';
import { appRoutes } from './app.routes';
import { MessagePageComponent } from './routes/message/message-page.component';
import { PartnerPageComponent } from './routes/partner/partner-page.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('AppRoot', () => {
  let harness: RouterTestingHarness;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [],
      providers: [
        provideRouter(appRoutes),
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    })
    .compileComponents();
    harness = await RouterTestingHarness.create();
  });

  it('should navigate to /message and load MessagePageComponent', async () => {
    await expect(harness.navigateByUrl('/message', MessagePageComponent)).resolves.not.toThrow();
  });

  it('should navigate to /partner and load PartnerPageComponent', async () => {
    await expect(harness.navigateByUrl('/partner', PartnerPageComponent)).resolves.not.toThrow();
  });

  it('should redirect "" to /message and load MessagePageComponent', async () => {
    await expect(harness.navigateByUrl('', MessagePageComponent)).resolves.not.toThrow();
  });

  it('should redirect unknown routes to /message and load MessagePageComponent', async () => {
    await expect(harness.navigateByUrl('/not-found', MessagePageComponent)).resolves.not.toThrow();
  });
});
