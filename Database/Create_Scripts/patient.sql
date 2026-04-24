-- Table: public.patient

-- DROP TABLE IF EXISTS public.patient;

CREATE TABLE IF NOT EXISTS public.patient
(
    insurance_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    person character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT patient_pkey PRIMARY KEY (insurance_id),
    CONSTRAINT uk_p0kfyft4h4a36aycdqmq8kvat UNIQUE (person),
    CONSTRAINT fk1qle78qg2u7w6lpb8xn9yhg3r FOREIGN KEY (person)
        REFERENCES public.person (nationalidnumber) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.patient
    OWNER to postgres;