-- Table: public.doctor

-- DROP TABLE IF EXISTS public.doctor;

CREATE TABLE IF NOT EXISTS public.doctor
(
    id bigint NOT NULL DEFAULT nextval('doctor_id_seq'::regclass),
    npwz_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    clinic_staff bigint NOT NULL,
    CONSTRAINT doctor_pkey PRIMARY KEY (id),
    CONSTRAINT uk_9yt1of9hyayittqoxu8ws92py UNIQUE (clinic_staff),
    CONSTRAINT fklg5ynd33ejxf93hwgjc05rjiv FOREIGN KEY (clinic_staff)
        REFERENCES public.clinic_staff (clinic_emp_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.doctor
    OWNER to postgres;