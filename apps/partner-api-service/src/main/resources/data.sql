-- Insert or update partners
MERGE INTO partner (id, alias)
KEY (id)
VALUES
  (1, 'SwiftGateway'),
  (2, 'ArchiveSystem'),
  (3, 'AlertingDashboard');
