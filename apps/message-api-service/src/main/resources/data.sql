-- Insert or update messages
MERGE INTO message (id, sender, payload, received_at)
KEY (id)
VALUES
  (
    1,
    'CoreBankingSystem',
    'PAYMENT|REF:12345|AMOUNT:100.00|CURRENCY:EUR',
    '2025-04-02T10:15:00'
  ),
  (
    2,
    'TradingApp',
    'ALERT|LEVEL:HIGH|TIMESTAMP:2025-04-02T11:30:00',
    '2025-04-02T11:30:00'
  );

-- Insert or update message recipients
MERGE INTO message_recipients (message_id, recipient)
KEY (message_id, recipient)
VALUES
  (1, 'SwiftGateway'),
  (1, 'ArchiveSystem'),
  (2, 'AlertingDashboard');
