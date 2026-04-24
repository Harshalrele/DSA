-- Table: public.clinic_staff

-- DROP TABLE IF EXISTS public.clinic_staff;

CREATE TABLE IF NOT EXISTS public.clinic_staff
(
    clinic_emp_id bigint NOT NULL DEFAULT nextval('clinic_staff_clinic_emp_id_seq'::regclass),
    person character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT clinic_staff_pkey PRIMARY KEY (clinic_emp_id),
    CONSTRAINT uk_6rtux1lced3tevdbknjkdmy43 UNIQUE (person),
    CONSTRAINT fkrmsatonbnjynfsn4y6sch21v8 FOREIGN KEY (person)
        REFERENCES public.person (nationalidnumber) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.clinic_staff
    OWNER to postgres;