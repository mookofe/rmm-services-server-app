-- Customers
create table companies (
  id            VARCHAR(36) not null,
  name          VARCHAR(50) not null,
  created_at    TIMESTAMP not null,
  updated_at    TIMESTAMP not null,

  primary key (id)
);

-- Services
create table services (
  id            VARCHAR(36) not null,
  name          VARCHAR(50) not null,
  price         DECIMAL(10,2) not null,
  description   VARCHAR(512) not null,
  created_at    TIMESTAMP not null,
  updated_at    TIMESTAMP not null,

  primary key (id)
);

-- Devices
create table devices (
  id            VARCHAR(36) not null,
  company_id    VARCHAR(36) not null REFERENCES companies (id),
  system_name   VARCHAR(100) not null,
  type          VARCHAR(20) not null,
  created_at    TIMESTAMP not null,
  updated_at    TIMESTAMP not null,

  primary key (id)
);

create table device_services (
  id            VARCHAR(36) not null,
  device_id     VARCHAR(36) not null REFERENCES devices (id),
  service_id    VARCHAR(36) not null REFERENCES services (id),
  created_at    TIMESTAMP not null,

  primary key (id),
  UNIQUE (device_id, service_id)
);