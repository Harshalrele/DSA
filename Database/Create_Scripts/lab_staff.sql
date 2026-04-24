-- Table: public.lab_staff

-- DROP TABLE IF EXISTS public.lab_staff;

CREATE TABLE IF NOT EXISTS public.lab_staff
(
    lab_emp_id bigint NOT NULL DEFAULT nextval('lab_staff_lab_emp_id_seq'::regclass),
    person character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT lab_staff_pkey PRIMARY KEY (lab_emp_id),
    CONSTRAINT uk_t9uarcy1xieow8are7tljhc4t UNIQUE (person),
    CONSTRAINT fkpc19vhxqwg1q1k8in3nnhfy0j FOREIGN KEY (person)
        REFERENCES public.person (nationalidnumber) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.lab_staff
    OWNER to postgres;