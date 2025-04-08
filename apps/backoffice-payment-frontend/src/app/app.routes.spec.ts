import { TestBed } from '@angular/core/testing';
import { RouterTestingHarness } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { provideRouter } from '@angular/router';
import { appRoutes } from './app.routes';
import { MessagePageComponent } from './routes/message/message-page.component';
import { PartnerPageComponent } from './routes/partner/partner-page.component';
import { HeaderComponent } from './layout/header.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { Component } from '@angular/core';

@Component({selector: 'bopr-partner-page'})
class PartnerPageStubComponent {}


describe('AppRoot', () => {
  let harness: RouterTestingHarness;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent, HeaderComponent],
      providers: [
        provideRouter(appRoutes),
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: PartnerPageComponent, useClass: PartnerPageStubComponent },
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
