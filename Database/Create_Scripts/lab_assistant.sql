-- Table: public.lab_assistant

-- DROP TABLE IF EXISTS public.lab_assistant;

CREATE TABLE IF NOT EXISTS public.lab_assistant
(
    id bigint NOT NULL DEFAULT nextval('lab_assistant_id_seq'::regclass),
    lab_staff bigint NOT NULL,
    CONSTRAINT lab_assistant_pkey PRIMARY KEY (id),
    CONSTRAINT uk_m8sfbbngqkukoaxtyts2y3c0t UNIQUE (lab_staff),
    CONSTRAINT fknkhte9fq95rglp0kno3m1trin FOREIGN KEY (lab_staff)
        REFERENCES public.lab_staff (lab_emp_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.lab_assistant
    OWNER to postgres;