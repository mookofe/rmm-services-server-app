-- Company
INSERT INTO companies VALUES ('0ee4a0fa-224c-4b0b-ac22-101acab87b7b', 'Demo Company', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Services
INSERT INTO services VALUES('1b023d42-1502-4e3e-acf2-f91d8ca50baf', 'Windows Antivirus', 5.00, '',   true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO services VALUES('3986ccb1-0890-451b-b819-e48b5b83ccab', 'Mac Antivirus', 7.00, '',   false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO services VALUES('9ea98956-7651-42e8-a704-a55acb55ea1d', 'Cloudberry', 3.00, '',   true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO services VALUES('f8bb1483-2e08-49ea-a506-c726cd0a2d6c', 'PSA',  2.00, '',   true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO services VALUES('73b27887-4da7-451f-bb91-c9fd3989c89e', 'TeamViewer',  2.00, '',   true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Users
INSERT INTO users VALUES('5f5c9ab0-525a-4c57-a254-b547fc45b1d9', 'admin', '$2a$10$51p87eiEAQ.CdkbsfpNJ/OpkCMJzTU.QcqZ0QHxLdGXqPuuYBE2la', '0ee4a0fa-224c-4b0b-ac22-101acab87b7b');