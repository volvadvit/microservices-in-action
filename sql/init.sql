CREATE TABLE IF NOT EXISTS organizations (
    organization_id varchar(255) NOT NULL,
    name varchar(50),
    contact_name varchar(50),
    contact_email varchar(50),
    contact_phone varchar(20),
    CONSTRAINT organizations_pkey PRIMARY KEY (organization_id)
);

CREATE TABLE IF NOT EXISTS licenses (
    license_id varchar(255) NOT NULL,
    organization_id varchar(255) NOT NULL,
    description varchar(255),
    product_name varchar(50) NOT NULL,
    license_type varchar(50) NOT NULL,
    comment varchar(255),
    CONSTRAINT licenses_pkey PRIMARY KEY (license_id),
    CONSTRAINT licenses_organization_id_fkey FOREIGN KEY (organization_id)
        REFERENCES public.organizations (organization_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);