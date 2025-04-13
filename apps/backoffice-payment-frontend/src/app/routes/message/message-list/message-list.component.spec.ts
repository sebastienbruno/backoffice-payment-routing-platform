import { DebugElement, signal } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { MessageListComponent } from './message-list.component';
import { Message } from '../models/message.models';
import { MessageStore } from '../message-store';
import { MessageDetailComponent } from '../message-detail/message-detail.component';

describe(`MessageListComponent`, () => {
  let fixture: ComponentFixture<MessageListComponent>;
  let component: MessageListComponent;
  let debugElement: DebugElement;

  const mockedMessages: Array<Message> = [
    {
      id: 1,
      payload: 'payload-test-1',
      recipients: ['recipient-1', 'recipient-2'],
      sender: 'sender-test-1',
    },
    {
      id: 2,
      payload: 'payload-test-2',
      recipients: ['recipient-2', 'recipient-3'],
      sender: 'sender-test-2',
    },
    {
      id: 3,
      payload: 'payload-test-3',
      recipients: ['recipient-3', 'recipient-4'],
      sender: 'sender-test-3',
    },
  ];
  let messageStoreMock: Partial<MessageStore>;

  beforeEach(async () => {
    messageStoreMock = {
      messages: signal(mockedMessages),
      page: signal(0),
      isFirstPage: signal(true),
      isLastPage: signal(false),
      fromElementNumber: signal(0),
      toElementNumber: signal(10),
      size: signal(10),
      totalElements: signal(35),
    };
    TestBed.configureTestingModule({
      providers: [
        { provide: MessageStore, useValue: messageStoreMock },
      ],
    });
    fixture = TestBed.createComponent(MessageListComponent);
    component = fixture.componentInstance;

    debugElement = fixture.debugElement;
    fixture.detectChanges();
  });

  it(`should create`, () => {
    expect(component).toBeTruthy();
  });

  it(`should display all messages`, () => {
    const rows = debugElement.queryAll(By.css('[data-testid="row-message"]'));
    expect(rows.length).toBe(mockedMessages.length);
  });

  it(`should display message id`, () => {
    const cellMessageId = debugElement.query(
      By.css('[data-testid="cell-message-id"]')
    );
    const textContent = cellMessageId.nativeElement.textContent.trim();
    expect(textContent).toContain(mockedMessages[0].id.toString());
  });

  it(`should display message sender`, () => {
    const cellMessageSender = debugElement.query(
      By.css('[data-testid="cell-message-sender"]')
    );
    const textContent = cellMessageSender.nativeElement.textContent.trim();
    expect(textContent).toContain(mockedMessages[0].sender.toString().trim());
  });

  it(`should display message recipients`, () => {
    const cellMessageRecipients = debugElement.query(
      By.css('[data-testid="cell-message-recipients"]')
    );
    const textContentList =
      cellMessageRecipients.nativeElement.textContent.split(',');
    mockedMessages[0].recipients.forEach((recipient, index) => {
      expect(textContentList[index].trim()).toContain(recipient.trim());
    });
  });

  it(`should open dialog MessageDetailComponent on message item click`, async () => {
    const dialogSpyOnOpen = jest.spyOn(component.dialog, 'open');
    const row = debugElement.query(By.css('[data-testid="row-message"]'));
    row.triggerEventHandler('click');
    fixture.detectChanges();
    expect(dialogSpyOnOpen).toHaveBeenCalled();
    expect(dialogSpyOnOpen).toHaveBeenCalledWith(
      MessageDetailComponent,
      expect.objectContaining({
        data: expect.any(Object),
      })
    )    
  });
});
