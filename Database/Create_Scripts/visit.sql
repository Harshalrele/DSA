-- Table: public.visit

-- DROP TABLE IF EXISTS public.visit;

CREATE TABLE IF NOT EXISTS public.visit
(
    id bigint NOT NULL DEFAULT nextval('visit_id_seq'::regclass),
    description character varying(255) COLLATE pg_catalog."default",
    diagnostics character varying(255) COLLATE pg_catalog."default",
    scheduled_date timestamp(6) without time zone NOT NULL,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    patient character varying(255) COLLATE pg_catalog."default" NOT NULL,
    receptionist bigint NOT NULL,
    selected_doctor bigint NOT NULL,
    CONSTRAINT visit_pkey PRIMARY KEY (id),
    CONSTRAINT fk3tc2nk9pa4jcw4fewpfj25a1h FOREIGN KEY (patient)
        REFERENCES public.patient (insurance_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkm3wk9dybwm58u5hx9kujl1bgb FOREIGN KEY (receptionist)
        REFERENCES public.receptionist (clinic_staff) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkmejokb226gd7beykc9jd11j7h FOREIGN KEY (selected_doctor)
        REFERENCES public.doctor (clinic_staff) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT visit_status_check CHECK (status::text = ANY (ARRAY['REGISTERED'::character varying, 'IN_PROGRESS'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.visit
    OWNER to postgres;