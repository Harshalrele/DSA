-- Table: public.receptionist

-- DROP TABLE IF EXISTS public.receptionist;

CREATE TABLE IF NOT EXISTS public.receptionist
(
    id bigint NOT NULL DEFAULT nextval('receptionist_id_seq'::regclass),
    clinic_staff bigint NOT NULL,
    CONSTRAINT receptionist_pkey PRIMARY KEY (id),
    CONSTRAINT uk_55xw2iid3ghs9bujtxhpl3147 UNIQUE (clinic_staff),
    CONSTRAINT fk8ypbno7x3ayxywwj39ux0h7k2 FOREIGN KEY (clinic_staff)
        REFERENCES public.clinic_staff (clinic_emp_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.receptionist
    OWNER to postgres;