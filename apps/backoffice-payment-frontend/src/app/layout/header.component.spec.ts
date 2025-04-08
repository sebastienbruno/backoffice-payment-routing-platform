import { appRoutes } from './../app.routes';
import { ComponentFixture, fakeAsync, TestBed, tick } from "@angular/core/testing"
import { HeaderComponent } from "./header.component"
import { provideRouter, Router, RouterLink } from "@angular/router";
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

const MESSAGE_ROUTER_LINK_INDEX = 0;
const PARTNER_ROUTER_LINK_INDEX = 1;
const MESSAGE_ROUTER_LINK = '/message';
const PARTNER_ROUTER_LINK = '/partner';

describe('HeaderComponent', () => {

    let fixture: ComponentFixture<HeaderComponent>;
    let component: HeaderComponent;
    let linkDes: DebugElement[];
    let routerLinks: RouterLink[];

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
                provideRouter(appRoutes),
            ]
        });

        fixture = TestBed.createComponent(HeaderComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();

        linkDes = fixture.debugElement.queryAll(By.directive(RouterLink));
        routerLinks = linkDes.map((de) => de.injector.get(RouterLink));
    });


    it('should be create', () => {
        expect(component).toBeTruthy();
    });

    it('can get RouterLinks from template', () => {

        expect(routerLinks.length).toEqual(2);
        expect(routerLinks.map(links => links.href)).toContain('/message');
        expect(routerLinks.map(links => links.href)).toContain('/partner');
    })

    it('can click Message link in template', fakeAsync(() => {
        const messageLinkDe = linkDes[MESSAGE_ROUTER_LINK_INDEX];
        messageLinkDe.triggerEventHandler('click', {button: 0});
        tick();
        fixture.detectChanges();
        expect(TestBed.inject(Router).url).toBe(MESSAGE_ROUTER_LINK);
      }));

      it('can click Partner link in template', fakeAsync(() => {
        const messageLinkDe = linkDes[PARTNER_ROUTER_LINK_INDEX];
        messageLinkDe.triggerEventHandler('click', {button: 0});
        tick();
        fixture.detectChanges();
        expect(TestBed.inject(Router).url).toBe(PARTNER_ROUTER_LINK);
      }));

})