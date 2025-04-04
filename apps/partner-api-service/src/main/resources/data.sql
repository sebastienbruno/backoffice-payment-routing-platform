-- Insert or update partners
MERGE INTO partner (id, alias, type, direction, application, processed_flow_type, description)
KEY (id)
VALUES
  (1, 'SwiftGateway', 'external', 'OUTBOUND', 'SWIFT-APP', 'MESSAGE', 'Partner for SWIFT transactions'),
  (2, 'ArchiveSystem', 'internal', 'INBOUND', 'ARCHIVER', 'NOTIFICATION', 'Internal archiving system'),
  (3, 'AlertingDashboard', 'internal', 'OUTBOUND', 'ALERT-UI', 'ALERTING', 'Dashboard for alert broadcasting');
